package nl.thebathduck.remakephone.listeners;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.thebathduck.remakephone.enums.PhoneSkin;
import nl.thebathduck.remakephone.managers.PhoneManager;
import nl.thebathduck.remakephone.objects.Phone;
import nl.thebathduck.remakephone.utils.ChatUtils;
import nl.thebathduck.remakephone.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public class RemakePhoneListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        String rmtPhoneItem = NBTEditor.getString(event.getCurrentItem(), "rmtphoneitem");
        if (rmtPhoneItem == null) return;
        if (!rmtPhoneItem.equals("true")) return;
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        player.updateInventory();
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();
        if (item == null) return;
        String rmtphoneitem = NBTEditor.getString(item, "rmtphoneitem");
        if (rmtphoneitem == null) return;
        if (!rmtphoneitem.equals("true")) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onItemSwap(PlayerSwapHandItemsEvent event) {
        ItemStack hand = event.getOffHandItem();
        String nbt = NBTEditor.getString(hand, "rmtphoneitem");

        if (nbt == null) return;
        if (!nbt.equals("true")) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void ifThisCriesForTpsDropsStfuCLOSE(InventoryCloseEvent event) {
        checkForDupe((Player) event.getPlayer());
    }

    @EventHandler
    public void ifThisCriesForTpsDropsStfuOPEN(InventoryOpenEvent event) {
        checkForDupe((Player) event.getPlayer());
    }

    public void setPhoneItem(Player player, Phone phone) {
        PhoneSkin skin = phone.getSkin();

        ItemBuilder phoneItem = new ItemBuilder(Material.BLAZE_POWDER);
        phoneItem.setColoredName("&b" + skin.getName());
        phoneItem.addLoreLine("&3Nummer: &b06-" + phone.getNumber());
        phoneItem.setNBT("rmtphoneitem", "true");
        phoneItem.setNBT("mtwcustom", skin.getNbt());
        phoneItem.setItemFlags();
        player.getInventory().setItem(8, phoneItem.build());

    }

    @EventHandler
    public void onGamemodeSwitch(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        setPhoneItem(event.getPlayer(), PhoneManager.getInstance().getPhone(player.getUniqueId()));
    }

    public void checkForDupe(Player player) {
        for (int i = 0; i <= 35; i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item == null) continue;
            if (item.getType().equals(Material.AIR)) continue;
            String nbt = NBTEditor.getString(item, "rmtphoneitem");
            if (nbt == null) continue;
            if (!nbt.equals("true")) continue;
            if (i == 8) continue;
            player.getInventory().setItem(i, new ItemStack(Material.AIR));
            setPhoneItem(player, PhoneManager.getInstance().getPhone(player.getUniqueId()));
            player.sendMessage(ChatUtils.color("&cHet leek erop alsof je een gedupliceerde telefoon had, je telefoon is teruggezet."));
        }
    }
}
