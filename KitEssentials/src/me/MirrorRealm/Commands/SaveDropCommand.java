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

public class SaveDropCommand implements CommandExecutor {
    public Main plugin;

    public SaveDropCommand(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("savedrop")) {
            Player player = (Player) sender;
            if (!player.hasPermission("kits.admin")) {
                plugin.send(player, "no-permission");
                return true;
            } else {
                ItemStack i = player.getItemInHand();
                if (i != null && !i.getType().equals(Material.AIR)) {
                    FileConfiguration fc = plugin.customFile("drops");
                    if (fc.getConfigurationSection("drops") == null) {
                        fc.set("drops.0", player.getItemInHand());
                    } else {
                        String s = "0";
                        for (String v : fc.getConfigurationSection("drops").getKeys(false)) {
                            s = v;
                        }
                        int k = Integer.parseInt(s) + 1;
                        fc.set("drops." + k, player.getItemInHand());
                    }
                    try {
                        fc.save(plugin.customData("drops"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    plugin.send(player, "saved-drop");
                } else {
                    plugin.send(player, "hold-item");
                }
            }
        }
        return true;
    }
}
