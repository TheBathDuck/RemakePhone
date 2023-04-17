package nl.thebathduck.remakephone.menu;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.thebathduck.remakephone.managers.MessageManager;
import nl.thebathduck.remakephone.managers.PhoneManager;
import nl.thebathduck.remakephone.objects.Phone;
import nl.thebathduck.remakephone.objects.PhoneMessage;
import nl.thebathduck.remakephone.utils.ChatUtils;
import nl.thebathduck.remakephone.utils.GUIHolder;
import nl.thebathduck.remakephone.utils.ItemBuilder;
import nl.thebathduck.remakephone.utils.PlotUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MessagesListMenu extends GUIHolder {

    int page;


    public MessagesListMenu(Player player, int page) {
        this.page = page;
        Phone phone = PhoneManager.getInstance().getPhone(player.getUniqueId());
        this.inventory = Bukkit.createInventory(this, 6 * 9, "Berichten - 06-" + phone.getNumber() + " (" + (page + 1) + ")");
        open(player);

        Comparator<PhoneMessage> comparator = Comparator.comparing(PhoneMessage::getTime).reversed();
        Set<PhoneMessage> sortedMessages = new TreeSet<>(comparator);
        sortedMessages.addAll(phone.getMessages().values());
        List<PhoneMessage> sortedFinal = new ArrayList<>(sortedMessages);


        int pageSize = 4 * 9;
        for (int i = 36; i <= 36 + 8; i++) {
            ItemStack filler = new ItemBuilder(Material.STAINED_GLASS_PANE).setColoredName(" ").setDurability((short) 9).build();
            inventory.setItem(i, filler);
        }

        for (int i = 0; i < Math.min(sortedFinal.size() - page * pageSize, pageSize); i++) {
            int index = i + page * pageSize;
            PhoneMessage message = sortedFinal.get(index);
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
            inventory.setItem(i, builder.build());
        }

        if (page > 0) {
            this.inventory.setItem(47, new ItemBuilder(Material.SPECTRAL_ARROW)
                    .setColoredName("&cVorige Pagina")
                    .setNBT("goto", (page - 1))
                    .build()
            );
        }

        if(sortedFinal.size() - page * pageSize > pageSize) {
            this.inventory.setItem(51, new ItemBuilder(Material.SPECTRAL_ARROW)
                    .setColoredName("&aVolgende Pagina")
                    .setNBT("goto", (page + 1))
                    .build()
            );
        }
    }


    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType().equals(Material.AIR)) return;
        event.setCancelled(true);
        if (event.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) return;
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem().getType().equals(Material.SPECTRAL_ARROW)) {
            int pageNumber = NBTEditor.getInt(event.getCurrentItem(), "goto");
            new MessagesListMenu(player, pageNumber);
            return;
        }

        if(!NBTEditor.contains(event.getCurrentItem(), "messageUuid")) return;
        UUID messageUuid = UUID.fromString(NBTEditor.getString(event.getCurrentItem(), "messageUuid"));
        Phone phone = PhoneManager.getInstance().getPhone(player.getUniqueId());
        PhoneMessage message = phone.getMessage(messageUuid);

        if(event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            MessageManager.getInstance().deleteMessage(phone, messageUuid);
            player.sendMessage(ChatUtils.color("&3Bericht verwijderd."));
            return;
        }

        message.setRead(!message.getRead());
        MessageManager.getInstance().setRead(messageUuid, message.getRead());
        new MessagesListMenu(player, 0);
    }
}
