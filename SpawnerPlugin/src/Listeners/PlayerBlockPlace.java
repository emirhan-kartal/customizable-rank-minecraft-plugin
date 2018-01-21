package Listeners;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.mhawthor.spawner.MainSpawner;
import com.mhawthor.spawner.MainSpawner.SpawnerType;

import Utils.Spawner;
import Utils.Text;

public class PlayerBlockPlace implements Listener {


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.getItemInHand().hasItemMeta()) {

            if (e.getItemInHand().getItemMeta().hasDisplayName()) {


                    Spawner sp = null;
                    if (e.getItemInHand().getType() == Material.IRON_BLOCK){
                        if (e.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(Text.colored("&c&l[&b&lIron Spawner&c&l]")) )
                         sp = new Spawner(e.getPlayer().getWorld().getName(), e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ(), SpawnerType.IRON);
                    }
                    if (e.getItemInHand().getType() == Material.DIAMOND_BLOCK)
                    {
                        if (e.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(Text.colored("&c&l[&a&lDiamond Spawner&c&l]")))
                         sp = new Spawner(e.getPlayer().getWorld().getName(), e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ(), SpawnerType.DIAMOND);
                    }
                    ArrayList<Spawner> liste;
                    if (MainSpawner.spawner.get(e.getPlayer()) == null) {
                        liste = new ArrayList<Spawner>();
                         liste.add(sp);
                    } else {
                        liste = MainSpawner.spawner.get(e.getPlayer());
                        liste.add(sp);
                    }
                    MainSpawner.spawner.put(e.getPlayer(), liste);
                    ArrayList<String> list = new ArrayList<String>();
                    for (Spawner s : MainSpawner.spawner.get(e.getPlayer())) {
                        list.add(s.toString());


                    }
                    MainSpawner.spawners.getConfig().set(e.getPlayer().getName(), list);
                    MainSpawner.spawners.saveConfig();

            }
        }
    }

}
