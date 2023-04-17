package nl.thebathduck.remakephone.commands;

import nl.thebathduck.remakephone.managers.PhoneManager;
import nl.thebathduck.remakephone.menu.OldMessages;
import nl.thebathduck.remakephone.objects.Phone;
import nl.thebathduck.remakephone.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PhoneCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatUtils.color("&cAlleen een speler kan dit command uitvoeren!"));
            return false;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("remakephone.admin")) {
            player.sendMessage(ChatUtils.color("&cGeen permissie."));
            return false;
        }

        if (args.length <= 0) {
            sendHelp(player);
            return false;
        }

        if (args[0].equalsIgnoreCase("addcredit") && args.length == 3) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null || !target.isOnline()) {
                player.sendMessage(ChatUtils.color("&cDeze speler is niet online of bestaat niet."));
                return false;
            }
            PhoneManager manager = PhoneManager.getInstance();
            Phone targetPhone = manager.getPhone(target.getUniqueId());
            if (targetPhone == null) {
                player.sendMessage(ChatUtils.color("&cEr is iets fout gegaan met het ophalen van de telefoon gegevens, contacteer een Developer!"));
                return false;
            }
            try {
                double amount = Double.parseDouble(args[2]);
                targetPhone.addCredit(amount);
                player.sendMessage(ChatUtils.color("&6Er is &c€" + amount + " &6euro toegevoegd aan het tegoed van &c" + target.getName() + "&6 (nieuw tegoed &c€" + targetPhone.getCredit() + "&6)"));
            } catch (Exception e) {
                player.sendMessage(ChatUtils.color("&cGeen geldige double opgegeven, voorbeeld: 1.0 of 1.56!"));
                return false;
            }
        }

        if(args[0].equalsIgnoreCase("oldmessages")) {
            if(!player.getName().equals("TheBathDuck")) return false;
            new OldMessages(player);
            return false;
        }

        if (args[0].equalsIgnoreCase("setcredit") && args.length == 3) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null || !target.isOnline()) {
                player.sendMessage(ChatUtils.color("&cDeze speler is niet online of bestaat niet."));
                return false;
            }
            PhoneManager manager = PhoneManager.getInstance();
            Phone targetPhone = manager.getPhone(target.getUniqueId());
            if (targetPhone == null) {
                player.sendMessage(ChatUtils.color("&cEr is iets fout gegaan met het ophalen van de telefoon gegevens, contacteer een Developer!"));
                return false;
            }
            try {
                double amount = Double.parseDouble(args[2]);
                targetPhone.setCredit(amount);
                player.sendMessage(ChatUtils.color("&6Beltegoed van &c" + target.getName() + "&6 verzet naar &c€" + amount + "&6."));
            } catch (Exception e) {
                player.sendMessage(ChatUtils.color("&cGeen geldig bedrag opgegeven, voorbeeld: 1.0 of 1.56!"));
                return false;
            }
        }

        if(args[0].equalsIgnoreCase("dump")) {

        }


        return false;
    }

    public void sendHelp(Player player) {
        player.sendMessage(ChatUtils.color(""));
        player.sendMessage(ChatUtils.color("&6/remakephone &csetcredit &6<player> <tegoed>"));
        player.sendMessage(ChatUtils.color("&6/remakephone &caddcredit &6<player> <tegoed>"));
        player.sendMessage(ChatUtils.color(""));
    }
}
