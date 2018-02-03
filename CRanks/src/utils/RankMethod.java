package utils;

import com.mhawthor.ranks.CRanks;
import org.bukkit.entity.Player;

public class RankMethod {

    public static Rank getRankByName(String name) {
        int key = -1;
        Rank r = null;
        for (int i = 0; i < CRanks.ranks.size(); i++) {
            if (CRanks.ranks.get(i).getName().equalsIgnoreCase(name)) {
                key = i;
            }
        }
        if (key != -1) {
            r = CRanks.ranks.get(key);
        }
        return r;
    }

    public static Rank getRankByLevel(long level) {
        int key = 0;
        for (int i = 0; i < CRanks.ranks.size(); i++) {
            if (CRanks.ranks.get(i).getLevel() == level) {
                key = i;
            }
        }
        return CRanks.ranks.get(key);
    }
    public static Rank getRankById(short id) {
        int key = 0;
        for (int i = 0; i < CRanks.ranks.size(); i++) {
            if (CRanks.ranks.get(i).getId() == id) {
                key = i;
                break;
            }
        }
        return CRanks.ranks.get(key);
    }

    public static short getIdByName(String rankName) {
        int key = 0;
        for (int i = 0; i < CRanks.ranks.size(); i++) {
            if (CRanks.ranks.get(i).getName().equals(rankName)) {
                key = i;
            }
        }
        return CRanks.ranks.get(key).getId();
    }

    public static void setRank(Player p,short rankID) {
        Rank r = getRankById(rankID);
        VaultM.perms.playerAddGroup(p,r.getGroupName());
        CRanks.rank.put(p,r.getId());
        CRanks.var.getConfig().set(p.getName(), CRanks.rank.get(p));
        CRanks.var.saveConfig();
    }
}
