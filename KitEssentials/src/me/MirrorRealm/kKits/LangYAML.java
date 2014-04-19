package me.MirrorRealm.kKits;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

public class LangYAML {
    private Main plugin;

    public LangYAML(Main plugin) {
        this.plugin = plugin;
    }
    private FileConfiguration lang = null;
    private File langFile = null;
    public FileConfiguration getLang() {
        if (lang == null) {
            reloadLang();
        }
        return lang;
    }
    public void saveDefaultLang() {
        if (langFile == null) {
            langFile = new File(plugin.getDataFolder(), "lang.yml");
        }
        if (!langFile.exists()) {
            plugin.saveResource("lang.yml", false);
        }
    }
    public void reloadLang() {
        if (langFile == null) {
            langFile = new File(plugin.getDataFolder(), "lang.yml");
        }
        lang = YamlConfiguration.loadConfiguration(langFile);

        InputStream defConfigStream = plugin.getResource("lang.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            lang.setDefaults(defConfig);
        }
    }
    public void saveLang() {
        if (lang == null || langFile == null) {
            return;
        }
        try {
            this.getLang().save(langFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + langFile, ex);
        }
    }
}
