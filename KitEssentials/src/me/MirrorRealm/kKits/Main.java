package me.MirrorRealm.kKits;

import me.MirrorRealm.Commands.*;
import me.MirrorRealm.handlers.ItemDropEvent;
import me.MirrorRealm.other.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashSet;

public class Main extends JavaPlugin implements Listener {
    public final HashSet<String> cantkit = new HashSet<>();
    BookGui bookgui = new BookGui(this);
    Methods methods = new Methods(this);
    LangYAML langYAML = new LangYAML(this);
    ReloadConfig reloadconfig = new ReloadConfig(this);
    ItemDropEvent itemdropevent = new ItemDropEvent(this);
    Drop drop = new Drop(this);
    KitCommand kitCommand = new KitCommand(this);
    CreateKit createKit = new CreateKit(this);
    SaveDropCommand saveDropCommand = new SaveDropCommand(this);
    DelDropCommand delDropCommand = new DelDropCommand(this);
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
        reloadConfig();
        langYAML.getLang().options().copyDefaults(true);
        langYAML.saveDefaultLang();
        langYAML.reloadLang();
        getCommand("savedrop").setExecutor(saveDropCommand);
        getCommand("deldrop").setExecutor(delDropCommand);
        getCommand("createkit").setExecutor(createKit);
        getCommand("delkit").setExecutor(createKit);
        getCommand("drop").setExecutor(drop);
        getCommand("reloadkits").setExecutor(reloadconfig);
        getCommand("kit").setExecutor(kitCommand);
        getCommand("kits").setExecutor(kitCommand);
        Bukkit.getPluginManager().registerEvents(kitCommand, this);
        Bukkit.getPluginManager().registerEvents(itemdropevent, this);
        Bukkit.getPluginManager().registerEvents(bookgui, this);
    }

    public void onDisable() {
        this.saveConfig();
    }

    public BookGui getBook() {
        return this.bookgui;
    }

    public LangYAML getLangYAML() {
        return this.langYAML;
    }

    public void send(Player player, String string) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', langYAML.getLang().getString(string)));
    }

    public void send(Player player, String string1, String string2) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', langYAML.getLang().getString(string1).replace("{0}", string2)));
    }

    public void send(Player player, String string1, String string2, String string3) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', langYAML.getLang().getString(string1).replace("{0}", string2).replace("{1}", string3)));
    }

    public FileConfiguration kitFile() {
        File playerDir = new File(this.getDataFolder() + File.separator + "kits.yml");
        return YamlConfiguration.loadConfiguration(playerDir);
    }

    public File kitData() {
        return new File(this.getDataFolder() + File.separator + "kits.yml");
    }

    public FileConfiguration customFile(String s) {
        File playerDir = new File(this.getDataFolder() + File.separator + s.toLowerCase() + ".yml");
        return YamlConfiguration.loadConfiguration(playerDir);
    }

    public File customData(String s) {
        return new File(this.getDataFolder() + File.separator + s.toLowerCase() + ".yml");
    }
    public FileConfiguration playerFile(String s) {
        File playerDir = new File(this.getDataFolder() + File.separator + "userdata" + File.separator + s + ".yml");
        return YamlConfiguration.loadConfiguration(playerDir);
    }
    public FileConfiguration playerFile(Player player){
        return playerFile(player.getUniqueId().toString().replace("-", ""));
    }
    public FileConfiguration playerFile(OfflinePlayer player){
        return playerFile(player.getUniqueId().toString().replace("-", ""));
    }
    public File playerData(String s) {
        return new File(this.getDataFolder() + File.separator + "userdata" + File.separator + s + ".yml");
    }
    public File playerData(Player player) {
        return playerData(player.getUniqueId().toString().replace("-", ""));
    }
    public File playerData(OfflinePlayer player) {
        return playerData(player.getUniqueId().toString().replace("-", ""));
    }
    public Methods methods() {
        return this.methods;
    }
}
