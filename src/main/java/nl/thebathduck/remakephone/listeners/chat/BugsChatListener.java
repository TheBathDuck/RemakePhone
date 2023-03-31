package nl.thebathduck.remakephone.listeners.chat;

import be.razerstorm.remakegrinding.common.events.custom.CustomFishEvent;
import be.razerstorm.remakegrinding.common.events.custom.CustomMineEvent;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import lombok.Getter;
import nl.thebathduck.remakephone.RemakePhone;
import nl.thebathduck.remakephone.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BugsChatListener implements Listener {

    private static @Getter List<UUID> listening = new ArrayList<>();

    @EventHandler
    public void onBugChatListener(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!listening.contains(player.getUniqueId())) return;
        event.setCancelled(true);
        listening.remove(player.getUniqueId());
        if(event.getMessage().toLowerCase().equals("annuleer") || event.getMessage().toLowerCase().equals("annuleren")) {
            player.sendMessage(ChatUtils.color("&cJe hebt je bug report geannuleerd!"));
            event.setCancelled(true);
            return;
        }

        String report = event.getMessage();
        player.sendMessage(ChatUtils.color("&aJe hebt een bugreport ingediend met de context: &2" + report));
        Bukkit.getScheduler().runTaskAsynchronously(RemakePhone.getInstance(), () -> {
           report(player, report);
        });
    }


    public void report(Player player, String report) {
        String iconUrl = "https://crafatar.com/renders/head/" + player.getUniqueId().toString();
        WebhookEmbedBuilder builder = new WebhookEmbedBuilder();
        builder.setAuthor(new WebhookEmbed.EmbedAuthor(player.getName(), iconUrl, null));
        builder.setDescription(report);
        RemakePhone.getInstance().getWebhookClient().send(builder.build());
    }
}
