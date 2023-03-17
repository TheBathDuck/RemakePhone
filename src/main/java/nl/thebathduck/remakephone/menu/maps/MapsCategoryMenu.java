package nl.thebathduck.remakephone.menu.maps;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.thebathduck.remakephone.enums.PhoneIcon;
import nl.thebathduck.remakephone.managers.NavigationManager;
import nl.thebathduck.remakephone.objects.maps.NavigationCategory;
import nl.thebathduck.remakephone.utils.ChatUtils;
import nl.thebathduck.remakephone.utils.GUIHolder;
import nl.thebathduck.remakephone.utils.ItemBuilder;
import nl.thebathduck.remakephone.utils.SkullUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MapsCategoryMenu extends GUIHolder {

    public MapsCategoryMenu(Player player) {
        this.inventory = Bukkit.createInventory(this, 6 * 9, ChatUtils.color("&9Remake&aMaps &6- &8Categorieën"));
        loadUI();

        for (NavigationCategory category : NavigationManager.getInstance().getCategories().values()) {
            String stripped = ChatUtils.strip(category.getDisplayName());
            ItemBuilder builder = new ItemBuilder(SkullUtils.skullUrl(category.getIconUrl()));
            builder.setColoredName(category.getDisplayName());
            builder.setNBT("category", category.getName());
            builder.addLoreLine("&6Klik hier om alle locaties");
            builder.addLoreLine("&6van de categorie &a" + stripped);
            builder.addLoreLine("&6te zien.");
            inventory.addItem(builder.build());
        }

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

        String categoryNbt = NBTEditor.getString(event.getCurrentItem(), "category");
        NavigationCategory category = NavigationManager.getInstance().getCategories().get(categoryNbt);
        new MapsCategoryViewMenu(player, category);
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
