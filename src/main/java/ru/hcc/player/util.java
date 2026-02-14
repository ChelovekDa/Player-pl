package ru.hcc.player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public sealed class util permits SoundManager {

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    @NotNull
    public static String getPath() {
        return Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin(ru.hcc.player.Player.getPlugin(ru.hcc.player.Player.class).getName())).getDataFolder().getAbsolutePath();
    }

}
