package nl.thebathduck.remakephone.menu.grinding;

import be.razerstorm.remakegrinding.RemakeGrinding;
import be.razerstorm.remakegrinding.objects.PlayerData;
import nl.thebathduck.remakephone.enums.PhoneIcon;
import nl.thebathduck.remakephone.menu.MainPhoneMenu;
import nl.thebathduck.remakephone.utils.GUIHolder;
import nl.thebathduck.remakephone.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GrindingStatsMenu extends GUIHolder {

    private Player player;

    public GrindingStatsMenu(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, 2 * 9, "Je grindinfo");
        PlayerData grindData = RemakeGrinding.getInstance().getCache().get(player.getUniqueId().toString());

        int level = RemakeGrinding.getInstance().getLevelFromXP(grindData.getGrindXP());
        int requiredXP = RemakeGrinding.getInstance().getXpLevels().get(level + 1);

        inventory.addItem(new ItemBuilder(Material.GOLD_NUGGET)
                .setColoredName("&6Grind Coins")
                .addLoreLine("&7Grindcoins: &e" + grindData.getGrindCoins())
                .build()
        );

        inventory.addItem(new ItemBuilder(Material.EXP_BOTTLE)
                .setColoredName("&6Grinding Level")
                .addLoreLine("&7Grindlevel: &e" + grindData.getGrindCoins())
                .addLoreLine("&7Volgend level: &e" + grindData.getGrindXP() + "&7/&e" + requiredXP)
                .build()
        );

        inventory.addItem(new ItemBuilder(Material.STONE_PICKAXE)
                .setColoredName("&6Mining Info")
                .addLoreLine("&7Blocks gemined: &e" + grindData.getTotalMined())
                .build()
        );

        inventory.addItem(new ItemBuilder(Material.FISHING_ROD)
                .setColoredName("&6Fishing Info")
                .addLoreLine("&7Vissen gevangen: &e" + grindData.getTotalFished())
                .build()
        );


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

        if(event.getCurrentItem().getType().equals(Material.IRON_INGOT)) {
            new MainPhoneMenu(player);
        }
    }
}
