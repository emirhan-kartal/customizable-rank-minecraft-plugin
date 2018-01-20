package Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.mhawthor.spawner.MainSpawner.SpawnerType;

public class Spawner {
	
	private String world;
	private double x;
	private double y;
	private double z;
	private SpawnerType type;

	
	
	public Spawner(String world ,int x,int y,int z, SpawnerType Type)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = Type;
		this.world = world;
	}
	
	public String toString()
	{
		return this.x + "," + this.y + "," + this.z + ","+ this.type.toString();
	}

	public Location getLocation()
	{
		return new Location(Bukkit.getWorld(this.world),this.x,this.y,this.z);
	}
	
	public SpawnerType getType()
	{
		return this.type;
	}
	public World getWorld()
	{
		return Bukkit.getWorld(this.world);
	}

}
