package Listeners;

import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.mhawthor.spawner.MainSpawner;

import Utils.Spawner;
import Utils.SpawnerMethod;

public class PlayerJoin implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        ArrayList<String> stringSpawnerList = (ArrayList<String>) MainSpawner.spawners.getConfig().getStringList(e.getPlayer().getName());
        ArrayList<Spawner> sp;
        if (MainSpawner.spawner.get(e.getPlayer()) != null) {
            sp = MainSpawner.spawner.get(e.getPlayer());
        } else {
            sp = new ArrayList<Spawner>();
        }
        if (stringSpawnerList != null) {
            for (String s : stringSpawnerList) {
                sp.add(SpawnerMethod.toSpawner(s));
            }
            MainSpawner.spawner.put(e.getPlayer(), sp);
        }


    }

}
