package nl.thebathduck.remakephone.menu.maps;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.thebathduck.remakephone.RemakePhone;
import nl.thebathduck.remakephone.enums.PhoneIcon;
import nl.thebathduck.remakephone.managers.NavigationManager;
import nl.thebathduck.remakephone.objects.maps.NavigationCategory;
import nl.thebathduck.remakephone.objects.maps.NavigationLocation;
import nl.thebathduck.remakephone.utils.ChatUtils;
import nl.thebathduck.remakephone.utils.GUIHolder;
import nl.thebathduck.remakephone.utils.ItemBuilder;
import nl.thebathduck.remakephone.utils.SkullUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MapsCategoryViewMenu extends GUIHolder {

    public MapsCategoryViewMenu(Player player, NavigationCategory category) {
        String stripped = ChatUtils.strip(category.getDisplayName());
        this.inventory = Bukkit.createInventory(this, 6 * 9, ChatUtils.color("&9Remake&aMaps &6- &8" + stripped));
        loadUI();

        category.getLocations().values().forEach(location -> {
            ItemBuilder builder = new ItemBuilder(SkullUtils.skullUrl(location.getIconUrl()));
            builder.setColoredName(location.getDisplayName());
            builder.addLoreLine("&6" + stripped);
            builder.setNBT("location", location.getName());
            inventory.addItem(builder.build());
        });

        open(player);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType().equals(Material.AIR)) return;
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();

        String action = NBTEditor.getString(event.getCurrentItem(), "action");
        if(action != null) {
            new MapsCategoryMenu(player);
            return;
        }

        String locationNbt = NBTEditor.getString(event.getCurrentItem(), "location");
        NavigationLocation location = NavigationManager.getInstance().getNavigationLocation(locationNbt);
        new MapsStartMenu(player, location);
    }

    public void loadUI() {
        for(int i = 45; i<45+9; i++) {
            ItemStack empty = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 0).setColoredName(" ").build();
            inventory.setItem(i, empty);

            ItemStack returnMenu = new ItemBuilder(PhoneIcon.HOME.getMaterial())
                    .setNBT(PhoneIcon.HOME.getKey(), PhoneIcon.HOME.getValue())
                    .setColoredName("&6Terug")
                    .setNBT("action", "return")
                    .build();
            inventory.setItem(49, returnMenu);
        }
    }

}
