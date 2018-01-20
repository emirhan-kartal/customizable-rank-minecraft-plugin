package Utils;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.mhawthor.spawner.MainSpawner;
import com.mhawthor.spawner.MainSpawner.SpawnerType;

public class SpawnerMethod {
	
	
	
	public static Spawner toSpawner(String spawnerInfo)
	{
		String[] list = spawnerInfo.split(",");
		
		Spawner sp = new Spawner(list[0],Integer.parseInt(list[1]),Integer.parseInt(list[2]), Integer.parseInt(list[3]),SpawnerType.valueOf(list[4]) );
		
		
		return sp;
	}

	public static ArrayList<Spawner> convertSpawnerArray(Player p)
	{
		ArrayList<String> stringSpawnerList = (ArrayList<String>) MainSpawner.spawners.getConfig().getStringList(p.getName());
		ArrayList<Spawner> sp = new ArrayList<Spawner>();
		for (String s : stringSpawnerList)
		{
			sp.add(SpawnerMethod.toSpawner(s));
		}
		return sp;
	}

}
