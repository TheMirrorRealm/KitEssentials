package me.MirrorRealm.kKits;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookGui implements Listener {
    private Main plugin;

    public BookGui(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        plugin.cantkit.remove(event.getEntity().getName());
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        String bb = plugin.getConfig().getString("gui.gui-name");
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (event.getInventory().getTitle().equals(ChatColor.translateAlternateColorCodes('&', bb))) {
                ItemStack i = event.getCurrentItem();
                if (i != null && !i.getType().equals(Material.AIR)) {
                    event.setCancelled(true);
                    for (String s : plugin.getConfig().getConfigurationSection("gui.items.").getKeys(false)) {
                        String ss = plugin.getConfig().getString("gui.items." + s + ".info");
                        String[] parts = ss.split(":");
                        String name = parts[3];
                        if (i.hasItemMeta()) {
                            if (i.getItemMeta().hasDisplayName()) {
                                if (i.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', name))) {
                                    if (i.getTypeId() == Integer.parseInt(parts[0])){
                                        if (i.getData().getData() == Integer.parseInt(parts[1])){
                                            Bukkit.getServer().dispatchCommand(player, "kit " + s);
                                            player.closeInventory();
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void openGui(Player player) {
        if (plugin.getConfig().getConfigurationSection("gui.items") == null) {
            plugin.send(player, "no-kits");
        } else {
            int size = 9;
            int k = plugin.getConfig().getConfigurationSection("gui.items").getKeys(false).size();
            if (k <= 54) {
                size = 54;
            }
            if (k <= 45) {
                size = 45;
            }
            if (k <= 36) {
                size = 36;
            }
            if (k <= 27) {
                size = 27;
            }
            if (k <= 18) {
                size = 18;
            }
            if (k <= 9) {
                size = 9;
            }
            String bb = plugin.getConfig().getString("gui.gui-name");
            Inventory inv = Bukkit.getServer().createInventory(null, size, ChatColor.translateAlternateColorCodes('&', bb));
            for (String s : plugin.getConfig().getConfigurationSection("gui.items.").getKeys(false)) {
                String ss = plugin.getConfig().getString("gui.items." + s + ".info");
                String[] parts = ss.split(":");
                int itemID = Integer.parseInt(parts[0]);
                int data = Integer.parseInt(parts[1]);
                int slot = Integer.parseInt(parts[2]);
                String name = parts[3];
                ItemStack item = new ItemStack(Material.getMaterial(itemID), 1, (short) data);
                ItemMeta sitem = item.getItemMeta();
                sitem.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
                List<String> lore = new ArrayList<>();
                for (String l : plugin.getConfig().getStringList("gui.items." + s + ".lore")) {
                    lore.add(ChatColor.translateAlternateColorCodes('&', l));
                }
                sitem.setLore(lore);
                item.setItemMeta(sitem);
                inv.setItem(slot, item);
                player.openInventory(inv);
            }
        }
    }
}
