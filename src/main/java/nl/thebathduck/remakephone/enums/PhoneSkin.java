package nl.thebathduck.remakephone.enums;

import lombok.Getter;
import nl.thebathduck.remakephone.utils.ChatUtils;
import nl.thebathduck.remakephone.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum PhoneSkin {
    DEFAULT("Telefoon", "lobby.use", "normal_phone"),
    BLAUW_PHONE("Blauwe Telefoon", "phoneskin.blauw_phone", "blauw_phone"),
    GEEL_PHONE("Gele Telefoon", "phoneskin.geel_phone", "geel_phone"),
    GROEN_PHONE("Groene Telefoon", "phoneskin.groen_phone", "groen_phone"),
    ROOD_PHONE("Rode Telefoon", "phoneskin.rood_phone", "rood_phone"),
    ROZE_PHONE("Roze Telefoon", "phoneskin.roze_phone", "roze_phone"),
    BLACKBERRY("Blackberry", "phoneskin.blackberry", "blackberry"),
    BLUEBLAZEPHONE("Blue Blaze Phone", "phoneskin.blueblazephone", "blueblazephone"),
    ORANGEBLAZEPHONE("Orange Blaze Phone", "phoneskin.orangeblazephone", "orangeblazephone"),
    SILVERBLAZEPHONE("Zilver Blaze Phone", "phoneskin.silverblazephone", "silverblazephone"),
    PINKBLAZEPHONE("Roze Blaze Phone", "phoneskin.pinkblazephone", "pinkblazephone"),
    ERICSSONGA628("Erics Songa 628", "phoneskin.ericssonga628", "ericssonga628"),
    ERICSSONGA628_RED("Erics Songa 628 Rood", "phoneskin.ericssonga628_red", "ericssonga628_red"),
    ERICSSONGA628_TURQOISE("Erics Songa 628 Turqoise", "phoneskin.ericssonga628_turqoise", "ericssonga628_turqoise"),
    ERICSSONW810("Erics Sonw 810", "phoneskin.ericssonw810", "ericssonw810"),
    FULLBLACK_TELEFOON("Fullblack Telefoon", "phoneskin.fullblack_telefoon", "fullblack_telefoon"),
    FULLBLACK_RELIC("Fullblack Telefoon Relic", "phoneskin.fullblackrelic_telefoon", "fullblackrelic_telefoon"),
    NEW_IPHONE_BLACK("New iPhone Black", "phoneskin.newiphoneblack", "newiphoneblack"),
    NEW_IPHONE_BROKEN("New iPhone Broken", "phoneskin.newiphoneblack", "newiphonebroken"),
    NEW_IPHONE_BRONZEN("New iPhone Bronze", "phoneskin.newiphonebronze", "newiphonebronze"),
    NOKIA3310("Nokia 3310", "phoneskin.nokia3310", "nokia3310"),
    NOKIA3310_BLACK("Nokia 3310 Zwart", "phoneskin.nokia3310_black", "nokia3310_black"),
    NOKIA3310_GOLD("Nokia 3310 Goud", "phoneskin.nokia3310_gold", "nokia3310_gold"),
    NOKIAN95BLACK("Nokia N95 Zwart", "phoneskin.nokian95black", "nokian95black"),
    NOKIAN95SILVER("Nokia N95 Zilver", "phoneskin.nokian95silver", "nokian95silver"),
    SAMSUNG_GALAXY_BLACK("Samsung Galaxy Zwart", "phoneskin.samsunggalaxy_black", "samsunggalaxy_black"),
    SAMSUNG_GALAXY_WHITE("Samsung Galaxy Wit", "phoneskin.samsunggalaxy_white", "samsunggalaxy_white"),
    SAMSUNG_GALAXY_BROKENWHITE("Samsung Galaxy Wit (Kapot)", "phoneskin.samsunggalaxy_brokenwhite", "samsunggalaxy_brokenwhite");


    private @Getter String name;
    private @Getter String permission;
    private @Getter String nbt;
    PhoneSkin(String name, String permission, String nbt) {
        this.name = name;
        this.permission = permission;
        this.nbt = nbt;
    }


}