package nl.thebathduck.remakephone.listeners;

import nl.thebathduck.remakephone.RemakePhone;
import nl.thebathduck.remakephone.enums.PhoneSkin;
import nl.thebathduck.remakephone.managers.PhoneManager;
import nl.thebathduck.remakephone.objects.Phone;
import nl.thebathduck.remakephone.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PhoneManager phoneManager = PhoneManager.getInstance();

        phoneManager.setunloadItem(player);


        Bukkit.getScheduler().runTaskLaterAsynchronously(RemakePhone.getInstance(), () -> {
            Bukkit.getScheduler().runTaskAsynchronously(RemakePhone.getInstance(), () -> {

                if(phoneManager.isInDatabase(player.getUniqueId())) {
                    Phone phone = phoneManager.getFromDatabase(player.getUniqueId());
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



}
