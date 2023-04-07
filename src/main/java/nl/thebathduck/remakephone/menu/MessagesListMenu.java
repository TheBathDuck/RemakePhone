package nl.thebathduck.remakephone.menu;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.thebathduck.remakephone.managers.MessageManager;
import nl.thebathduck.remakephone.managers.PhoneManager;
import nl.thebathduck.remakephone.objects.Phone;
import nl.thebathduck.remakephone.objects.PhoneMessage;
import nl.thebathduck.remakephone.utils.ChatUtils;
import nl.thebathduck.remakephone.utils.GUIHolder;
import nl.thebathduck.remakephone.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class MessagesListMenu extends GUIHolder {

    public MessagesListMenu(Player player) {
        Phone phone = PhoneManager.getInstance().getPhone(player.getUniqueId());
        this.inventory = Bukkit.createInventory(this, 6 * 9, "Berichten - 06-" + phone.getNumber());
        open(player);
        reload(player);
    }

    public void reload(Player player) {
        this.inventory.clear();
        Phone phone = PhoneManager.getInstance().getPhone(player.getUniqueId());
        Comparator<PhoneMessage> comparator = Comparator.comparing(PhoneMessage::getTime).reversed();
        Set<PhoneMessage> sortedMessages = new TreeSet<>(comparator);
        sortedMessages.addAll(phone.getMessages().values());

        sortedMessages.forEach(message -> {
            ItemBuilder builder = new ItemBuilder(Material.BOOK);
            builder.setColoredName("&3Bericht:");
            builder.addLoreLine("&b" + ChatColor.stripColor(message.getMessage()));
            builder.addLoreLine("");
            builder.addLoreLine("&3Verstuurder:");
            builder.addLoreLine("&b06-" + message.getSender());
            builder.addLoreLine("");
            builder.addLoreLine("&3Verstuurd op:");
            builder.addLoreLine("&b" + message.getDate());
            builder.addLoreLine("");

            if(message.getRead()) {
                builder.setType(Material.WRITTEN_BOOK);
                builder.addLoreLine("&7Klik hier om hem als ongelezen te markeren.");
            } else {
                builder.addLoreLine("&7Klik hier om hem als gelezen te markeren.");
            }
            builder.addLoreLine("&7Shift klik om te verwijderen");

            builder.setItemFlags();
            builder.setNBT("messageUuid", message.getUuid().toString());
            inventory.addItem(builder.build());
        });
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType().equals(Material.AIR)) return;
        if(!NBTEditor.contains(event.getCurrentItem(), "messageUuid")) return;
        event.setCancelled(true);

        UUID messageUuid = UUID.fromString(NBTEditor.getString(event.getCurrentItem(), "messageUuid"));
        Player player = (Player) event.getWhoClicked();
        Phone phone = PhoneManager.getInstance().getPhone(player.getUniqueId());
        PhoneMessage message = phone.getMessage(messageUuid);

        if(event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            MessageManager.getInstance().deleteMessage(phone, messageUuid);
            player.sendMessage(ChatUtils.color("&3Bericht verwijderd."));
            reload(player);
            return;
        }

        message.setRead(!message.getRead());
        MessageManager.getInstance().setRead(messageUuid, message.getRead());


        reload(player);
    }
}
