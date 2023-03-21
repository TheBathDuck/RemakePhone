package nl.thebathduck.remakephone.menu.grinding;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.thebathduck.remakephone.enums.grinding.GrindingFishingSkin;
import nl.thebathduck.remakephone.enums.grinding.GrindingPickaxeSkin;
import nl.thebathduck.remakephone.utils.ChatUtils;
import nl.thebathduck.remakephone.utils.GUIHolder;
import nl.thebathduck.remakephone.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GrindingFishingSkinsMenu extends GUIHolder {

    private Player player;

    public GrindingFishingSkinsMenu(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, 6 * 9, "Selecteer je vishengel skin.");

        for (GrindingFishingSkin skin : GrindingFishingSkin.values()) {
            ItemBuilder builder = new ItemBuilder(Material.FISHING_ROD);
            builder.setColoredName("&3" + skin.getName());
            builder.setItemFlags();

            builder.addLoreLine("");
            builder.addLoreLine("&7Ontgrendeld: " + (player.hasPermission(skin.getPermission()) ? "&aJa." : "&cNee."));
            builder.addLoreLine("");
            builder.addLoreLine("&7Klik om deze skin toe te");
            builder.addLoreLine("&7passen op je tool.");

            builder.setNBT("mtwcustom", skin.getNbt());
            builder.setNBT("skin", skin.toString().toUpperCase());
            inventory.addItem(builder.build());
        }

        open(player);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType().equals(Material.AIR)) return;
        event.setCancelled(true);

        String nbtSkin = NBTEditor.getString(event.getCurrentItem(), "skin");
        if(nbtSkin == null) return;

        GrindingFishingSkin skin = GrindingFishingSkin.valueOf(nbtSkin.toUpperCase());
        String skinName = ChatColor.stripColor(skin.getName());
        player.sendMessage(ChatUtils.color("&3Je hebt je &bvishengel &3skin veranderd naar &b" + skinName));

        Inventory inv = player.getInventory();
        for (int i = 0; i <= 35; i++) {
            ItemStack item = inv.getItem(i);
            if(item == null) continue;
            if(!item.getType().equals(Material.FISHING_ROD)) continue;

            ItemBuilder skinBuilder = new ItemBuilder(item);
            skinBuilder.setColoredName("&f" + skin.getName());
            skinBuilder.setNBT("mtwcustom", skin.getNbt());
            inv.setItem(i, skinBuilder.build());
        }
    }

}
