package nl.thebathduck.remakephone.menu;

import nl.thebathduck.remakephone.managers.PhoneManager;
import nl.thebathduck.remakephone.objects.Phone;
import nl.thebathduck.remakephone.objects.PhoneMessage;
import nl.thebathduck.remakephone.utils.GUIHolder;
import nl.thebathduck.remakephone.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class OldMessages extends GUIHolder  {

    public OldMessages(Player player) {
        Phone phone = PhoneManager.getInstance().getPhone(player.getUniqueId());
        this.inventory = Bukkit.createInventory(this, 6 * 9, "Berichten - 06-" + phone.getNumber() + " (nigg)");

        HashMap<UUID, PhoneMessage> messages = phone.getMessages();
        List<UUID> uuids = new ArrayList<>(messages.keySet());

        PhoneMessage testMessage = messages.get(uuids.get(0));

        for(int i = 0; i<50; i++) {
            PhoneMessage middelMan = testMessage;
            middelMan.setMessage("hahahahahaha " + i);
            messages.put(middelMan.getUuid(), middelMan);
        }

        messages.forEach((uuid, message) -> {
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

        open(player);
    }

    @Override
    public void onClick(InventoryClickEvent event) {

    }
}
