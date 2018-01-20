package Listeners;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.mhawthor.spawner.MainSpawner;
import com.mhawthor.spawner.MainSpawner.SpawnerType;

import Utils.Spawner;

public class PlayerBlockPlace implements Listener{
	
	
	
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e)
	{
		if (e.getItemInHand().hasItemMeta())
		{
			if (e.getItemInHand().getItemMeta().hasDisplayName())
			{
				if (e.getItemInHand().getItemMeta().getDisplayName() == "Iron Spawner" && e.getItemInHand().getType() == Material.MOB_SPAWNER)
				{
					e.getBlock().setType(Material.IRON_BLOCK);
					MainSpawner.spawner.get(e.getPlayer()).add(new Spawner(e.getPlayer().getWorld().getName(), e.getBlock().getX(),e.getBlock().getY(),e.getBlock().getZ(),SpawnerType.IRON));
					ArrayList<String> list = new ArrayList<String>();
					for (Spawner s : MainSpawner.spawner.get(e.getPlayer()))
					{
						list.add(s.toString());
					
					}
					MainSpawner.spawners.getConfig().set(e.getPlayer().getName(), list);
				}
			}
		}
	}

}
