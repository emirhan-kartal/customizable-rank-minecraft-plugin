package Listeners;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mhawthor.spawner.MainSpawner;
import com.mhawthor.spawner.MainSpawner.SpawnerType;

import Utils.Spawner;
import Utils.SpawnerMethod;
import Utils.Text;

public class BlockBreak implements Listener {


    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        if (e.getBlock().getType() == Material.IRON_BLOCK || e.getBlock().getType() == Material.DIAMOND_BLOCK) {
            if (SpawnerMethod.isSpawner(e.getPlayer(), e.getBlock())) {

                ArrayList<Spawner> list = MainSpawner.spawner.get(e.getPlayer());
                Spawner sp = null;
                ItemStack item = null;

                String name = "";
                if (e.getBlock().getType() == Material.IRON_BLOCK) {
                    sp = new Spawner(e.getBlock().getWorld().getName(), e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ(), SpawnerType.IRON);
                    item = new ItemStack(Material.IRON_BLOCK);
                    name = Text.colored("&c&l[&b&lIron Spawner&c&l]");
                } else if (e.getBlock().getType() == Material.DIAMOND_BLOCK) {
                    sp = new Spawner(e.getBlock().getWorld().getName(), e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ(), SpawnerType.DIAMOND);
                    item = new ItemStack(Material.DIAMOND_BLOCK);
                    name = Text.colored("&c&l[&a&lDiamond Spawner&c&l]");
                }

                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).toString().equalsIgnoreCase(sp.toString())) list.remove(i);

                }
                MainSpawner.spawners.getConfig().set(e.getPlayer().getName(), list);
                MainSpawner.spawners.saveConfig();
                MainSpawner.spawner.put(e.getPlayer(), list);
                Text.colored("&bBaşarıyla Spawner Alındı!");
                MainSpawner.spawners.getConfig().set(e.getPlayer().getName(), list);
                MainSpawner.spawners.saveConfig();

                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(name);
                item.setItemMeta(meta);
                e.getPlayer().getWorld().dropItem(e.getPlayer().getLocation(), item);


            }
        }


    }

}
