package com.mhawthor.ranks.utils;

import com.mhawthor.ranks.CRanks;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Set;

import static com.mhawthor.ranks.CRanks.playerConfig;
import static com.mhawthor.ranks.CRanks.rank;
import static com.mhawthor.ranks.CRanks.ranks;
import static com.mhawthor.ranks.listeners.KillListeners.mobkill;
import static com.mhawthor.ranks.listeners.KillListeners.playerkill;

public class ReqMethods {

    public static int biggest;


    public static Rank getRankByName(String name) {
        int key = -1;
        Rank r = null;
        for (int i = 0; i < ranks.size(); i++) {
            if (ranks.get(i).getName().equalsIgnoreCase(name)) {
                key = i;
            }
        }
        if (key != -1) {
            r = ranks.get(key);
        }
        return r;
    }

    public static Rank getRankByLevel(long level) {
        int key = 0;
        for (int i = 0; i < ranks.size(); i++) {
            if (ranks.get(i).getLevel() == level) {
                key = i;
            }
        }
        return ranks.get(key);
    }

    public static Rank getRankById(short id) {
        int key = 0;
        for (int i = 0; i < ranks.size(); i++) {
            if (ranks.get(i).getId() == id) {
                key = i;
                break;
            }
        }
        return ranks.get(key);
    }

    public static short getIdByName(String rankName) {
        int key = 0;
        for (int i = 0; i < ranks.size(); i++) {
            if (ranks.get(i).getName().equals(rankName)) {
                key = i;
            }
        }
        return ranks.get(key).getId();
    }

    public static void setRank(Player p, short rankID) {
        Rank r = getRankById(rankID);
        VaultM.perms.playerRemoveGroup(p, VaultM.perms.getPrimaryGroup(p));
        VaultM.perms.playerAddGroup(p, r.getGroupName());
        CRanks.rank.put(p, r.getId());
        playerConfig.get(p).getConfig().set("R", rankID);
        playerConfig.get(p).saveConfig();
    }

    public static boolean check(Player p, Rank r) {
        boolean b = true;
        if (r.getItems() != null) {
            for (ItemStack item : r.getItems()) {
                if (item == null) continue;
                if (!hasItems(p.getInventory().getContents(), item.getType(), item.getAmount())) {
                    b = false;
                }
            }
        }
        if (p.getLevel() >= r.getExp() && b && mobkill.get(p) >= r.getMobKill() && playerkill.get(p) >= r.getPlayerKill()) {

            EconomyResponse response = VaultM.econ.withdrawPlayer(p, r.getMoney());
            b = response.transactionSuccess();


        }else b = false;
        return b;
    }

    public static int valueOf(String text, char c) {
        int number = 0;

        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == c) {
                number = i;
                break;
            }
        }
        return number + 1;
    }

    public static boolean hasItems(ItemStack[] in, Material material, int amount) {
        int s = 0;
        for (ItemStack item : in) {
            if (item == null) continue;
            if (item.getType() == material) {
                s += item.getAmount();
            }
        }
        return s >= amount;
    }


    public static void loadRanks() {
        Set<String> list = null;
        ArrayList<Integer> liste = new ArrayList<>();
        liste.add(0);
        list = CRanks.config.getConfig().getConfigurationSection("ranklar").getKeys(false);
        for (String s : list) {
            Rank rank = new Rank();
            rank.setName(s);
            for (String str : CRanks.config.getConfig().getConfigurationSection("ranklar." + s).getKeys(false)) {
                switch (str) {
                    case "sira":
                        rank.setId((short) CRanks.config.getConfig().getInt("ranklar." + s + "." + str));
                        liste.add(CRanks.config.getConfig().getInt("ranklar." + s + ".sira"));
                        break;
                    case "grupAdi":
                        rank.setGroupName(CRanks.config.getConfig().getString("ranklar." + s + "." + str));
                        break;
                    case "adaLeveli":
                        rank.setLevel(CRanks.config.getConfig().getLong("ranklar." + s + "." + str));
                        break;
                    case "para":
                        rank.setMoney(CRanks.config.getConfig().getLong("ranklar." + s + "." + str));
                        break;
                    case "exp":
                        rank.setExp((float) CRanks.config.getConfig().getInt("ranklar." + s + "." + str));
                        break;
                    case "gerekliItemler":
                        for (String s1 : CRanks.config.getConfig().getStringList("ranklar." + s + "." + str)) {
                            String m = s1.substring(0, valueOf(s1, ':') - 1);
                            int amount = Integer.parseInt(s1.substring(valueOf(s1, ':')));
                            Material material = Material.valueOf(m);
                            ItemStack item = new ItemStack(material);
                            item.setAmount(amount);
                            rank.addItem(item);
                        }
                        break;
                    case "MobKill":
                        rank.setMobKill(CRanks.config.getConfig().getInt("ranklar." + s + "." + str));
                        break;
                    case "PlayerKill":
                        rank.setPlayerKill(CRanks.config.getConfig().getInt("ranklar." + s + "." + str));
                        break;
                }
            }
            ranks.add(rank);
        }
        int[] array = new int[liste.size()];
        for (int i = 0; i < liste.size(); i++) {
            array[i] = liste.get(i);
        }

        biggest = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > biggest) {
                biggest = array[i];
            }
        }


    }
}
