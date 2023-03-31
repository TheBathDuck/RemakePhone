package nl.thebathduck.remakephone.menu;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.thebathduck.remakephone.managers.PhoneManager;
import nl.thebathduck.remakephone.objects.Contact;
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

public class ContactRemoveConfirm extends GUIHolder {

    private Player player;
    private Phone phone;
    private Contact contact;

    public ContactRemoveConfirm(Player player, int number) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, 3 * 9, "Verwijder 06-" + number + "?");
        this.phone = PhoneManager.getInstance().getPhone(player.getUniqueId());
        this.contact = phone.getContact(number);

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(contact.getOwner());

        ItemBuilder skull = new ItemBuilder(Material.SKULL_ITEM, 1, (byte) 3);
        skull.setSkull(offlinePlayer);
        skull.setColoredName("&b" + offlinePlayer.getName());
        inventory.setItem(4, skull.build());

        ItemBuilder deny = new ItemBuilder(Material.CONCRETE);
        deny.setDurability((short) 14);
        deny.setColoredName("&cAnnuleer");
        deny.setNBT("action", "deny");
        inventory.setItem(11, deny.build());

        ItemBuilder confirm = new ItemBuilder(Material.CONCRETE);
        confirm.setDurability((short) 5);
        confirm.setColoredName("&aBevestig");
        confirm.addLoreLine("&7Dit contact zal permanent verwijderd zijn!");
        confirm.setNBT("action", "confirm");
        inventory.setItem(15, confirm.build());

        open(player);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType().equals(Material.AIR)) return;
        event.setCancelled(true);
        if (NBTEditor.getString(event.getCurrentItem(), "action") == null) return;
        String action = NBTEditor.getString(event.getCurrentItem(), "action");

        if(action.equals("deny")) {
            player.closeInventory();
            player.sendMessage(ChatUtils.color("&cGeannuleerd!"));
            return;
        }

        if(action.equals("confirm")) {
            player.sendMessage(ChatUtils.color("&3Je hebt succesvol het contact met het nummer &b06-" + contact.getNumber() + " &3verwijderd."));
            PhoneManager.getInstance().removeContact(phone, contact.getNumber());
            new ContactsMenu(player);
        }


    }
}
