package nl.thebathduck.remakephone.listeners.chat;

import lombok.Getter;
import nl.thebathduck.remakephone.RemakePhone;
import nl.thebathduck.remakephone.managers.MessageManager;
import nl.thebathduck.remakephone.managers.PhoneManager;
import nl.thebathduck.remakephone.objects.Phone;
import nl.thebathduck.remakephone.utils.ChatUtils;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.Hash;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MessagesChatListener implements Listener {

    private static List<UUID> listening = new ArrayList<>();
    private static HashMap<UUID, UUID> targetUuid = new HashMap<>();
    private static HashMap<UUID, Integer> targetNumber = new HashMap<>();

    public static void startListener(Player player, UUID uuid, int number) {
        listening.add(player.getUniqueId());
        targetUuid.put(player.getUniqueId(), uuid);
        targetNumber.put(player.getUniqueId(), number);
        player.sendMessage(ChatUtils.color("&3Stuur je &bbericht &3in de chat of type \n'&bannuleren&3\b' om te annuleren."));
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!listening.contains(player.getUniqueId())) return;
        event.setCancelled(true);
        listening.remove(player.getUniqueId());

        Phone phone = PhoneManager.getInstance().getPhone(player.getUniqueId());
        int number = targetNumber.get(player.getUniqueId());
        UUID uuid = targetUuid.get(player.getUniqueId());

        targetNumber.remove(player.getUniqueId());
        targetUuid.remove(player.getUniqueId());

        if(event.getMessage().toLowerCase().equals("annuleer") || event.getMessage().toLowerCase().equals("annuleren")) {
            player.sendMessage(ChatUtils.color("&cGeannuleerd."));
            event.setCancelled(true);
            return;
        }
        player.sendMessage(ChatUtils.color("&3Je bericht is verstuurd!"));
        MessageManager.getInstance().addMessage(uuid, number, phone.getNumber(), event.getMessage());
    }

}
