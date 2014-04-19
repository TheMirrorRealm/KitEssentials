package me.MirrorRealm.Commands;

import me.MirrorRealm.kKits.Main;
import org.bukkit.Bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class KitCommand implements CommandExecutor, Listener {
    public Main plugin;

    public KitCommand(Main plugin) {
        this.plugin = plugin;
    }

    public boolean kitExits(String s) {
        FileConfiguration fc = plugin.kitFile();
        return (fc.getConfigurationSection("kits." + s.toLowerCase()) != null);
    }

    public void openGUI(Player player) {
        FileConfiguration fc = plugin.kitFile();
        if (fc.getConfigurationSection("kits.") != null) {
            plugin.getBook().openGui(player);
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("kit")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0 || args.length > 2) {
                    if (plugin.kitFile().getConfigurationSection("kits.") != null) {
                        plugin.send(player, "kit-usage");
                    } else {
                        plugin.send(player, "no-kits");
                    }
                    return true;
                } else if (args.length == 1) {
                    String s = args[0].toLowerCase();
                    if (!kitExits(args[0])) {
                        plugin.send(player, "null-kit", args[0]);
                        openGUI(player);
                    } else {
                        if (player.hasPermission("kits.kit." + args[0].toLowerCase())) {
                            if (!plugin.cantkit.contains(player.getName())) {
                                if (player.hasPermission("kits.bypass.kit") || player.hasPermission("kits.admin")) {
                                    plugin.methods().load(player, s);
                                    plugin.send(player, "gave-kit", plugin.methods().getKitName(s));
                                } else {
                                    plugin.send(player, "cant-kit");
                                }
                            }
                        } else {
                            plugin.send(player, "no-permission");
                        }
                    }
                } else if (args.length == 2) {
                    String s = args[0].toLowerCase();
                    if (!kitExits(args[0])) {
                        plugin.send(player, "null-kit", args[0]);
                        openGUI(player);
                    } else {
                        Player target = Bukkit.getServer().getPlayer(args[1]);
                        if (player.hasPermission("kits.admin")) {
                            if (target != null) {
                                plugin.methods().load(target, s);
                                plugin.send(target, "gave-kit", plugin.methods().getKitName(s));
                                plugin.send(player, "gave-target-kit", plugin.methods().getKitName(s), target.getName());
                            } else {
                                plugin.send(player, "null-target", args[1]);
                            }
                        } else {
                            plugin.send(player, "no-permission");
                        }
                    }
                }
            }
        }

        if (cmd.getName().equals("kits")) {
            if (sender instanceof Player) {
                plugin.getBook().openGui((Player) sender);
            }
        }

        return true;
    }
}
