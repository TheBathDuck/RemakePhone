package nl.thebathduck.remakephone.commands;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import nl.thebathduck.remakephone.utils.ChatUtils;
import nl.thebathduck.remakephone.utils.PlotUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlotinfoCommandOverride implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatUtils.color("&cAlleen spelers kunnen dit command gebruiken!"));
            return false;
        }

        Player player = (Player) commandSender;
        PlotUtils plotUtils = PlotUtils.getInstance();

        ProtectedRegion region = plotUtils.getRegion(player.getLocation());
        if (region == null) {
            player.sendMessage(ChatUtils.color("&cJe staat niet op een geldig plot."));
            return false;
        }
        if (region.getFlag(plotUtils.RMT_PLOTS_PRICE) == null) {
            player.sendMessage(ChatUtils.color("&cJe staat niet op een geldig plot."));
            return false;
        }
        String ownerSign = (region.getOwners().size() >= 1 ? "Eigenaren" : "Eigenaar");
        String owners = "";
        for (UUID uuid : region.getOwners().getUniqueIds()) {

        }
        player.sendMessage(ChatUtils.color("&6-------------"));
        player.sendMessage(ChatUtils.color("&6Naam: &c" + region.getId()));

        if (region.getFlag(plotUtils.RMT_PLOTS_SELLING) == null) {
            player.sendMessage(ChatUtils.color("&6Prijs: &c€" + region.getFlag(plotUtils.RMT_PLOTS_PRICE)));
        } else {
            player.sendMessage(ChatUtils.color("&6Prijs: &c€" + region.getFlag(plotUtils.RMT_PLOTS_SELLPRICE) + " &6(Orgineel &c€" + region.getFlag(plotUtils.RMT_PLOTS_PRICE) + "&6)"));
        }

        player.sendMessage(ChatUtils.color("&6" + ownerSign + ": &c" + getOwners(region)));
        player.sendMessage(ChatUtils.color("&6Leden: &c" + getMembers(region)));
        player.sendMessage(ChatUtils.color("&6-------------"));

        return false;
    }

    public String getOwners(ProtectedRegion region) {
        List<String> owners = new ArrayList<>();
        for (UUID uuid : region.getOwners().getUniqueIds()) {
            owners.add(Bukkit.getOfflinePlayer(uuid).getName());
        }

        if (owners.size() == 0) {
            return "Gemeente";
        }

        return StringUtils.join(owners, ", ");
    }

    public String getMembers(ProtectedRegion region) {
        List<String> owners = new ArrayList<>();
        for (UUID uuid : region.getMembers().getUniqueIds()) {
            owners.add(Bukkit.getOfflinePlayer(uuid).getName());
        }

        if (owners.size() == 0) {
            return "Geen";
        }

        return StringUtils.join(owners, ", ");
    }
}
