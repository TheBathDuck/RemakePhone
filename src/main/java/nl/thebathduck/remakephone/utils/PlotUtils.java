package nl.thebathduck.remakephone.utils;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.BooleanFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.IntegerFlag;
import com.sk89q.worldguard.protection.flags.StringFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PlotUtils {

    private static PlotUtils instance;
    private WorldGuardPlugin worldGuard;

    private List<Flag> worldGuardFlags;
    public IntegerFlag RMT_PLOTS_PRICE;
    public IntegerFlag RMT_PLOTS_SELLPRICE;
    public BooleanFlag RMT_PLOTS_SELLING;
    public StringFlag RMT_GPS_POINT;

    private List<ProtectedRegion> huizenMarktRegions;

    public void onLoad() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
        if (plugin == null) return;
        worldGuard = (WorldGuardPlugin) plugin;
        worldGuardFlags = new ArrayList<>();

        RMT_PLOTS_PRICE = new IntegerFlag("rmt-price");
        RMT_PLOTS_SELLPRICE = new IntegerFlag("rmt-sellprice");
        RMT_PLOTS_SELLING = new BooleanFlag("rmt-selling");
        RMT_GPS_POINT = new StringFlag("rmt-gps-point");
        worldGuardFlags.add(RMT_PLOTS_PRICE);
        worldGuardFlags.add(RMT_PLOTS_SELLPRICE);
        worldGuardFlags.add(RMT_PLOTS_SELLING);
        worldGuardFlags.add(RMT_GPS_POINT);

        huizenMarktRegions = new ArrayList<>();

        FlagRegistry registry = worldGuard.getFlagRegistry();
        try {
            worldGuardFlags.forEach(flag -> {
                registry.register(flag);
                Bukkit.getLogger().info("[RemakePhone] [WorldguardFlags] Flag loaded: " + flag.getName());
            });
        } catch (FlagConflictException e) {
            Bukkit.getLogger().severe("[FlagRegistery] Err: " + e.getLocalizedMessage());
        }
    }

    public void initialize() {
        Bukkit.getWorlds().forEach(world -> {
            RegionManager manager = worldGuard.getRegionManager(world);
            manager.getRegions().values().forEach(region -> {
                if(region.getFlag(RMT_PLOTS_PRICE) != null) {
                    huizenMarktRegions.add(region);
                    Bukkit.getLogger().info("[DEV] Region: " + region.getId() + " found and loaded!");
                }
            });
        });
    }

    public ArrayList<ProtectedRegion> getPlots(World world, double prizeRange) {
        ArrayList<ProtectedRegion> regions = new ArrayList<>();
        huizenMarktRegions.forEach(region -> {
            int prize = region.getFlag(RMT_PLOTS_PRICE);
            if(prizeRange < prize) return;
            regions.add(region);
        });
        return regions;
    }

    public ProtectedRegion getRegion(Location location) {
        RegionManager regionManager = worldGuard.getRegionManager(location.getWorld());
        if (regionManager == null) return null;
        ProtectedRegion region = null;
        try {
            region = regionManager.getApplicableRegions(location).getRegions().iterator().next();
        } catch (Exception e) {
            return null;
        }
        return region;
    }



    /**
     * Getter Methods
     */
    public static PlotUtils getInstance() {
        if (instance == null) instance = new PlotUtils();
        return instance;
    }

    public List<Flag> getWorldGuardFlags() {
        return worldGuardFlags;
    }

}
