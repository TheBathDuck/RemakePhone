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

        player.sendMessage(ChatUtils.color("&6&m------------------------"));
        player.sendMessage(ChatUtils.color("&6Plot info voor: &c" + region.getId()));
        player.sendMessage(ChatUtils.color("&6Eigenaar: &c" + getOwners(region)));
        player.sendMessage(ChatUtils.color("&6Leden: &c" + getMembers(region)));


        if(region.getFlag(plotUtils.RMT_PLOTS_PRICE) != null) {
            player.sendMessage(ChatUtils.color("&6Plot prijs: &c€" + ChatUtils.eco(region.getFlag(plotUtils.RMT_PLOTS_PRICE))));
        }

        if(region.getFlag(plotUtils.RMT_PLOTS_SELLING) != null) {
            player.sendMessage(ChatUtils.color("&6Te koop, prijs: &c€" + ChatUtils.eco(region.getFlag(plotUtils.RMT_PLOTS_SELLPRICE))));
        }

        player.sendMessage(ChatUtils.color("&6&m------------------------"));

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
