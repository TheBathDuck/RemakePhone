package nl.thebathduck.remakephone.enums;

import lombok.Getter;
import nl.thebathduck.remakephone.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum MapLocation {

    POLITIE("Politie", "politie", MapUtils.genStack(Material.IRON_INGOT, "&9Politie", "phone_icon8")),
    TANKSTATION("Tankstation", "tankstation", MapUtils.genStack(Material.IRON_INGOT, "&9Tankstation", "phone_icon8")),
    VENDINGPLEIN("Vendingplein", "vendingplein", MapUtils.genStack(Material.IRON_INGOT, "&9Vending Plein", "phone_icon8")),
    SOAWB("Spawn", "spawn", MapUtils.genStack(Material.IRON_INGOT, "&6Spawn", "phone_icon8"));

    private @Getter String name;
    private @Getter ItemStack item;
    private @Getter String gpsValue;

    MapLocation(String name, String gpsValue, ItemStack item) {
        this.name = name;
        this.item = item;
        this.gpsValue = gpsValue;
    }


    public static class MapUtils {
        private static ItemStack genStack(Material material, String name, String nbt) {

            ItemBuilder builder = new ItemBuilder(material);
            builder.setItemFlags();
            builder.setColoredName(name);
            builder.setNBT("mtwcustom", nbt);

            return builder.build();
        }
    }
}
