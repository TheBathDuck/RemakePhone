package nl.thebathduck.remakephone.commands;

import nl.thebathduck.remakephone.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class NotValidOnThisServerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage(ChatUtils.color("&cDit command is niet geldig op deze server!"));
        return false;
    }
}
