package nl.thebathduck.remakephone.enums;

import lombok.Getter;
import nl.thebathduck.remakephone.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum PhoneIcon {
    CONTACT(Material.IRON_INGOT, "mtwcustom", "phone_icon1"),
    WHATSAPP(Material.IRON_INGOT, "mtwcustom", "phone_icon2"),
    MAIL(Material.IRON_INGOT, "mtwcustom", "phone_icon3"),
    BELTEGOED(Material.IRON_INGOT, "mtwcustom", "phone_icon4"),
    NEWS(Material.IRON_INGOT, "mtwcustom", "phone_icon5"),
    HUIZENMARKT(Material.IRON_INGOT, "mtwcustom", "phone_icon6"),

    CALL(Material.IRON_INGOT, "mtwcustom", "phone_icon7"),
    MAPS(Material.IRON_INGOT, "mtwcustom", "phone_icon8"),
    SKINS(Material.IRON_INGOT, "mtwcustom", "phone_icon9"),
    VEILING(Material.IRON_INGOT, "mtwcustom", "phone_icon10"),
    STEM(Material.IRON_INGOT, "mtwcustom", "phone_icon15"),
    BOOSTERS(Material.IRON_INGOT, "mtwcustom", "phone_icon14"),
    HOME(Material.IRON_INGOT, "mtwcustom", "phone_icon18"),


    GPS_SEARCH(Material.IRON_INGOT, "mtwcustom", "phone_icon23"),
    GPS_CATEGORY(Material.IRON_INGOT, "mtwcustom", "phone_icon24"),
    GPS_START(Material.IRON_INGOT, "mtwcustom", "phone_icon25"),
    GPS_STOP(Material.IRON_INGOT, "mtwcustom", "phone_icon26");



    private @Getter Material material;
    private @Getter String key;
    private @Getter String value;

    PhoneIcon(Material material, String key, String value) {
        this.material = material;
        this.key = key;
        this.value = value;
    }
}
