package me.MirrorRealm.Commands;

import me.MirrorRealm.kKits.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class DelDropCommand implements CommandExecutor {
    public Main plugin;

    public DelDropCommand(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("deldrop")) {
            Player player = (Player) sender;
            if (!player.hasPermission("kits.admin")) {
                plugin.send(player, "no-permission");
                return true;
            } else {
                ItemStack i = player.getItemInHand();
                if (i != null && !i.getType().equals(Material.AIR)) {
                    FileConfiguration fc = plugin.customFile("drops");
                    if (fc.getConfigurationSection("drops") == null) {
                        plugin.send(player, "not-a-blocked-drop");
                        return true;
                    } else {
                        for (String v : fc.getConfigurationSection("drops").getKeys(false)) {
                            ItemStack ni = fc.getItemStack("drops." + v);
                            if (ni.getType().equals(i.getType())) {
                                if (ni.getData().getData() == i.getData().getData()) {
                                    if (ni.getItemMeta().equals(i.getItemMeta())) {
                                        if (ni.getEnchantments().equals(i.getEnchantments())) {
                                            fc.set("drops." + v, null);
                                            plugin.send(player, "deleted-drop");
                                            try {
                                                fc.save(plugin.customData("drops"));
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                        plugin.send(player, "not-a-blocked-drop");
                    }
                } else {
                    plugin.send(player, "hold-item");
                }
            }
        }
        return true;
    }
}
