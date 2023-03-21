package nl.thebathduck.remakephone.menu.grinding;

import nl.thebathduck.remakephone.enums.PhoneIcon;
import nl.thebathduck.remakephone.menu.MainPhoneMenu;
import nl.thebathduck.remakephone.utils.GUIHolder;
import nl.thebathduck.remakephone.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GrindingSkinSelectionMenu extends GUIHolder {

    private Player player;

    public GrindingSkinSelectionMenu(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, 2 * 9, "Waar wil je de skin van aanpassen?");

        inventory.addItem(new ItemBuilder(Material.STONE_PICKAXE).setColoredName("&6Pickaxe Skins").build());
        inventory.addItem(new ItemBuilder(Material.FISHING_ROD).setColoredName("&6Fishing Skins").build());

        inventory.setItem(13, new ItemBuilder(PhoneIcon.HOME.getMaterial())
                .setNBT(PhoneIcon.HOME.getKey(), PhoneIcon.HOME.getValue())
                .setColoredName("&6Hoofdmenu")
                .setItemFlags()
                .build()
        );

        open(player);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType().equals(Material.AIR)) return;
        event.setCancelled(true);

        Material material = event.getCurrentItem().getType();

        switch (material) {
            case STONE_PICKAXE:
                new GrindingPickaxeSkinsMenu(player);
                break;
            case FISHING_ROD:
                new GrindingFishingSkinsMenu(player);
                break;
            case IRON_INGOT:
                new MainPhoneMenu(player);
                break;
            default:
                player.closeInventory();
                break;
        }
    }
}
