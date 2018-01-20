package com.mhawthor.spawner;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import Listeners.PlayerBlockPlace;
import Listeners.PlayerJoin;
import Utils.Configuration;
import Utils.RandomInt;
import Utils.SetSpawner;
import Utils.Spawner;

public class MainSpawner extends JavaPlugin implements Listener{
	
	public static HashMap<Player,List<Spawner>> spawner = new HashMap<Player,List<Spawner>>();
	public static Configuration spawners;
	@Override
	public void onEnable()
	{	
	    spawners = new Configuration(this,"spawners.db");
		spawners.saveConfig();
		SetSpawner.setSpawners();
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
			{

				@Override
				public void run() 
				{
					for (Player p : Bukkit.getOnlinePlayers())
					{
						
						for (Spawner sp : spawner.get(p))
						{
							if (sp.getType() == SpawnerType.DIAMOND)
							{
								ItemStack item = new ItemStack(Material.DIAMOND);
								if (RandomInt.Take() <= 25) item.setAmount(2);
								sp.getWorld().dropItem(sp.getLocation(), item);
							}
							if (sp.getType() == SpawnerType.IRON)
							{
								ItemStack item = new ItemStack(Material.IRON_INGOT);
								if (RandomInt.Take() <= 25) item.setAmount(2);
								sp.getWorld().dropItem(sp.getLocation(), item);
							}
						}
					}
				}
				
			},0,40);
	}
	
	
	
	public enum SpawnerType
	{
		DIAMOND,
		IRON
	}
	public void registerEvents()
	{
		Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerBlockPlace(), this);
	}

}
