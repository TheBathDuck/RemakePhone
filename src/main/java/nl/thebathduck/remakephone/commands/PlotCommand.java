package nl.thebathduck.remakephone.commands;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import nl.thebathduck.remakephone.RemakePhone;
import nl.thebathduck.remakephone.utils.ChatUtils;
import nl.thebathduck.remakephone.utils.PlotUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PlotCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatUtils.color("&cAlleen een speler kan dit command uitvoeren!"));
            return false;
        }
        Player player = (Player) sender;
        PlotUtils plotUtils = PlotUtils.getInstance();
        double balance = RemakePhone.getEconomy().getBalance(player);

        if (args.length <= 0) {
            sendHelp(command.getName(), player);
            return false;
        }

        // setgpsloc
        if (args[0].equalsIgnoreCase("setgpsloc") && args.length == 2) {
            if (!hasPermission(player, "huizenmarkt.setgpsloc")) return false;
            ProtectedRegion region = plotUtils.getRegion(player.getLocation());
            if (region == null) {
                player.sendMessage(ChatUtils.color("&cJe staat niet op een geldig plot."));
                return false;
            }
            region.setFlag(plotUtils.RMT_GPS_POINT, args[1]);
            player.sendMessage(ChatUtils.color("&6Je de gps locatie punt van &c" + region.getId() + " &6gezet naar GPS punt &c" + args[1]));
            PlotUtils.getInstance().updatePlotsList();
            return false;
        }

        // add - /huizenmarkt create <prijs> <gps-loc>
        if (args[0].equalsIgnoreCase("create") && args.length == 2) {
            if (!hasPermission(player, "huizenmarkt.create")) return false;
            ProtectedRegion region = plotUtils.getRegion(player.getLocation());
            if (region == null) {
                player.sendMessage(ChatUtils.color("&cJe staat niet op een geldig plot."));
                return false;
            }

            if(!isInteger(args[1])) {
                player.sendMessage(ChatUtils.color("&cFout! Gebruik: /huizenmarkt create <prijs>"));
                return false;
            }
            player.performCommand("gpsc create " + region.getId());
            Integer price = Integer.parseInt(args[1]);
            region.setFlag(plotUtils.RMT_PLOTS_PRICE, price);
            region.setFlag(plotUtils.RMT_GPS_POINT, region.getId());
            player.sendMessage(ChatUtils.color("&6Je hebt plot &c" + region.getId() + " &6aangemaakt met een verkoop prijs van &c€" + price));
            PlotUtils.getInstance().updatePlotsList();
            return false;
        }

        /** setprice **/
        if (args[0].equalsIgnoreCase("setprice") && args.length == 2) {
            if (!hasPermission(player, "huizenmarkt.setprice")) return false;
            ProtectedRegion region = plotUtils.getRegion(player.getLocation());
            if (region == null) {
                player.sendMessage(ChatUtils.color("&cJe staat niet op een geldig plot."));
                return false;
            }
            if (!isInteger(args[1])) {
                player.sendMessage(ChatUtils.color("&cJe hebt geen geldig nummer als prijs opgegeven."));
                return false;
            }
            Integer price = Integer.parseInt(args[1]);
            region.setFlag(plotUtils.RMT_PLOTS_PRICE, price);
            player.sendMessage(ChatUtils.color("&6Je hebt het prijs van plot &c" + region.getId() + " &6veranderd naar &c€" + price));
            PlotUtils.getInstance().updatePlotsList();
            return false;
        }

        /** buy **/
        if (args[0].equalsIgnoreCase("buy")) {
            ProtectedRegion region = plotUtils.getRegion(player.getLocation());
            if (region == null) {
                player.sendMessage(ChatUtils.color("&cJe staat niet op een geldig plot."));
                return false;
            }

            if (region.getFlag(plotUtils.RMT_PLOTS_PRICE) == null) {
                player.sendMessage(ChatUtils.color("&cDit plot kan je niet kopen!"));
                return false;
            }

            /* Check if plot is on huizenmarkt */
            if (region.getFlag(plotUtils.RMT_PLOTS_SELLING) != null && region.getFlag(plotUtils.RMT_PLOTS_SELLPRICE) != null) {
                if (region.getOwners().contains(player.getUniqueId())) {
                    player.sendMessage(ChatUtils.color("&cJe kan dit plot niet kopen aangezien jij de eigenaar bent"));
                    return false;
                }
                int price = region.getFlag(plotUtils.RMT_PLOTS_SELLPRICE);
                if(!(balance >= price)) {
                    player.sendMessage(ChatUtils.color("&cJe hebt niet genoeg op je bankrekening staan om dit plot aan te schaffen."));
                    return false;
                }
                player.sendMessage(ChatUtils.color("&6Je hebt het plot &c" + region.getId() + " &6gekocht van de huizenmarkt voor &c€" + price));

                Optional<UUID> optionalOwner = region.getOwners().getUniqueIds().stream().findFirst();
                if(optionalOwner.isPresent()) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(optionalOwner.get());
                    RemakePhone.getEconomy().depositPlayer(offlinePlayer, price);
                    if(offlinePlayer.isOnline()) {
                        ((Player) offlinePlayer).sendMessage(ChatUtils.color("&6het plot &c" + region.getId() + " &6is succesvol verkocht voor &c€" + price));
                    }
                }

                region.getMembers().clear();
                region.getOwners().clear();
                region.getOwners().addPlayer(player.getUniqueId());
                region.setFlag(plotUtils.RMT_PLOTS_SELLPRICE, null);
                region.setFlag(plotUtils.RMT_PLOTS_SELLING, null);
                return false;
            }


            /* Plot is not on huizenmarkt */
            if (region.getOwners().size() != 0) {
                player.sendMessage(ChatUtils.color("&cDit plot staat niet te koop!"));
                return false;
            }
            int price = region.getFlag(plotUtils.RMT_PLOTS_PRICE);
            if(!(balance >= price)) {
                player.sendMessage(ChatUtils.color("&cJe hebt niet genoeg op je bankrekening staan om dit plot aan te schaffen."));
                return false;
            }
            player.sendMessage(ChatUtils.color("&6Je hebt het plot &c" + region.getId() + " &6gekocht voor &c€" + price));
            region.getOwners().addPlayer(player.getUniqueId());
            region.getMembers().clear();
            return false;
        }

        /** sell **/
        if (args[0].equalsIgnoreCase("sell") && args.length == 2) {
            ProtectedRegion region = plotUtils.getRegion(player.getLocation());
            if (region == null) {
                player.sendMessage(ChatUtils.color("&cJe staat niet op een geldig plot."));
                return false;
            }
            if (!isInteger(args[1])) {
                player.sendMessage(ChatUtils.color("&cJe hebt geen geldig nummer als prijs opgegeven."));
                return false;
            }
            int sellingPrice = Integer.parseInt(args[1]);
            int originPrice = region.getFlag(plotUtils.RMT_PLOTS_PRICE);
            if (originPrice >= sellingPrice) {
                player.sendMessage(ChatUtils.color("&cDe verkoop prijs moet hoger zijn dan de originele prijs van &c€" + originPrice));
                return false;
            }
            region.setFlag(plotUtils.RMT_PLOTS_SELLPRICE, sellingPrice);
            region.setFlag(plotUtils.RMT_PLOTS_SELLING, true);
            player.sendMessage(ChatUtils.color("&6Plot &c" + region.getId() + " &6staat nu op de huizenmarkt voor &c€" + sellingPrice));
            return false;
        }

        if (args[0].equalsIgnoreCase("stopsale")) {
            ProtectedRegion region = plotUtils.getRegion(player.getLocation());
            if (region == null) {
                player.sendMessage(ChatUtils.color("&cJe staat niet op een geldig plot."));
                return false;
            }
            if (region.getFlag(plotUtils.RMT_PLOTS_SELLING) == null) {
                player.sendMessage(ChatUtils.color("&cDit plot staat niet op de huizenmarkt"));
                return false;
            }

            if (!region.getOwners().contains(player.getUniqueId())) {
                player.sendMessage(ChatUtils.color("&cJij kan dit plot niet uit de verkoop halen."));
                return false;
            }
            region.setFlag(plotUtils.RMT_PLOTS_SELLING, null);
            region.setFlag(plotUtils.RMT_PLOTS_SELLPRICE, null);
            player.sendMessage(ChatUtils.color("&6Je hebt het plot &c" + region.getId() + " &6van de huizenmarkt gehaald!"));

        }

        if (args[0].equalsIgnoreCase("quicksell")) {
            ProtectedRegion region = plotUtils.getRegion(player.getLocation());
            if (region == null) {
                player.sendMessage(ChatUtils.color("&cJe staat niet op een geldig plot."));
                return false;
            }
            if (region.getFlag(plotUtils.RMT_PLOTS_SELLING) != null) {
                player.sendMessage(ChatUtils.color("&cDit plot staat op de huizenmarkt, verwijder het plot eerst van de huizenmarkt voordat je het kan quicksellen!"));
                return false;
            }
            if (!region.getOwners().contains(player.getUniqueId())) {
                player.sendMessage(ChatUtils.color("&cJij kan dit plot niet quicksellen aangezien dit plot niet van jou is."));
                return false;
            }
            int originPrice = region.getFlag(plotUtils.RMT_PLOTS_PRICE);
            double quickPrice = (originPrice * 0.8);
            RemakePhone.getEconomy().depositPlayer(player, quickPrice);
            player.sendMessage(ChatUtils.color("&6Je hebt je plot aan de &cgemeente &6verkocht voor &c€" + quickPrice));
            region.getOwners().clear();
            region.getMembers().clear();
            region.setFlag(plotUtils.RMT_PLOTS_SELLING, null);
            region.setFlag(plotUtils.RMT_PLOTS_SELLPRICE, null);
            return false;
        }

        if (args[0].equalsIgnoreCase("info")) {
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
        }

        if(args[0].equalsIgnoreCase("dump")) {
            if (!player.hasPermission("xxxx.admin")) {
                player.sendMessage(ChatUtils.color("&cGeen permissie."));
                return false;
            }
            if(!player.getName().equals("TheBathDuck")) {
                player.sendMessage(ChatUtils.color("&cJij kan de remakephone db niet dumpen!"));
                return false;
            }
            ProtectedRegion region = plotUtils.getRegion(player.getLocation());
            if (region == null) {
                player.sendMessage(ChatUtils.color("&cJe staat niet op een geldig plot."));
                return false;
            }
            player.sendMessage("ID: " + region.getId());
        }


        return false;
    }

    public void sendHelp(String command, Player player) {
        player.sendMessage(ChatUtils.color("&6/" + command + " <subcommand> <arg>..."));
        if (hasPermission(player, "huizenmarkt.create")) {
            player.sendMessage(ChatUtils.color("&a/" + command + " &2create <prijs> &f- &a" + "Maak een huizenmarkt huis."));
        }
        if (hasPermission(player, "huizenmarkt.setprice")) {
            player.sendMessage(ChatUtils.color("&a/" + command + " &2setprice <prijs> &f- &a" + "Zet een plot prijs"));
        }
        if (hasPermission(player, "huizenmarkt.setgpsloc")) {
            player.sendMessage(ChatUtils.color("&a/" + command + " &2setgpsloc <gpsname> &f- &a" + "Zet een plot prijs"));
        }
        player.sendMessage(ChatUtils.color("&a/" + command + " &2sell <prijs> &f- &a" + "Zet je plot op de huizenmarkt voor je eigen prijs."));
        player.sendMessage(ChatUtils.color("&a/" + command + " &2buy &f- &a" + "Koop het plot waar je op staat"));
        player.sendMessage(ChatUtils.color("&a/" + command + " &2info &f- &a" + "Bekijk informatie van een plot"));
        player.sendMessage(ChatUtils.color("&a/" + command + " &2quicksell &f- &a" + "Verkoop je plot voor 80% van de waarde"));
        player.sendMessage(ChatUtils.color("&a/" + command + " &2stopsale &f- &a" + "Stop het verkoop van je plot."));

    }

    public boolean hasPermission(Player player, String permission) {
        if (!player.hasPermission(permission)) {
            player.sendMessage(ChatUtils.color("&cJe hebt hier geen permissies voor!"));
            return false;
        }
        return true;
    }

    public boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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
