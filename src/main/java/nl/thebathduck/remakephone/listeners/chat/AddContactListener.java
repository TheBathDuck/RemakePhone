package nl.thebathduck.remakephone.listeners.chat;

import com.sk89q.worldguard.bukkit.listener.SpongeUtil;
import lombok.Getter;
import net.minecraft.server.v1_12_R1.InteractionResultWrapper;
import nl.thebathduck.remakephone.RemakePhone;
import nl.thebathduck.remakephone.managers.PhoneManager;
import nl.thebathduck.remakephone.objects.Contact;
import nl.thebathduck.remakephone.objects.Phone;
import nl.thebathduck.remakephone.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddContactListener implements Listener {

    private static @Getter List<UUID> listening = new ArrayList<>();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!listening.contains(player.getUniqueId())) return;
        event.setCancelled(true);
        listening.remove(player.getUniqueId());
        if(event.getMessage().toLowerCase().equals("annuleer") || event.getMessage().toLowerCase().equals("annuleren")) {
            player.sendMessage(ChatUtils.color("&cGeannuleerd."));
            event.setCancelled(true);
            return;
        }

        String[] args = event.getMessage().split(" ");
        String arg1 = args[0];
        if(arg1.startsWith("06-")) {
            arg1 = arg1.replaceAll("06-", "");
        }
        if(!isNumber(arg1)) {
            player.sendMessage(ChatUtils.color("&cJe hebt geen geldig telefoon nummer opgegeven."));
            return;
        }
        int number = Integer.parseInt(arg1);

        PhoneManager phoneManager = PhoneManager.getInstance();
        Bukkit.getScheduler().runTaskAsynchronously(RemakePhone.getInstance(), () -> {
            Phone phone = phoneManager.getPhone(player.getUniqueId());
            Phone targetPhone = phoneManager.getFromDatabaseNumber(number);
            if(targetPhone == null) {
                player.sendMessage(ChatUtils.color("&cHet nummer wat je opgegeven hebt is momenteel niet in gebruik."));
                return;
            }
            player.sendMessage(ChatUtils.color("&3Je hebt het nummer &b06-" + targetPhone.getNumber() + " &3toegevoegd aan je contacten."));
            phoneManager.addContact(phone, targetPhone.getOwner(), targetPhone.getNumber());
        });


    }

    private boolean isNumber(String numberString) {
        try {
            Integer.valueOf(numberString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
