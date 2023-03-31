package nl.thebathduck.remakephone.listeners;

import nl.thebathduck.remakephone.RemakePhone;
import nl.thebathduck.remakephone.enums.ServerType;
import nl.thebathduck.remakephone.managers.PhoneManager;
import nl.thebathduck.remakephone.objects.Phone;
import nl.thebathduck.remakephone.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.Arrays;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PhoneManager phoneManager = PhoneManager.getInstance();

        phoneManager.setunloadItem(player);


        Bukkit.getScheduler().runTaskLaterAsynchronously(RemakePhone.getInstance(), () -> {

            if(!player.hasPlayedBefore()) {
                    player.getInventory().addItem(
                            new ItemBuilder(Material.APPLE)
                                    .setAmount(32)
                                    .addLoreLine("Officieel Remake Minetopia Item")
                                    .build()
                    );

                    player.getInventory().addItem(getWaterBottle());

                if(RemakePhone.getInstance().getServerType() == ServerType.GRINDING) {
                    player.getInventory().addItem(getUnbreakable(Material.WOOD_PICKAXE));
                    player.getInventory().addItem(getUnbreakable(Material.FISHING_ROD));
                }
            }

            Bukkit.getScheduler().runTaskAsynchronously(RemakePhone.getInstance(), () -> {

                if (phoneManager.isInDatabase(player.getUniqueId())) {
                    Phone phone = phoneManager.getFromDatabase(player.getUniqueId());
                    phoneManager.loadContacts(phone);
                    phoneManager.cachePhone(player.getUniqueId(), phone);
                    phoneManager.loadItem(player, phone);
                    return;
                }
                int number = phoneManager.getRandomNumber();
                Phone phone = phoneManager.registerPhoneNumber(player.getUniqueId(), number);
                phoneManager.loadItem(player, phone);
            });
        }, 10L);

    }

    private ItemStack getWaterBottle() {
        ItemStack bottle = new ItemStack(Material.POTION, 1);
        ItemMeta meta = bottle.getItemMeta();
        PotionMeta pmeta = (PotionMeta) meta;
        PotionData pdata = new PotionData(PotionType.WATER);
        pmeta.setBasePotionData(pdata);
        meta.setLore(Arrays.asList("Officieel Remake Minetopia Item"));
        bottle.setItemMeta(meta);
        return bottle;
    }

    private ItemStack getUnbreakable(Material material) {
        ItemBuilder builder = new ItemBuilder(material, 1);
        builder.addLoreLine("Officieel Remake Minetopia Grind Item");
        builder.makeUnbreakable(true);
        return builder.build();
    }


}
