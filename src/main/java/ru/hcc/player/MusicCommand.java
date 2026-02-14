package ru.hcc.player;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class MusicCommand extends SoundManager implements CommandExecutor, TabCompleter {

    private static boolean isNum(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    private static boolean hasPerm(Player player, String perm) {
        return (player.hasPermission(perm) || player.hasPermission("player.music.*") || player.isOp());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (command.getName().equals("music")) {
                final HashMap<String, String> SOUNDS = getAllSounds();

                if (args.length > 0) {
                    if (args[0].equals("create") && hasPerm(player, "player.music.create")) {
                        if (args.length >= 2) {
                            if (SOUNDS.containsKey(args[1])) {
                                player.sendMessage(color("&4Композиция с таким именем уже задана. Композиция: &e&l%s".formatted(SOUNDS.get(args[1]))));
                                return false;
                            }
                            else {
                                write(new String[] {args[1], args[2]});
                                player.sendMessage(color("&aКомпозиция создана. Композиция: &e&l%s".formatted(SOUNDS.get(args[1]))));
                                return true;
                            }
                        }
                    }

                    if (args[0].equals("play") && hasPerm(player, "player.music.play")) {
                        if (args.length == 2) {
                            if (SOUNDS.containsKey(args[1])) {
                                for (Player p: Bukkit.getOnlinePlayers()) {
                                    p.stopAllSounds();
                                    p.playSound(p, SOUNDS.get(args[1]), 3, 1.5F);
                                    p.sendMessage(color("&aСейчас играет: &e&l%s".formatted(args[1])));
                                }
                                return true;
                            }
                        }
                        if (args.length == 4) {
                            if (isNum(args[2]) && isNum(args[3])) {
                                if (Float.parseFloat(args[3]) > 2F) {
                                    player.sendMessage(color("&4Тон звука не может быть выше значения &4&l2&4"));
                                    return false;
                                }
                                if (SOUNDS.containsKey(args[1])) {
                                    for (Player p: Bukkit.getOnlinePlayers()) {
                                        p.stopAllSounds();
                                        p.playSound(p, SOUNDS.get(args[1]), Float.parseFloat(args[2]), Float.parseFloat(args[3]));
                                        p.sendMessage(color("&aСейчас играет: &e&l%s".formatted(args[1])));
                                    }
                                    return true;
                                }
                            }
                            else {
                                player.sendMessage(color("&4Дальность или тон звука не являются числами."));
                                return false;
                            }
                        }
                        else return false;
                    }

                    if (args[0].equals("stop") && hasPerm(player, "player.music.stop")) {
                        for (Player p: Bukkit.getOnlinePlayers()) p.stopAllSounds();
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("create");
            completions.add("play");
            completions.add("stop");
        }

        return completions;
    }
}
