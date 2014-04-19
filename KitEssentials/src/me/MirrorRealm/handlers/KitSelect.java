package me.MirrorRealm.handlers;

import me.MirrorRealm.CustomEvents.KitEssentialsEquipKitEvent;
import me.MirrorRealm.kKits.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

import java.io.IOException;

public class KitSelect implements Listener {
    public Main plugin;

    public KitSelect(Main plugin) {
        this.plugin = plugin;
    }

    public void onKit(KitEssentialsEquipKitEvent event) {
        FileConfiguration fc = plugin.playerFile(event.getPlayer());
        fc.set("last-kit", event.getKitName());
        try {
            fc.save(plugin.playerData(event.getPlayer()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
