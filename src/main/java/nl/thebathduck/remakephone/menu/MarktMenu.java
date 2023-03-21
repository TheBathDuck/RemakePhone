package nl.thebathduck.remakephone.menu;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.thebathduck.remakephone.RemakePhone;
import nl.thebathduck.remakephone.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class MarktMenu extends GUIHolder {

    public MarktMenu(Player player, int page) {

        this.inventory = Bukkit.createInventory(this, 6 * 9, ChatUtils.color("&cHuizenmarkt &7- &6Pagina ") + (page + 1));
        double balance = RemakePhone.getEconomy().getBalance(player);
        ArrayList<ProtectedRegion> regions = new ArrayList<>(PlotUtils.getInstance().getPlots(balance));
        int pageSize = 4 * 9;

        for (int i = 36; i <= 36 + 8; i++) {
            ItemStack filler = new ItemBuilder(Material.STAINED_GLASS_PANE).setColoredName(" ").setDurability((short) 9).build();
            inventory.setItem(i, filler);
        }

        for (int i = 0; i < Math.min(regions.size() - page * pageSize, pageSize); i++) {
            int index = i + page * pageSize;
            ProtectedRegion region = regions.get(index);
            inventory.setItem(i, getHouseItem(region, region.getId(), region.getFlag(PlotUtils.getInstance().RMT_PLOTS_PRICE)));
        }

        open(player);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType().equals(Material.AIR)) return;
        event.setCancelled(true);
        if (event.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) return;
        Player player = (Player) event.getWhoClicked();

        if (!NBTEditor.contains(event.getCurrentItem(), "huizenmarkt-region")) {
            player.sendMessage(ChatUtils.color("&cEr is geen gepaste GPS locatie gevonden voor dit plot."));
            return;
        }
        String gps = NBTEditor.getString(event.getCurrentItem(), "huizenmarkt-region");
        RemakePhone.getGPS().startGPS(player, gps);
    }

    public ItemStack getHouseItem(ProtectedRegion region, String name, int price) {
        String headUrl = RemakePhone.getInstance().getConfig().getString("huizenmarkt.icon");
        ItemBuilder builder = new ItemBuilder(SkullUtils.skullUrl(headUrl));

        if (region.getFlag(PlotUtils.getInstance().RMT_GPS_POINT) != null) {
            builder.setNBT("huizenmarkt-region", region.getFlag(PlotUtils.getInstance().RMT_GPS_POINT));
        }

        builder.setColoredName("&b" + name);
        builder.addLoreLine("&6€" + price);
        return builder.build();
    }
}
