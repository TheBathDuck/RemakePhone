package nl.thebathduck.remakephone.commands;

import com.live.bemmamin.gps.api.GPSAPI;
import nl.thebathduck.remakephone.RemakePhone;
import nl.thebathduck.remakephone.managers.NavigationManager;
import nl.thebathduck.remakephone.utils.ChatUtils;
import nl.thebathduck.remakephone.utils.PlotUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
//        if (!(sender instanceof Player)) {
//            sender.sendMessage(ChatUtils.color("&cAlleen een speler kan dit command uitvoeren!"));
//            return false;
//        }
//        Player player = (Player) sender;
        if(!sender.hasPermission("remakephone.reload")) {
            sender.sendMessage(ChatUtils.color("&cJij hebt hier geen permissies voor!"));
            return false;
        }

        sender.sendMessage(ChatUtils.color("&6Reloading phone, check the console for error!"));
        RemakePhone.getInstance().reloadConfig();
        NavigationManager.getInstance().reload();
        PlotUtils.getInstance().updatePlotsList();
        sender.sendMessage(ChatUtils.color("&6Reload complete."));
        RemakePhone.setGPS(new GPSAPI(RemakePhone.getInstance()));

        return false;
    }
}
