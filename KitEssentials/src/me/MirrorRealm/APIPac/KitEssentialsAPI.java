package me.MirrorRealm.APIPac;

import me.MirrorRealm.kKits.Cooldowns;
import me.MirrorRealm.kKits.Main;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class KitEssentialsAPI {
    public static Main plugin;

    public KitEssentialsAPI(Main plugin) {
        KitEssentialsAPI.plugin = plugin;
    }

    /**
     * @param player - Player whom you want to get the kit name of
     * @return - name of kit or null
     */
    public static String getKitName(Player player){
        FileConfiguration fc = plugin.playerFile(player);
        if (fc.getString("last-kit") != null){
            return fc.getString("last-kit");
        }
        return null;
    }

    /**
     * @param player
     * @return - the FileConfiguration of a player.
     */
    public static FileConfiguration playerFile(Player player){
        return plugin.playerFile(player);
    }
    public static FileConfiguration playerFile(OfflinePlayer player){
        return plugin.playerFile(player);
    }
    public static FileConfiguration playerFile(String uuid){
        return plugin.playerFile(uuid);
    }
    /**
     * @param player
     * @return - the userdata File.
     */
    public static File playerFolder(Player player){
        return plugin.playerData(player);
    }
    public static File playerFolder(OfflinePlayer player){
        return plugin.playerData(player);
    }
    public static File playerFolder(String uuid){
        return plugin.playerData(uuid);
    }

    /**
     * @param player
     * @return - number of seconds until a player is allowed to use /kit again
     */
    public static long getTimeLeftToKit(Player player){
        return Cooldowns.getCooldown(player, "kit") / 1000;
    }
    public static void resetTimeLeftToKit(Player player){
        Cooldowns.setCooldown(player, "kit", 0);
    }
    public static void setTimeLeftToKit(Player player){
        
    }
}
