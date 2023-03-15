package nl.thebathduck.remakephone;

import com.live.bemmamin.gps.api.GPSAPI;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import nl.thebathduck.remakephone.commands.PhoneCommand;
import nl.thebathduck.remakephone.commands.PlotCommand;
import nl.thebathduck.remakephone.listeners.*;
import nl.thebathduck.remakephone.managers.NavigationManager;
import nl.thebathduck.remakephone.managers.PhoneManager;
import nl.thebathduck.remakephone.objects.Phone;
import nl.thebathduck.remakephone.utils.GUIHolder;
import nl.thebathduck.remakephone.utils.PlotUtils;
import nl.thebathduck.remakephone.utils.sql.SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class RemakePhone extends JavaPlugin {

    private static @Getter RemakePhone instance;
    private static @Getter GPSAPI GPS;
    private static @Getter Economy economy;

    @Override
    public void onLoad() {
        PlotUtils.getInstance().onLoad();
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        GUIHolder.init(this);

        GPS = new GPSAPI(this);

        SQLManager.getInstance().init(
                getConfig().getString("database.ip"),
                getConfig().getInt("database.port"),
                getConfig().getString("database.database"),
                getConfig().getString("database.username"),
                getConfig().getString("database.password")
        );
        SQLManager.getInstance().createTables();

        getCommand("remakephone").setExecutor(new PhoneCommand());
        getCommand("huizenmarkt").setExecutor(new PlotCommand());

        RegisteredServiceProvider<Economy> economyService = getServer().getServicesManager().getRegistration(Economy.class);
        if(economyService == null) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        economy = economyService.getProvider();

        registerListeners();

        NavigationManager.getInstance().initialize();
        PlotUtils.getInstance().initialize();

        loadPlayers();
    }

    @Override
    public void onDisable() {
        PhoneManager phoneManager = PhoneManager.getInstance();
        Bukkit.getOnlinePlayers().forEach(player -> {
            phoneManager.savePhone(phoneManager.getPhone(player.getUniqueId()));
            phoneManager.setunloadItem(player);
        });
        try {
            SQLManager.getInstance().shutdown();
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Could not close connection to the database: " + e.getSQLState());
        }
    }

    public void registerListeners() {
        PluginManager manager = Bukkit.getPluginManager();

        manager.registerEvents(new PhoneInteractionListener(), this);
        manager.registerEvents(new MapsChatListener(), this);
        manager.registerEvents(new PlayerJoinListener(), this);
        manager.registerEvents(new PlayerQuitListener(), this);
        manager.registerEvents(new RemakePhoneListener(), this);

    }

    public void loadPlayers() {
        PhoneManager phoneManager = PhoneManager.getInstance();

        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            Bukkit.getOnlinePlayers().forEach(player -> {
                if(phoneManager.isInDatabase(player.getUniqueId())) {
                    Phone phone = phoneManager.getFromDatabase(player.getUniqueId());
                    phoneManager.cachePhone(player.getUniqueId(), phone);
                    phoneManager.loadItem(player, phone);
                    return;
                }
                int number = phoneManager.getRandomNumber();
                Phone phone = phoneManager.registerPhoneNumber(player.getUniqueId(), number);
                phoneManager.loadItem(player, phone);
            });
        });


    }

}