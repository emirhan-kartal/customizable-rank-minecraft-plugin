package com.mhawthor.ranks.utils.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import com.mhawthor.ranks.utils.Rank;

public class PlayerRankUpEvent extends Event implements Cancellable{


    private static final HandlerList handlers = new HandlerList();
    private Rank newRank;
    private Rank oldRank;
    private Player player;
    private boolean isCancelled;
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
    public PlayerRankUpEvent(Player player,Rank newRank, Rank oldRank) {
        this.newRank = newRank;
        this.oldRank = oldRank;
        this.player = player;
    }

    public Rank getNewRank() {
        return this.newRank;
    }
    public Rank getOldRank() {
        return this.oldRank;
    }
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        isCancelled = b;

    }
}
