package ru.hcc.player;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class MusicCommand extends FileRW implements CommandExecutor {

    private static boolean isNum(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private boolean hasPerm(Player player) {
        if (player.hasPermission("player.music.*")) return true;
        return player.hasPermission("player.*");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        try {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (command.getName().equalsIgnoreCase("music")) {
                    if (args.length > 0) {
                        if (args[0].equalsIgnoreCase("create") && player.hasPermission("player.music.create") || hasPerm(player) && args[0].equalsIgnoreCase("create")) {
                            if (args.length >= 4) {
                                return false;
                            }
                            else {
                                if (getAllSounds().containsKey(args[1])) {
                                    util.sendMessage(player, "&4Композиция с таким именем уже задана. Композиция: " + getAllSounds().get(args[1]));
                                    return false;
                                }
                                else {
                                    write(new String[]{args[1], args[2]});
                                    util.sendMessage(player, "&aКомпозиция создана. Композиция: " + getAllSounds().get(args[1]));
                                    return true;
                                }
                            }
                        }

                        if (args[0].equalsIgnoreCase("play") && player.hasPermission("player.music.play") || hasPerm(player) && args[0].equalsIgnoreCase("play")) {
                            if (args.length == 2) {
                                if (getAllSounds().containsKey(args[1])) {
                                    HashMap<String, String> map = getAllSounds();
                                    for (Player p: Bukkit.getOnlinePlayers()) {
                                        p.stopAllSounds();
                                        p.playSound(p, map.get(args[1]), 3, 1.5F);
                                    }
                                    Bukkit.broadcastMessage("Сейчас играет: " + args[1]);
                                    return true;
                                }
                            }
                            if (args.length == 4) {
                                if (isNum(args[2]) && isNum(args[3])) {
                                    if (Float.parseFloat(args[3]) > 2F) {
                                        util.sendMessage(player, "&4Тон звука не может быть выше значения &4&l2&4");
                                        return false;
                                    }
                                    if (getAllSounds().containsKey(args[1])) {
                                        HashMap<String, String> map = getAllSounds();
                                        for (Player p: Bukkit.getOnlinePlayers()) {
                                            p.stopAllSounds();
                                            p.playSound(p, map.get(args[1]), Float.parseFloat(args[2]), Float.parseFloat(args[3]));
                                        }
                                        Bukkit.broadcastMessage("Сейчас играет: " + args[1]);
                                        return true;
                                    }
                                }
                                else {
                                    util.sendMessage(player, "&4Дальность или тон звука не являются числами.");
                                    return false;
                                }
                            }
                            else return false;
                        }

                        if (args[0].equalsIgnoreCase("stop") && player.hasPermission("player.music.stop") || hasPerm(player) && args[0].equalsIgnoreCase("stop")) {
                            for (Player p: Bukkit.getOnlinePlayers()) p.stopAllSounds();
                            return true;
                        }
                    }
                }
                return false;
            }
            else return false;
        }
        catch (Exception e) {
            System.out.println(e.getMessage() + "\n" + e.getCause());
        }
        return false;
    }

}
