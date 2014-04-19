package me.MirrorRealm.handlers;

import me.MirrorRealm.kKits.Main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class ItemDropEvent implements Listener {
    public Main plugin;

    public ItemDropEvent(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (event.getItemDrop() != null && !event.getItemDrop().getItemStack().getType().equals(Material.AIR)) {
            FileConfiguration fc = plugin.customFile("drops");
            if (fc.getConfigurationSection("drops") != null) {
                for (String s : fc.getConfigurationSection("drops").getKeys(false)) {
                    if (fc.getItemStack("drops." + s).equals(event.getItemDrop().getItemStack())) {
                        event.setCancelled(true);
                        plugin.send(player, "cannot-drop-item");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        if (plugin.getConfig().getBoolean("clear-drops-on-death")) {
            event.getDrops().clear();
        }
    }
}
