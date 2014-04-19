package me.MirrorRealm.Commands;

import me.MirrorRealm.kKits.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateKit implements CommandExecutor {
    public Main plugin;

    public CreateKit(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("createkit")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!(player.hasPermission("kits.admin"))) {
                    plugin.send(player, "no-permission");
                    return true;
                } else {
                    if (args.length != 1) {
                        plugin.send(player, "create-kit-usage");
                        return true;
                    } else {
                        plugin.methods().save(player, args[0]);
                        plugin.send(player, "created-kit", args[0]);
                        int i;
                        int data;
                        if (player.getInventory().getItem(0) == null || player.getInventory().getItem(0).getType().equals(Material.AIR)) {
                            i = 1;
                            data = 0;
                        } else {
                            i = player.getInventory().getItem(0).getTypeId();
                            data = player.getInventory().getItem(0).getData().getData();
                        }
                        String slot = "0";
                        if (plugin.getConfig().getConfigurationSection("gui.items.") != null) {
                            for (String v : plugin.getConfig().getConfigurationSection("gui.items.").getKeys(false)) {
                                String[] parts = plugin.getConfig().getString("gui.items." + v + ".info").split(":");
                                slot = parts[2];
                            }
                        }
                        plugin.getConfig().set("gui.items." + args[0].toLowerCase() + ".info", i + ":" + data + ":" + Integer.parseInt(slot) + ":&c" + args[0]);
                        List<String> lore = new ArrayList<>();
                        lore.add("&7This is the &9" + args[0] + " &7kit!");
                        plugin.getConfig().set("gui.items." + args[0].toLowerCase() + ".lore", lore);
                        plugin.saveConfig();
                    }
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("delkit")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!(player.hasPermission("kits.admin"))) {
                    plugin.send(player, "no-permission");
                    return true;
                } else {
                    if (args.length != 1) {
                        plugin.send(player, "delete-kit-usage");
                        return true;
                    } else {
                        FileConfiguration fc = plugin.kitFile();
                        if (fc.getConfigurationSection("kits." + args[0].toLowerCase()) != null) {
                            fc.set("kits." + args[0].toLowerCase(), null);
                            plugin.send(player, "deleted-kit", args[0].toLowerCase());
                            if (plugin.getConfig().getConfigurationSection("gui.items." + args[0].toLowerCase()) != null) {
                                plugin.getConfig().set("gui.items." + args[0].toLowerCase(), null);
                                plugin.saveConfig();
                            }
                            try {
                                fc.save(plugin.kitData());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return true;
                        } else {
                            plugin.send(player, "null-kit", args[0].toLowerCase());
                            return true;
                        }
                    }
                }
            }
        }
        return true;
    }
}
