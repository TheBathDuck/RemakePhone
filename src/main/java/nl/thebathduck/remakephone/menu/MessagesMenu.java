package nl.thebathduck.remakephone.menu;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.thebathduck.remakephone.listeners.chat.MessagesChatListener;
import nl.thebathduck.remakephone.managers.PhoneManager;
import nl.thebathduck.remakephone.objects.Phone;
import nl.thebathduck.remakephone.utils.GUIHolder;
import nl.thebathduck.remakephone.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.UUID;

public class MessagesMenu extends GUIHolder {

    public MessagesMenu(Player player) {
        Phone phone = PhoneManager.getInstance().getPhone(player.getUniqueId());
        this.inventory = Bukkit.createInventory(this, 6 * 9, "Berichten - 06-" + phone.getNumber());
        open(player);

        phone.getContacts().forEach(contact -> {
            OfflinePlayer ofp = Bukkit.getOfflinePlayer(contact.getOwner());
            ItemBuilder contactBuilder = new ItemBuilder(Material.PAPER);
            contactBuilder.setColoredName("&b" + ofp.getName());
            contactBuilder.setNBT("targetNumber", contact.getNumber());
            contactBuilder.setNBT("targetUuid", contact.getOwner().toString());
            contactBuilder.addLoreLine("&306-" + contact.getNumber());
            if (ofp != null || ofp.hasPlayedBefore() || ofp.isOnline()) {
                contactBuilder.setType(Material.SKULL_ITEM);
                contactBuilder.setDurability((byte) 3);
                contactBuilder.setSkull(ofp);
            }
            inventory.addItem(contactBuilder.build());
        });


    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType().equals(Material.AIR)) return;
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        if(NBTEditor.contains(event.getCurrentItem(), "targetNumber") && NBTEditor.contains(event.getCurrentItem(), "targetUuid")) {
            UUID uuid = UUID.fromString(NBTEditor.getString(event.getCurrentItem(), "targetUuid"));
            int number = NBTEditor.getInt(event.getCurrentItem(), "targetNumber");
            MessagesChatListener.startListener(player, uuid, number);
            player.closeInventory();
            return;
        }
    }

}
