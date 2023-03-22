package nl.thebathduck.remakephone.listeners;

import be.razerstorm.remakegrinding.RemakeGrinding;
import be.razerstorm.remakegrinding.objects.PlayerData;
import nl.thebathduck.remakephone.RemakePhone;
import nl.thebathduck.remakephone.managers.PhoneManager;
import nl.thebathduck.remakephone.menu.MainPhoneMenu;
import nl.thebathduck.remakephone.objects.Phone;
import nl.thebathduck.remakephone.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PhoneInteractionListener implements Listener {

    @EventHandler
    public void phoneInteraction(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getItem() == null) return;

        if (!event.getItem().getType().equals(Material.BLAZE_POWDER)) return;
        Player player = event.getPlayer();


        Bukkit.getScheduler().runTaskAsynchronously(RemakePhone.getInstance(), () -> {
            PhoneManager phoneManager = PhoneManager.getInstance();
            Phone phone = phoneManager.getPhone(player.getUniqueId());

            if (phone == null) {
                player.sendMessage(ChatUtils.color("&cEr is iets misgegaan met het ophalen van je telefoon gegevens, aangeraden om te reloggen. Blijft dit voorkomen? Open een ticket en tag een developer!"));
                return;
            }
            player.sendMessage(ChatUtils.color("&3Je opent de telefoon met telefoonnummer &b06-" + phone.getNumber()));
            new MainPhoneMenu(player);
        });


    }


}
