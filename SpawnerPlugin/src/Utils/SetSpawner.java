package Utils;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.mhawthor.spawner.MainSpawner;

public class SetSpawner {


    public static void setSpawners() {
        ArrayList<Spawner> list = new ArrayList<Spawner>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (MainSpawner.spawners.getConfig().getStringList(p.getName()) != null) {
                for (String s : MainSpawner.spawners.getConfig().getStringList(p.getName())) {
                    list.add(SpawnerMethod.toSpawner(s));
                }
                MainSpawner.spawner.put(p, list);
            }
        }
    }

}
