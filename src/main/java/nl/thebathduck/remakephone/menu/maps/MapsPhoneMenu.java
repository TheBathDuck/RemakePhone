package nl.thebathduck.remakephone.menu.maps;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.thebathduck.remakephone.RemakePhone;
import nl.thebathduck.remakephone.enums.PhoneIcon;
import nl.thebathduck.remakephone.listeners.MapsChatListener;
import nl.thebathduck.remakephone.menu.MainPhoneMenu;
import nl.thebathduck.remakephone.utils.ChatUtils;
import nl.thebathduck.remakephone.utils.GUIHolder;
import nl.thebathduck.remakephone.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MapsPhoneMenu extends GUIHolder {
    public MapsPhoneMenu(Player player) {
        this.inventory = Bukkit.createInventory(this, 2 * 9, ChatUtils.color("&9Remake&aMaps &6- &8Hoofdmenu"));

        inventory.setItem(0, new ItemBuilder(PhoneIcon.GPS_SEARCH.getMaterial())
                .setNBT(PhoneIcon.GPS_SEARCH.getKey(), PhoneIcon.GPS_SEARCH.getValue())
                .setNBT("submenu", "searchMenu")
                .setColoredName("&6Zoek op letter")
                .addLoreLine("&7Zoek een bepaalde locatie op letter.")
                .setItemFlags()
                .build()
        );

        inventory.setItem(1, new ItemBuilder(PhoneIcon.GPS_CATEGORY.getMaterial())
                .setNBT(PhoneIcon.GPS_CATEGORY.getKey(), PhoneIcon.GPS_CATEGORY.getValue())
                .setNBT("submenu", "category")
                .setColoredName("&6Categorieën")
                .setItemFlags()
                .build()
        );

        inventory.setItem(13, new ItemBuilder(PhoneIcon.HOME.getMaterial())
                .setNBT(PhoneIcon.HOME.getKey(), PhoneIcon.HOME.getValue())
                .setNBT("submenu", "home")
                .setColoredName("&6Hoofdmenu")
                .setItemFlags()
                .build()
        );

        if (RemakePhone.getGPS().gpsIsActive(player)) {
            inventory.setItem(8, new ItemBuilder(PhoneIcon.GPS_STOP.getMaterial())
                    .setNBT(PhoneIcon.GPS_STOP.getKey(), PhoneIcon.GPS_STOP.getValue())
                    .setColoredName("&cStop navigatie")
                    .setNBT("submenu", "stopnav")
                    .build()
            );
        }


        open(player);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType().equals(Material.AIR)) return;
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        String submenu = NBTEditor.getString(event.getCurrentItem(), "submenu");

        switch (submenu) {
            case "home":
                new MainPhoneMenu(player);
                break;
            case "searchMenu":
                MapsChatListener.getListening().add(player.getUniqueId());
                player.closeInventory();
                player.sendMessage(ChatUtils.color("&aType in de chat waar je op wilt zoeken, op basis van de letters worden zoek resultaten getoond."));
                break;
            case "stopnav":
                player.closeInventory();
                player.sendMessage(ChatUtils.color("&cNavigatie gestopt."));
                RemakePhone.getGPS().stopGPS(player);
                break;
            case "category":
                new MapsCategoryMenu(player);
                break;
            default:
                return;
        }

    }

}
