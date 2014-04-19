package me.MirrorRealm.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.MirrorRealm.kKits.Main;

public class ReloadConfig implements CommandExecutor {
    public Main plugin;

    public ReloadConfig(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("reloadkits")) {
            Player player = (Player) sender;
            if (!player.hasPermission("kits.admin")) {
                plugin.send(player, "no-permission");
                return true;
            } else {
                plugin.send(player, "reloaded-config");
                plugin.getLangYAML().saveLang();
                plugin.saveConfig();
                plugin.getLangYAML().reloadLang();
                plugin.reloadConfig();
            }
        }
        return true;
    }
}
