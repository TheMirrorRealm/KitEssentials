package me.MirrorRealm.kKits;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.bukkit.entity.Player;

public class Cooldowns {
    private static Table<String, String, Long> cooldowns = HashBasedTable.create();

    public static long getCooldown(Player player, String key) {
        return calculateRemainder(cooldowns.get(player.getName(), key));
    }

    public static long setCooldown(Player player, String key, long delay) {
        return calculateRemainder(cooldowns.put(player.getName(), key, System.currentTimeMillis() + delay));
    }

    public static boolean tryCooldown(Player player, String key, long delay) {
        if (getCooldown(player, key) <= 0L) {
            setCooldown(player, key, delay);
            return true;
        }
        return false;
    }

    private static long calculateRemainder(Long expireTime) {
        return expireTime != null ? expireTime - System.currentTimeMillis() : -9223372036854775808L;
    }
}
