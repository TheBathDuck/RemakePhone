package nl.thebathduck.remakephone.menu;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.thebathduck.remakephone.enums.PhoneSkin;
import nl.thebathduck.remakephone.managers.PhoneManager;
import nl.thebathduck.remakephone.objects.Phone;
import nl.thebathduck.remakephone.utils.ChatUtils;
import nl.thebathduck.remakephone.utils.GUIHolder;
import nl.thebathduck.remakephone.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SkinPhoneMenu extends GUIHolder {

    private Player player;

    public SkinPhoneMenu(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, 6 * 9, "Selecteer je skin.");
        this.open(player);
        reload();
    }

    public void reload() {
        inventory.clear();
        String current = NBTEditor.getString(player.getInventory().getItemInMainHand(), "mtwcustom");
        if (current == null) {
            current = "normal_phone";
        }


        for (PhoneSkin skin : PhoneSkin.values()) {
            ItemBuilder builder = new ItemBuilder(Material.BLAZE_POWDER);
            builder.addLoreLine("");

            builder.setColoredName("&3" + skin.getName());
            if (skin.getNbt().equals(current)) {
                builder.addUnsafeEnchantment(Enchantment.MENDING, 1);
                builder.setColoredName("&3&o" + skin.getName());
            }
            builder.setItemFlags();

            if (player.hasPermission(skin.getPermission())) {
                builder.addLoreLine("&7Ontgrendeld: &aJa.");
            } else {
                builder.addLoreLine("&7Ontgrendeld: &cNee.");
            }
            builder.addLoreLine("");
            builder.addLoreLine("&7Klik om deze skin toe te");
            builder.addLoreLine("&7passen op je telefoon.");

            builder.setNBT("mtwcustom", skin.getNbt());
            builder.setNBT("skin", skin.toString().toUpperCase());
            inventory.addItem(builder.build());
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType().equals(Material.AIR)) return;
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        PhoneSkin skin = PhoneSkin.valueOf(NBTEditor.getString(event.getCurrentItem(), "skin"));

        if (!player.hasPermission(skin.getPermission())) {
            player.sendMessage(ChatUtils.color("&cJe hebt deze telefoon skin niet ontgrendeld."));
            return;
        }

        if (event.getCurrentItem().getItemMeta().hasEnchant(Enchantment.MENDING)) {
            player.sendMessage(ChatUtils.color("&cJe hebt deze skin al geselecteerd."));
            return;
        }
        Phone phone = PhoneManager.getInstance().getPhone(player.getUniqueId());
        phone.setSkin(skin);
        ItemStack newSkin = new ItemBuilder(player.getInventory().getItemInMainHand()).setNBT("mtwcustom", skin.getNbt()).setColoredName("&b" + skin.getName()).build();
        player.getInventory().setItemInMainHand(newSkin);
        player.sendMessage(ChatUtils.color("&3Je hebt je telefoon skin veranderd naar &b" + skin.getName() + "&3."));
        reload();
    }
}
