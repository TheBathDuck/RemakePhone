package nl.thebathduck.remakephone.menu;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.thebathduck.remakephone.RemakePhone;
import nl.thebathduck.remakephone.enums.PhoneIcon;
import nl.thebathduck.remakephone.listeners.chat.AddContactListener;
import nl.thebathduck.remakephone.managers.PhoneManager;
import nl.thebathduck.remakephone.objects.Phone;
import nl.thebathduck.remakephone.objects.PhoneMessage;
import nl.thebathduck.remakephone.utils.ChatUtils;
import nl.thebathduck.remakephone.utils.GUIHolder;
import nl.thebathduck.remakephone.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ContactsMenu extends GUIHolder {


    public ContactsMenu(Player player) {
        Phone phone = PhoneManager.getInstance().getPhone(player.getUniqueId());
        this.inventory = Bukkit.createInventory(this, 6 * 9, "Contacten van 06-" + phone.getNumber());

        ItemBuilder menuBuilder = new ItemBuilder(Material.IRON_INGOT);
        menuBuilder.setNBT(PhoneIcon.CONTACT_ADD.getKey(), PhoneIcon.CONTACT.getValue());
        menuBuilder.setNBT("action", "addcontact");
        menuBuilder.setColoredName("&aVoeg contact toe.");
        inventory.setItem(49, menuBuilder.build());
        open(player);

        inventory.setItem(22, new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 14).setColoredName("&cContacten inladen..").build());

        Bukkit.getScheduler().runTaskAsynchronously(RemakePhone.getInstance(), () -> {
            inventory.setItem(22, null);
            phone.getContacts().values().forEach(contact -> {
                OfflinePlayer ofp = Bukkit.getOfflinePlayer(contact.getOwner());
                ItemBuilder contactBuilder = new ItemBuilder(Material.PAPER);
                contactBuilder.setColoredName("&b" + ofp.getName());
                contactBuilder.addLoreLine("&306-" + contact.getNumber());
                contactBuilder.addLoreLine("");
                contactBuilder.addLoreLine("&7Klik om te verwijderen.");
                contactBuilder.setNBT("action", "removecontact");
                contactBuilder.setNBT("number", contact.getNumber());
                if (ofp != null || ofp.hasPlayedBefore() || ofp.isOnline()) {
                    contactBuilder.setType(Material.SKULL_ITEM);
                    contactBuilder.setDurability((byte) 3);
                    contactBuilder.setSkull(ofp);
                }
                inventory.addItem(contactBuilder.build());

            });
        });

    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType().equals(Material.AIR)) return;
        event.setCancelled(true);
        if (NBTEditor.getString(event.getCurrentItem(), "action") == null) return;
        Player player = (Player) event.getWhoClicked();
        Phone phone = PhoneManager.getInstance().getPhone(player.getUniqueId());
        ItemStack item = event.getCurrentItem();
        String action = NBTEditor.getString(item, "action");

        if (action.equals("addcontact")) {
            player.closeInventory();
            player.sendMessage(ChatUtils.color("&3Voer het telefoon nummer in de chat of type annuleer om te annuleren."));
            AddContactListener.getListening().add(player.getUniqueId());
            return;
        }

        if(action.equals("removecontact")) {
            int number = NBTEditor.getInt(item, "number");
            new ContactRemoveConfirm(player, number);
            return;
        }

        player.sendMessage(ChatUtils.color("&cEr ging iets mis."));

    }
}
