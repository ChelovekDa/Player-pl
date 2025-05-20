package ru.hcc.player;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class util {

    public static void sendMessage(Player player, String mes) {
        player.sendMessage(color(mes));
    }

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
