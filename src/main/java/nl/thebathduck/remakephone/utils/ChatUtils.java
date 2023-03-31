package nl.thebathduck.remakephone.utils;

import org.bukkit.ChatColor;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public static String eco(double money) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        DecimalFormat formatter = new DecimalFormat("#,###", symbols);
        return formatter.format(money);
    }

}