package nl.thebathduck.remakephone.menu;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.thebathduck.remakephone.enums.PhoneIcon;
import nl.thebathduck.remakephone.managers.PhoneManager;
import nl.thebathduck.remakephone.menu.maps.MapsPhoneMenu;
import nl.thebathduck.remakephone.objects.Phone;
import nl.thebathduck.remakephone.utils.GUIHolder;
import nl.thebathduck.remakephone.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class MainPhoneMenu extends GUIHolder {

    public MainPhoneMenu(Player player) {
        Phone phone = PhoneManager.getInstance().getPhone(player.getUniqueId());

        this.inventory = Bukkit.createInventory(this, InventoryType.SHULKER_BOX, "06-" + phone.getNumber());
        inventory.clear();

        inventory.setItem(2, new ItemBuilder(PhoneIcon.CALL.getMaterial())
                .setNBT(PhoneIcon.CALL.getKey(), PhoneIcon.CALL.getValue())
                .setColoredName("&9Bellen")
                .addLoreLine("&7Versie: 1.0.0")
                .setItemFlags()
                .build()
        );

        inventory.setItem(3, new ItemBuilder(PhoneIcon.NEWS.getMaterial())
                .setNBT(PhoneIcon.NEWS.getKey(), PhoneIcon.NEWS.getValue())
                .setColoredName("&9Remake&cAlerts")
                .addLoreLine("&7Vandaag zijn er (nog) geen")
                .addLoreLine("&9Remake&cAlerts &7geplaatst!")
                .setItemFlags()
                .build()
        );

        inventory.setItem(4, new ItemBuilder(PhoneIcon.MAPS.getMaterial())
                .setNBT(PhoneIcon.MAPS.getKey(), PhoneIcon.MAPS.getValue())
                .setColoredName("&9Remake&aMaps")
                .addLoreLine("&7Versie: 1.0.5")
                .setNBT("menu", "maps")
                .setItemFlags()
                .build()
        );

        inventory.setItem(5, new ItemBuilder(PhoneIcon.VEILING.getMaterial())
                .setNBT(PhoneIcon.VEILING.getKey(), PhoneIcon.VEILING.getValue())
                .setColoredName("&cVeiling &6(Coming Soon)")
                .addLoreLine("&7Versie: 1.0.0")
                .setItemFlags()
                .build()
        );

        inventory.setItem(6, new ItemBuilder(Material.CHEST)
                .setColoredName("&bInstellingen")
                .setItemFlags()
                .build()
        );

        inventory.setItem(11, new ItemBuilder(PhoneIcon.CONTACT.getMaterial())
                .setNBT(PhoneIcon.CONTACT.getKey(), PhoneIcon.CONTACT.getValue())
                .setColoredName(" ")
                .build()
        );

        inventory.setItem(12, new ItemBuilder(PhoneIcon.WHATSAPP.getMaterial())
                .setNBT(PhoneIcon.WHATSAPP.getKey(), PhoneIcon.WHATSAPP.getValue())
                .setColoredName(" ")
                .build()
        );

        inventory.setItem(13, new ItemBuilder(PhoneIcon.HUIZENMARKT.getMaterial())
                .setNBT(PhoneIcon.HUIZENMARKT.getKey(), PhoneIcon.HUIZENMARKT.getValue())
                .setColoredName(" ")
                .build()
        );


        inventory.setItem(14, new ItemBuilder(PhoneIcon.SKINS.getMaterial())
                .setNBT(PhoneIcon.SKINS.getKey(), PhoneIcon.SKINS.getValue())
                .setColoredName("&cTelefoon Skins")
                .setNBT("menu", "skin")
                .setItemFlags()
                .build()
        );

        inventory.setItem(15, new ItemBuilder(Material.FLINT)
                .setNBT(PhoneIcon.HUIZENMARKT.getKey(), PhoneIcon.HUIZENMARKT.getValue())
                .setColoredName("&4Bug Report")
                .build()
        );

        inventory.setItem(20, new ItemBuilder(PhoneIcon.BELTEGOED.getMaterial())
                .setNBT(PhoneIcon.BELTEGOED.getKey(), PhoneIcon.BELTEGOED.getValue())
                .setColoredName("&8Beltegoed")
                .addLoreLine("&7Je hebt momenteel &8€" + phone.getCredit() + " &7beltegoed")
                .build()
        );

        inventory.setItem(21, new ItemBuilder(PhoneIcon.MAIL.getMaterial())
                .setNBT(PhoneIcon.MAIL.getKey(), PhoneIcon.MAIL.getValue())
                .setColoredName(" ")
                .build()
        );

        inventory.setItem(22, new ItemBuilder(PhoneIcon.BOOSTERS.getMaterial())
                .setNBT(PhoneIcon.BOOSTERS.getKey(), PhoneIcon.BOOSTERS.getValue())
                .setColoredName(" ")
                .build()
        );

        open(player);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType().equals(Material.AIR)) return;
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        String menu = NBTEditor.getString(event.getCurrentItem(), "menu");
        if (menu == null) return;

        switch (menu) {
            case "skin":
                new SkinPhoneMenu(player);
                break;
            case "maps":
                new MapsPhoneMenu(player);
                break;
            default:
                return;
        }

    }


}
