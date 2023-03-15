package nl.thebathduck.remakephone.menu;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import nl.thebathduck.remakephone.RemakePhone;
import nl.thebathduck.remakephone.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MarktMenu extends GUIHolder {

    public MarktMenu(Player player, int page) {

        this.inventory = Bukkit.createInventory(this, 54, ChatUtils.color("&cHuizenmarkt &7- &6Pagina ") + (page + 1));
        double balance = RemakePhone.getEconomy().getBalance(player);
        ArrayList<ProtectedRegion> regions = new ArrayList<>(PlotUtils.getInstance().getPlots(player.getWorld(), balance));
        int pageSize = 45;

        for (int i = 0; i < Math.min(regions.size() - page * pageSize, pageSize); i++) {
            int index = i + page * pageSize;
            ProtectedRegion region = regions.get(index);
            inventory.setItem(i, getHouseItem(region.getId(), region.getFlag(PlotUtils.getInstance().RMT_PLOTS_PRICE)));
        }

        open(player);
    }

    @Override
    public void onClick(InventoryClickEvent event) {

    }

    public ItemStack getHouseItem(String name, int price) {
        String headUrl = RemakePhone.getInstance().getConfig().getString("huizenmarkt.icon");
        ItemBuilder builder = new ItemBuilder(SkullUtils.skullUrl(headUrl));
        builder.setColoredName("&b" + name);
        builder.addLoreLine("&6€" + price);
        return builder.build();
    }
}
