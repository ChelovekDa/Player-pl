package ru.hcc.player;

import org.bukkit.plugin.java.JavaPlugin;
import java.util.Objects;

public final class Player extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(getServer().getPluginCommand("music")).setExecutor(new MusicCommand());
    }

}
