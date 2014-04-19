package me.MirrorRealm.CustomEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class KitEssentialsEquipKitEvent extends Event{
    private static final HandlerList handlers = new HandlerList();
    Player player;
    String kit;
    boolean cancelled = false;
    public KitEssentialsEquipKitEvent(Player player, String kit) {
        this.player = player;
        this.kit = kit;
    }
    public Player getPlayer(){
        return this.player;
    }
    public boolean isCancelled(){
        return cancelled;
    }
    public void setCancelled(boolean t){
        cancelled = t;
    }
    public String getKitName(){
        return this.kit;
    }
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
