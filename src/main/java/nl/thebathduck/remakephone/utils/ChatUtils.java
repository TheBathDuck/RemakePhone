package nl.thebathduck.remakephone.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ChatUtils {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public static String strip(String message) {
        return ChatColor.stripColor(message);
    }

    public static List<String> colorList(List<String> list) {
        List<String> temp = new ArrayList<>();
        list.forEach(item -> temp.add(color(item)));
        return temp;
    }

}