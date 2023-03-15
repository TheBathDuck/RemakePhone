package nl.thebathduck.remakephone.listeners;

import nl.thebathduck.remakephone.RemakePhone;
import nl.thebathduck.remakephone.managers.PhoneManager;
import nl.thebathduck.remakephone.objects.Phone;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.annotation.WillCloseWhenClosed;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskAsynchronously(RemakePhone.getInstance(), () -> {
            PhoneManager phoneManager = PhoneManager.getInstance();

            Phone phone = phoneManager.getPhone(player.getUniqueId());
            phoneManager.savePhone(phone);



        });

    }

}
