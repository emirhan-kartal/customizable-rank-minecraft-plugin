package com.mhawthor.ranks.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;

public class KillListeners implements Listener {

    public static HashMap<Player,Integer> mobkill = new HashMap<>();
    public static HashMap<Player,Integer> playerkill = new HashMap<>();

    @EventHandler
    public void onEntityKill(EntityDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            mobkill.put(e.getEntity().getKiller(), mobkill.get(e.getEntity().getKiller()) + 1);
        }

    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            playerkill.put(e.getEntity().getKiller(),mobkill.get(e.getEntity().getKiller())+1);
        }
    }
}
