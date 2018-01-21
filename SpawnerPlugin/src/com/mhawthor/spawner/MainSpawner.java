package com.mhawthor.spawner;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import Listeners.BlockBreak;
import Listeners.PlayerBlockPlace;
import Listeners.PlayerJoin;
import Utils.Configuration;
import Utils.RandomInt;
import Utils.SetSpawner;
import Utils.Spawner;
import Utils.Text;

public class MainSpawner extends JavaPlugin implements Listener {

    public static HashMap<Player, ArrayList<Spawner>> spawner = new HashMap<Player, ArrayList<Spawner>>();
    public static Configuration spawners;

    @Override
    public void onEnable() {
        spawners = new Configuration(this, "spawners.yml");
        spawners.saveConfig();
        SetSpawner.setSpawners();
        registerEvents();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (spawner.get(p) != null) {
                        for (Spawner sp : spawner.get(p)) {
                            if (sp.getType() == SpawnerType.DIAMOND) {
                                ItemStack item = new ItemStack(Material.DIAMOND);
                                if (RandomInt.Take() <= 25) item.setAmount(2);
                                sp.getWorld().dropItem(sp.getLocation(), item);
                            }
                            if (sp.getType() == SpawnerType.IRON) {
                                ItemStack item = new ItemStack(Material.IRON_INGOT);
                                if (RandomInt.Take() <= 25) item.setAmount(2);
                                sp.getWorld().dropItem(sp.getLocation(), item);
                            }
                        }
                    }

                }
            }

        }, 0, 50);
    }


    public enum SpawnerType {

        DIAMOND,
        IRON
    }

    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new BlockBreak(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerBlockPlace(), this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ironspver")) {
            Player p = (Player) sender;
            if (p.hasPermission("admin.sp")) {
                ItemStack item = new ItemStack(Material.IRON_BLOCK);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(Text.colored("&c&l[&b&lIron Spawner&c&l]"));
                item.setItemMeta(meta);
                p.getInventory().addItem(item);
                item.setType(Material.DIAMOND_BLOCK);
                meta = item.getItemMeta();
                meta.setDisplayName(Text.colored("&c&l[&a&lDiamond Spawner&c&l]"));
                item.setItemMeta(meta);
                p.getInventory().addItem(item);

            }
        }

        return false;

    }


}
