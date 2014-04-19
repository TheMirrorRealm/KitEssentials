package me.MirrorRealm.other;

import me.MirrorRealm.CustomEvents.KitEssentialsEquipKitEvent;
import me.MirrorRealm.kKits.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.Set;

public class Methods {
    public Main plugin;

    public Methods(Main plugin) {
        this.plugin = plugin;
    }

    public void save(Player p, String b) {
        String s = b.toLowerCase();
        FileConfiguration fc = plugin.kitFile();
        int slot = 0;
        fc.set("kits." + s + ".name", b);
        for (ItemStack stack : p.getInventory().getContents()) {
            fc.set("kits." + s + ".inv." + slot, stack);
            slot++;
        }
        int sslot = 100;
        for (ItemStack stack : p.getInventory().getArmorContents()) {
            fc.set("kits." + s + ".armor." + sslot, stack);
            sslot++;
        }
        int m = 0;
        for (PotionEffect g : p.getActivePotionEffects()) {
            String ll = Integer.toString(m);
            fc.set("kits." + s + ".potion.name." + ll + ".type", g.getType().getName());
            fc.set("kits." + s + ".potion.name." + ll + ".level", g.getAmplifier());
            fc.set("kits." + s + ".potion.name." + ll + ".duration", g.getDuration());
            m++;
        }
        try {
            fc.save(plugin.kitData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getKitName(String s) {
        return plugin.kitFile().getString("kits." + s.toLowerCase() + ".name");
    }

    public void load(Player player, String s) {
        KitEssentialsEquipKitEvent kit = new KitEssentialsEquipKitEvent(player, getKitName(s));
        Bukkit.getServer().getPluginManager().callEvent(kit);
        if (!kit.isCancelled()) {
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            FileConfiguration fc = plugin.kitFile();
            for (String g : fc.getConfigurationSection("kits." + s + ".inv.").getKeys(false)) {
                player.getInventory().setItem(Integer.parseInt(g), fc.getItemStack("kits." + s + ".inv." + g));
            }
            player.getInventory().setHelmet(fc.getItemStack("kits." + s + ".armor.103"));
            player.getInventory().setChestplate(fc.getItemStack("kits." + s + ".armor.102"));
            player.getInventory().setLeggings(fc.getItemStack("kits." + s + ".armor.101"));
            player.getInventory().setBoots(fc.getItemStack("kits." + s + ".armor.100"));

            if (fc.getConfigurationSection("kits." + s + ".potion") != null) {
                for (String m : fc.getConfigurationSection("kits." + s + ".potion.name.").getKeys(false)) {
                    String b = fc.getString("kits." + s + ".potion.name." + m + ".type");
                    int n = fc.getInt("kits." + s + ".potion.name." + m + ".level");
                    int i = fc.getInt("kits." + s + ".potion.name." + m + ".duration");
                    player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(b), i, n));
                }
            }
            plugin.cantkit.add(player.getName());
        }
    }
}
