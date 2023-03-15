package nl.thebathduck.remakephone.menu.maps;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.thebathduck.remakephone.RemakePhone;
import nl.thebathduck.remakephone.enums.PhoneIcon;
import nl.thebathduck.remakephone.objects.maps.NavigationLocation;
import nl.thebathduck.remakephone.utils.ChatUtils;
import nl.thebathduck.remakephone.utils.GUIHolder;
import nl.thebathduck.remakephone.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MapsStartMenu extends GUIHolder {

    public MapsStartMenu(Player player, NavigationLocation location) {
        this.inventory = Bukkit.createInventory(this, 9, ChatUtils.color("&9World&aMaps &6- &8") + ChatUtils.strip(location.getDisplayName()));

        inventory.setItem(0, new ItemBuilder(PhoneIcon.GPS_START.getMaterial())
                .setNBT(PhoneIcon.GPS_START.getKey(), PhoneIcon.GPS_START.getValue())
                .setNBT("action", location.getGpsValue())
                .setColoredName("&aNavigatie starten").build()
        );

        inventory.setItem(8, new ItemBuilder(PhoneIcon.GPS_STOP.getMaterial())
                .setNBT(PhoneIcon.GPS_STOP.getKey(), PhoneIcon.GPS_STOP.getValue())
                .setColoredName("&cAnnuleer")
                .setNBT("action", "cancel")
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
        String action = NBTEditor.getString(event.getCurrentItem(), "action");

        if(action == null) return;
        if(action.equals("cancel")) {
            player.closeInventory();
            return;
        }

        player.closeInventory();
        RemakePhone.getGPS().startGPS(player, action);
        player.sendMessage(ChatUtils.color("&aNavigatie gestart!"));

    }
}
