package nl.thebathduck.remakephone.listeners;

import lombok.Getter;
import nl.thebathduck.remakephone.menu.maps.MapsSearchMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MapsChatListener implements Listener {

    private static @Getter List<UUID> listening = new ArrayList<>();

    @EventHandler
    public void onMapsChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!listening.contains(player.getUniqueId())) return;
        event.setCancelled(true);
        listening.remove(player.getUniqueId());
        new MapsSearchMenu(player, event.getMessage());
    }

}
