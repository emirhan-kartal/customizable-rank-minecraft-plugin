package Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.mhawthor.spawner.MainSpawner;

public class SetSpawner {
	
	
	
	
	public static void setSpawners()
	{
		for (Player p : Bukkit.getOnlinePlayers())
		{
			for (String s : MainSpawner.spawners.getConfig().getStringList(p.getName()))
			{
				MainSpawner.spawner.get(p).add(SpawnerMethod.toSpawner(s));
			}
		}
	}

}
