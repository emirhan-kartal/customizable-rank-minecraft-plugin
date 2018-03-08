package com.mhawthor.ranks.listeners;

import com.mhawthor.ranks.CRanks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.mhawthor.ranks.CRanks.playerConfig;

public class PlayerQuit implements Listener{



    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        playerConfig.get(e.getPlayer()).getConfig().set("R", CRanks.rank.get(e.getPlayer()));
        playerConfig.get(e.getPlayer()).getConfig().set("KM", KillListeners.mobkill.get(e.getPlayer()));
        playerConfig.get(e.getPlayer()).getConfig().set("K", KillListeners.playerkill.get(e.getPlayer()));
        playerConfig.get(e.getPlayer()).saveConfig();
    }
}
