package ru.hcc.player;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class Player extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            new FileRW().createDir();
        } catch (IOException e) {
            System.out.println(e.getMessage() + "\n" + e.getCause());
        }
        getServer().getPluginCommand("music").setExecutor(new MusicCommand());
    }


}
