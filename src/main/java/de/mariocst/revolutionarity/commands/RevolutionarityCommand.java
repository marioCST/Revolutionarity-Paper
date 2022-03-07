package de.mariocst.revolutionarity.commands;

import de.mariocst.revolutionarity.Revolutionarity;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RevolutionarityCommand implements CommandExecutor, TabCompleter {
    private final Revolutionarity plugin;

    public RevolutionarityCommand(Revolutionarity plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            if (args.length > 1) {
                switch (args[0].toLowerCase()) {
                    case "config" -> {
                        switch (args[1].toLowerCase()) {
                            case "reload" -> {
                                this.plugin.loadConfigs();
                                sender.sendMessage(this.plugin.getPrefix() + "Configs reloaded!");
                            }
                            case "save" -> {
                                this.plugin.saveConfigs();
                                sender.sendMessage(this.plugin.getPrefix() + "Configs saved!");
                            }
                            default -> sender.sendMessage(this.plugin.getPrefix() + "/rv config <reload|save>");
                        }
                    }
                    case "velo" -> {
                        if (!this.plugin.getVeloConfig().containsPlayer(args[1])) {
                            sender.sendMessage(this.plugin.getPrefix() + "§cThe player §a" + args[1] + " §cis not registered!");
                            return true;
                        }

                        sender.sendMessage(this.plugin.getPrefix() + "§cVelo of §a" + args[1] + "§c: §a" + this.plugin.getVeloConfig().getVelo(args[1]));
                    }
                    default -> sender.sendMessage(this.plugin.getPrefix() + "/rv <config|velo> <reload|save|playername>");
                }
            }
            else {
                sender.sendMessage(this.plugin.getPrefix() + "/rv <config|velo> <reload|save|playername>");
            }
            return true;
        }

        if (player.hasPermission("revolutionarity.command") || player.hasPermission("revolutionarity.*") || player.hasPermission("*") || player.isOp()) {
            if (args.length > 1) {
                switch (args[0].toLowerCase()) {
                    case "config" -> {
                        if (!player.hasPermission("revolutionarity.command.config") &&
                                !player.hasPermission("revolutionarity.*") &&
                                !player.hasPermission("*") &&
                                !player.isOp()) {
                            player.sendMessage(this.plugin.getPrefix() + "§cNo permission!");
                            return true;
                        }

                        switch (args[1].toLowerCase()) {
                            case "reload" -> {
                                this.plugin.loadConfigs();
                                player.sendMessage(this.plugin.getPrefix() + "Configs reloaded!");
                            }
                            case "save" -> {
                                this.plugin.saveConfigs();
                                player.sendMessage(this.plugin.getPrefix() + "Configs saved!");
                            }
                            default -> player.sendMessage(this.plugin.getPrefix() + "/rv config <reload|save>");
                        }
                    }
                    case "velo" -> {
                        if (!this.plugin.getVeloConfig().containsPlayer(args[1])) {
                            player.sendMessage(this.plugin.getPrefix() + "§cThe player §a" + args[1] + " §cis not registered!");
                            return true;
                        }

                        player.sendMessage(this.plugin.getPrefix() + "§4Velo of §a" + args[1] + "§4: §a" + this.plugin.getVeloConfig().getVelo(args[1]));
                    }
                    default -> player.sendMessage(this.plugin.getPrefix() + "/rv <config|velo> <reload|save|playername>");
                }
            }
            else {
                player.sendMessage(this.plugin.getPrefix() + "/rv <config|velo> <reload|save|playername>");
            }
        }
        else {
            player.sendMessage("§cNo permission!");
        }
        return false;
    }

    private final String[] MODES = { "config", "velo" };
    private final String[] CONFIG = { "reload", "save" };

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        final List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], Arrays.asList(MODES), completions);
            Collections.sort(completions);
        }
        else if (args.length == 2 && args[0].equalsIgnoreCase("config")) {
            StringUtil.copyPartialMatches(args[1], Arrays.asList(CONFIG), completions);
            Collections.sort(completions);
        }
        else if (args.length == 2 && args[0].equalsIgnoreCase("velo")) {
            List<String> players = new ArrayList<>();

            for (OfflinePlayer player : this.plugin.getServer().getOfflinePlayers()) {
                players.add(player.getName());
            }

            StringUtil.copyPartialMatches(args[1], players, completions);
            Collections.sort(completions);
        }
        return completions;
    }
}
