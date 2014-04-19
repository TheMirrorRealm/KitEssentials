package me.MirrorRealm.Commands;

import me.MirrorRealm.kKits.Main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Drop implements CommandExecutor {
    public Main plugin;

    public Drop(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("drop")) {
            if (!(sender instanceof Player)) {
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("kits.drop")) {
                plugin.send(player, "no-permission");
                return true;
            } else {
                player.setItemInHand(new ItemStack(Material.AIR));
                player.updateInventory();
            }
        }
        return true;
    }
}
