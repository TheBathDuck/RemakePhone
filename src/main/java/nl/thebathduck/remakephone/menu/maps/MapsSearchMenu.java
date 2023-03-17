package nl.thebathduck.remakephone.menu.maps;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.thebathduck.remakephone.enums.PhoneIcon;
import nl.thebathduck.remakephone.managers.NavigationManager;
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

public class MapsSearchMenu extends GUIHolder {

    public MapsSearchMenu(Player player, String result) {
        this.inventory = Bukkit.createInventory(this, 6 * 9, ChatUtils.color("&9Remake&aMaps &6- &8Resultaten"));
        loadUI();


        NavigationManager manager = NavigationManager.getInstance();
        manager.getCategories().values().forEach(category -> {
            category.getLocations().values().forEach(location -> {
                if (location.getName().contains(result)) {
                    inventory.addItem(generateIcon(location));
                }
            });
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
        if (action != null) {
            new MapsPhoneMenu(player);
            return;
        }

        String location = NBTEditor.getString(event.getCurrentItem(), "location");
        new MapsStartMenu(player, NavigationManager.getInstance().getNavigationLocation(location));
    }

    private ItemStack generateIcon(NavigationLocation location) {

        ItemBuilder builder = new ItemBuilder(SkullUtils.skullUrl(location.getIconUrl()));
        builder.setNBT(PhoneIcon.GPS_CATEGORY.getKey(), PhoneIcon.GPS_CATEGORY.getValue());
        builder.setColoredName(location.getDisplayName());
        builder.addLoreLine("&7Klik hier om je navigatie te starten!");
        builder.setNBT("location", location.getName());

        return builder.build();
    }

    public void loadUI() {
        for (int i = 45; i < 45 + 9; i++) {
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
