package com.mhawthor.ranks;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import listeners.PlayerJoin;
import listeners.PlayerQuit;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import utils.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import utils.events.PlayerRankUpEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static utils.RankMethod.*;

public class CRanks extends JavaPlugin {



    public static HashMap<Material,Integer> map = new HashMap<>();
    public static ArrayList<Rank> ranks = new ArrayList<Rank>();
    public static Configuration config;
    public static Configuration var;
    public static HashMap<Player, Short> rank = new HashMap<>();
    private static boolean skyblockPlugin;
    private static int biggest;
    public void onEnable() {
        if (!VaultM.setupEconomy() ||  !VaultM.setupPermissions()) {
            Bukkit.getConsoleSender().sendMessage("Gerekli Eklentiler sunucuda bulunamadi.Eklenti Devre Disi.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
        Plugin p = Bukkit.getServer().getPluginManager().getPlugin("ASkyBlock");
        skyblockPlugin = p != null;
        var = new Configuration(this);
        var.saveConfig();
        config = new Configuration(this, "ranklar.yml", true);
        config.saveConfig();
        loadRanks();
        for (Player pl : Bukkit.getOnlinePlayers()) {
            rank.put(pl, (short) var.getConfig().getInt(pl.getName()));
        }
            Bukkit.getConsoleSender().sendMessage("Started..");


    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("rankup") || cmd.getName().equalsIgnoreCase("rutbeatla")) {
            Player player = (Player) sender;
            long islandLevel = 0;
            if (skyblockPlugin) {
                ASkyBlockAPI skyBlockAPI = ASkyBlockAPI.getInstance();
                skyBlockAPI.calculateIslandLevel(player.getUniqueId());
                islandLevel = skyBlockAPI.getLongIslandLevel(player.getUniqueId());
            }
            if (rank.get(player) != biggest) {
            Rank currentRank = getRankById(rank.get(player));
            Rank nextRank = getRankById((short) (rank.get(player) + 1));

            if (check(player, nextRank)) {
                if (skyblockPlugin) {
                    if (islandLevel >= nextRank.getLevel()) {
                        if (nextRank.getItems() != null) {
                            for (ItemStack item : nextRank.getItems()) {
                                player.getInventory().removeItem(item);
                            }
                            player.setLevel((int) (player.getLevel() - nextRank.getExp()));
                            VaultM.perms.playerAddGroup(player, nextRank.getGroupName());
                            String rtb =config.getConfig().getString("RutbeAtlamaMesaji");
                            rtb = rtb.replaceAll("<player>",player.getName());
                            rtb = rtb.replaceAll("<rank>", nextRank.getName());
                            player.sendMessage(Chat.colored(rtb));
                            rank.put(player, nextRank.getId());
                            CRanks.var.getConfig().set(player.getName(), CRanks.rank.get(player));
                            CRanks.var.saveConfig();
                            Bukkit.getPluginManager().callEvent(new PlayerRankUpEvent(player, nextRank, currentRank));
                        }
                    } else {
                        String ykm = config.getConfig().getString("YetersizKosulMesaji");
                        ykm = ykm.replaceAll("<player>", player.getName());
                        ykm = ykm.replaceAll("<rank>", nextRank.getName());
                        player.sendMessage(Chat.colored(ykm));
                        player.sendMessage(Chat.colored("    &cGerekenler:"));
                        if (nextRank.getMoney() > 0) {
                            player.sendMessage(Chat.colored("&aPara:" + nextRank.getMoney()));
                        }
                        if (nextRank.getLevel() > 0) {
                            player.sendMessage("Ada Leveli:" + nextRank.getLevel());
                        }
                        if (nextRank.getExp() > 0) {
                            player.sendMessage("Level:" + nextRank.getExp());
                        }
                        if (nextRank.getItems() != null && nextRank.getItems().size() != 0) {
                            player.sendMessage("İtemler:");
                            for (ItemStack item : nextRank.getItems()) {
                                player.sendMessage(Chat.colored("&b" + item.getType() +":"+item.getAmount()));
                            }
                        }
                    }
                } else {
                    if (nextRank.getItems() != null) {
                        for (ItemStack item : nextRank.getItems()) {
                            player.getInventory().removeItem(item);
                        }
                        player.setLevel((int) (player.getLevel() - nextRank.getExp()));
                        VaultM.perms.playerAddGroup(player, nextRank.getGroupName());
                        String ykm = config.getConfig().getString("RutbeAtlamaMesaji");
                        ykm = ykm.replaceAll("<player>", player.getName());
                        ykm = ykm.replaceAll("<rank>", nextRank.getName());
                        player.sendMessage(Chat.colored(ykm));
                        rank.put(player, nextRank.getId());
                        CRanks.var.getConfig().set(player.getName(), CRanks.rank.get(player));
                        CRanks.var.saveConfig();
                        Bukkit.getPluginManager().callEvent(new PlayerRankUpEvent(player, nextRank, currentRank));
                    }
                }
            } else {
                String ykm = config.getConfig().getString("YetersizKosulMesaji");
                ykm = ykm.replaceAll("<player>", player.getName());
                ykm = ykm.replaceAll("<rank>", nextRank.getName());
                player.sendMessage(Chat.colored(ykm));
                player.sendMessage(Chat.colored("    &cGerekenler:"));
                if (nextRank.getMoney() > 0) {
                    player.sendMessage(Chat.colored("&aPara:" + nextRank.getMoney()));
                }
                if (nextRank.getLevel() > 0) {
                    player.sendMessage("Ada Leveli:" + nextRank.getLevel());
                }
                if (nextRank.getExp() > 0) {
                    player.sendMessage("Level:" + nextRank.getExp());
                }
                if (nextRank.getItems() != null && nextRank.getItems().size() != 0) {
                    player.sendMessage("İtemler:");
                    for (ItemStack item : nextRank.getItems()) {
                        player.sendMessage(Chat.colored("&b" + item.getType() +":"+item.getAmount()));
                    }
                }
            }
        }else {
                String str = config.getConfig().getString("MaksimumRutbe");
                str = str.replaceAll("<player>", player.getName());
                player.sendMessage(Chat.colored(str));
            }

        } else if (cmd.getName().equalsIgnoreCase("CRanks")) {
            if (args.length == 0) {
                sender.sendMessage(Chat.colored("&6/Skyrank configyenile // Configi yeniler."));
                return false;
            }
            if (args[0].equalsIgnoreCase("configyenile")) {
                if (sender instanceof ConsoleCommandSender) {
                    config.loadConfig();
                    config.saveConfig();
                    ranks.clear();
                    loadRanks();
                    Bukkit.getConsoleSender().sendMessage("Config Yenilendi!");
                } else if (sender instanceof Player) {
                    if (sender.hasPermission("cranks.reload") || sender.isOp()) {
                        Player p = (Player) sender;
                        config.loadConfig();
                        config.saveConfig();
                        ranks.clear();
                        loadRanks();
                        p.sendMessage("Config Yenilendi!");
                    }
                }
            }
        } else if (cmd.getName().equalsIgnoreCase("rutbelistesi")) {
            for (Rank r : ranks) {
                sender.sendMessage(Chat.colored("&6" + r.getName()) );
            }
        }else if (cmd.getName().equalsIgnoreCase("rankbelirle")) {
            if (sender.hasPermission("cranks.rankbelirle")) {
                if (args.length != 2) {
                    sender.sendMessage(Chat.colored("&6Kullanim : /rankbelirle <player> <rankAdi>"));
                    return false;
                }
                Rank r = RankMethod.getRankByName(args[1]);
                Player p = Bukkit.getPlayer(args[0]);
                if (r != null) {
                    if (!getRankById(rank.get(p)).getName().equalsIgnoreCase(r.getName())) {
                        sender.sendMessage(Chat.colored("&bOyuncunun Ranki basariyla degistirildi."));
                        Bukkit.getPlayer(args[0]).sendMessage(Chat.colored("&6Rank'ın " + RankMethod.getRankById(rank.get(p)).getName() + " rank'ından " + r.getName() + " rankına degistirildi!"));
                        RankMethod.setRank(p, r.getId());
                    } else {
                        p.sendMessage(Chat.colored("&6Kullanıcının rankı zaten değiştirilmek istenen ranka eşit."));
                    }
                }else {
                    p.sendMessage(Chat.colored("&6Böyle bir rank yok. Eğer Config'de yazmasına rağmen bu hatayı alıyorsanız /cranks configyenile komutunu kullanın veya sunucuyu yeniden başlatın."));
                }
            }
        }else if (cmd.getName().equalsIgnoreCase("rutbebilgi")) {

            if (args.length != 1) {
                sender.sendMessage(Chat.colored("&6Kullanim : /rutbebilgi <rank>"));
                return false;
            }
            Rank r = getRankByName(args[0]);
            if (r != null) {
                sender.sendMessage(Chat.colored("    &cGerekenler:"));
                if (r.getMoney() > 0) {
                    sender.sendMessage(Chat.colored("&aPara:" + r.getMoney()));
                }
                if (r.getLevel() > 0) {
                    sender.sendMessage("Ada Leveli:" + r.getLevel());
                }
                if (r.getExp() > 0) {
                    sender.sendMessage("Level:" + r.getExp());
                }
                if (r.getItems() != null&& r.getItems().size() != 0) {
                    sender.sendMessage("İtemler:");
                    for (ItemStack item : r.getItems()) {
                        sender.sendMessage(Chat.colored("&b" + item.getType() +":"+item.getAmount()));
                    }
                }
            }
            else {
                sender.sendMessage(Chat.colored("&6Boyle bir rank yok."));
            }
        }
        return false;
    }
    private boolean check(Player p, Rank r) {
        boolean b = true;
        if (r.getItems() != null) {
            for (ItemStack item : r.getItems()) {
                if (item == null) continue;
                if (!hasItems(p.getInventory().getContents(), item.getType(), item.getAmount())) {
                    b = false;
                }
            }
        }
        if (p.getLevel() >= r.getExp() && b) {
            EconomyResponse response = VaultM.econ.withdrawPlayer(p, r.getMoney());
            b = response.transactionSuccess();
        }
        return b;
    }
    private int valueOf(String text, char c) {
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
    private boolean hasItems(ItemStack[] in, Material material, int amount) {
        int s = 0;
        for (ItemStack item : in) {
            if (item == null) continue;
            if (item.getType() == material) {
                s +=item.getAmount();
            }
        }
        return s >= amount;
    }


    private void loadRanks() {
        Set<String> list = null;
        ArrayList<Integer> liste = new ArrayList<>();

            list = config.getConfig().getConfigurationSection("ranklar").getKeys(false);
            for (String s : list) {
                Rank rank = new Rank();
                rank.setName(s);
                for (String str : config.getConfig().getConfigurationSection("ranklar." + s).getKeys(false)) {
                    if (str.equalsIgnoreCase("sira")) {
                        rank.setId((short) config.getConfig().getInt("ranklar." + s + "." + str));
                        liste.add(config.getConfig().getInt("ranklar." + s + ".sira"));
                    }
                    if (str.equalsIgnoreCase("grupAdi"))
                        rank.setGroupName(config.getConfig().getString("ranklar." + s + "." + str));
                    if (str.equalsIgnoreCase("adaLeveli"))
                        rank.setLevel(config.getConfig().getLong("ranklar." + s + "." + str));
                    if (str.equalsIgnoreCase("para"))
                        rank.setMoney(config.getConfig().getLong("ranklar." + s + "." + str));
                    if (str.equalsIgnoreCase("exp"))
                        rank.setExp((float) config.getConfig().getInt("ranklar." + s + "." + str));
                    if (str.equalsIgnoreCase("gerekliItemler")) {
                        for (String s1 : config.getConfig().getStringList("ranklar." + s + "." + str)) {
                            String m = s1.substring(0, valueOf(s1, ':') - 1);
                            int amount = Integer.parseInt(s1.substring(valueOf(s1, ':')));
                            Material material = Material.valueOf(m);
                            ItemStack item = new ItemStack(material);
                            item.setAmount(amount);
                            rank.addItem(item);
                        }
                    }
                }
                ranks.add(rank);
            }
            int[] array = new int[liste.size()];
            for (int i = 0; i < liste.size(); i++) {
                array[i] = liste.get(i);
            }

            biggest = 0;
            for (int i =0; i < array.length; i++) {
                if (array[i] > biggest) {
                    biggest = array[i];
                }
            }
    }
}