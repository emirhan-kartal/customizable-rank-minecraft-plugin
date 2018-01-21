package Utils;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.mhawthor.spawner.MainSpawner;
import com.mhawthor.spawner.MainSpawner.SpawnerType;

public class SpawnerMethod {


    public static Spawner toSpawner(String spawnerInfo) {
        String[] list = spawnerInfo.split(",");

        Spawner sp = new Spawner(list[0], Double.parseDouble(list[1]), Double.parseDouble(list[2]), Double.parseDouble(list[3]), SpawnerType.valueOf(list[4]));


        return sp;
    }

    public static ArrayList<Spawner> convertSpawnerArray(Player p) {
        ArrayList<String> stringSpawnerList = (ArrayList<String>) MainSpawner.spawners.getConfig().getStringList(p.getName());
        ArrayList<Spawner> sp = new ArrayList<Spawner>();
        for (String s : stringSpawnerList) {
            sp.add(SpawnerMethod.toSpawner(s));
        }
        return sp;
    }

    public static boolean isSpawner(Player p, Block b) {
        boolean turn = false;
        String str = b.getWorld().getName() + "," + b.getX() + "," + b.getY() + "," + b.getZ();
        if (b.getType() == Material.IRON_BLOCK) {
            str += ",IRON";
        } else if (b.getType() == Material.DIAMOND_BLOCK)
                str += ",DIAMOND";

        Spawner sp = SpawnerMethod.toSpawner(str);
        if (MainSpawner.spawner.get(p) != null) {
            for (Spawner s : MainSpawner.spawner.get(p)) {
                if (s.toString().equalsIgnoreCase(sp.toString())) {
                    turn = true;
                }
            }
        }


        return turn;
    }

}
