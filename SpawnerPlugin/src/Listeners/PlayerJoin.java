package Listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.mhawthor.spawner.MainSpawner;

import Utils.SpawnerMethod;

public class PlayerJoin implements Listener{
	
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		ArrayList<String> stringSpawnerList = (ArrayList<String>) MainSpawner.spawners.getConfig().getStringList(e.getPlayer().getName());
		if (stringSpawnerList != null)
		{
			for (String s : stringSpawnerList)
			{
				MainSpawner.spawner.get(e.getPlayer()).add(SpawnerMethod.toSpawner(s));
			}
			for (OfflinePlayer p : Bukkit.getOfflinePlayers())
			{
				
			}
		}
		
		
	}

}
