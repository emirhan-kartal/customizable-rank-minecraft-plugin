package com.mhawthor.ranks;

import com.mhawthor.ranks.listeners.KillListeners;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.mhawthor.ranks.listeners.PlayerJoin;
import com.mhawthor.ranks.listeners.PlayerQuit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import com.mhawthor.ranks.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import com.mhawthor.ranks.utils.events.PlayerRankUpEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static com.mhawthor.ranks.listeners.KillListeners.mobkill;
import static com.mhawthor.ranks.listeners.KillListeners.playerkill;
import static com.mhawthor.ranks.utils.Chat.colored;
import static com.mhawthor.ranks.utils.ReqMethods.*;

public class CRanks extends JavaPlugin {



    public static HashMap<Material,Integer> map = new HashMap<>();
    public static ArrayList<Rank> ranks = new ArrayList<Rank>();
    public static Configuration config;
    public static HashMap<Player, Short> rank = new HashMap<>();
    public static HashMap<Player,Configuration> playerConfig = new HashMap<>();
    private static boolean skyblockPlugin;
    public static File f;
    static String defaultGroup;
    public void onEnable() {
        if (!VaultM.setupEconomy() ||  !VaultM.setupPermissions()) {
            Bukkit.getConsoleSender().sendMessage("Gerekli Eklentiler sunucuda bulunamadi.Eklenti Devre Disi.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        if (!VersionCheck.versionCheck()) {
            Bukkit.getConsoleSender().sendMessage("Yeni Bir surum mevcut! İlgili forumlardan indirebilirsiniz.");
        }
        f = new File(getDataFolder() + "\\CRranksData");
        if (!f.exists()) {
            f.mkdirs();
        }

        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
        Bukkit.getPluginManager().registerEvents(new KillListeners(),this);
        Plugin p = Bukkit.getServer().getPluginManager().getPlugin("ASkyBlock");
        skyblockPlugin = p != null;
        config = new Configuration(this, "ranklar.yml", true);
        config.saveConfig();
        defaultGroup = config.getConfig().getString("VarsayilanGrup");
        Rank rank = new Rank();
        rank.setGroupName(defaultGroup);
        rank.setId((short)0);
        rank.setName(defaultGroup);
        ranks.add(rank);
        loadRanks();
        for (Player pl : Bukkit.getOnlinePlayers()) {
            File c = new File(f.getPath(),pl.getUniqueId() + ".yml");
            Configuration cf = new Configuration(f,pl.getUniqueId() + ".yml");
            if (!c.exists()) {

                cf.getConfig().set("R",0);
                cf.getConfig().set("KM",0);
                cf.getConfig().set("K",0);
                cf.saveConfig();
                this.rank.put(pl, (short) 0);
                mobkill.put(pl,0);
                playerkill.put(pl,0);
                playerConfig.put(pl,cf);
            }else {

                this.rank.put(pl, (short) cf.getConfig().getInt("R"));
                mobkill.put(pl,cf.getConfig().getInt("KM"));
                playerkill.put(pl,cf.getConfig().getInt("K"));
                playerConfig.put(pl,cf);
            }

        }
            Bukkit.getConsoleSender().sendMessage("Started..");


    }

    @Override
    public void onDisable() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Configuration playerConfig = this.playerConfig.get(p);
            playerConfig.getConfig().set("R", rank.get(p));
            playerConfig.getConfig().set("KM",mobkill.get(p));
            playerConfig.getConfig().set("K",playerkill.get(p));
            playerConfig.saveConfig();

        }
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
                            set(player, currentRank, nextRank);
                        } else {
                            yetersiz(player,nextRank);
                        }
                    } else {
                        set(player, currentRank, nextRank);
                    }
                } else {
                    yetersiz(player,nextRank);

                }
        }else {
                String str = config.getConfig().getString("MaksimumRutbe");
                str = str.replaceAll("<player>", player.getName());
                player.sendMessage(colored(str));
            }

        } else if (cmd.getName().equalsIgnoreCase("CRanks")) {
            if (args.length == 0) {
                sender.sendMessage(colored("&6/Skyrank configyenile // Configi yeniler."));
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
                sender.sendMessage(colored("&6" + r.getName()) );
            }
        }else if (cmd.getName().equalsIgnoreCase("rankbelirle")) {
            if (sender.hasPermission("cranks.rankbelirle")) {
                if (args.length != 2) {
                    sender.sendMessage(colored("&6Kullanim : /rankbelirle <player> <rankAdi>"));
                    return false;
                }
                Player player = (Player) sender;
                Rank r = getRankByName(args[1]);
                Rank current = getRankById(rank.get(player));
                Player p = Bukkit.getPlayer(args[0]);
                if (r != null) {
                    if (!getRankById(rank.get(p)).getName().equalsIgnoreCase(r.getName())) {
                        VaultM.perms.playerRemoveGroup(p,current.getGroupName());
                        if (VaultM.perms.playerAddGroup(p, r.getGroupName())) {

                            sender.sendMessage(colored("&bOyuncunun Ranki basariyla degistirildi."));
                            p.sendMessage(colored("&6Rank'ın " + getRankById(rank.get(p)).getName() + " rank'ından " + r.getName() + " rankına degistirildi!"));
                            setRank(p, r.getId());
                        }else {
                            player.sendMessage(colored("&cRank'ın grubunda bir problem var.Admine bildirin lütfen!"));
                        }

                    } else {
                        p.sendMessage(colored("&6Kullanıcının rankı zaten değiştirilmek istenen ranka eşit."));
                    }
                }else {
                    p.sendMessage(colored("&6Böyle bir rank yok. Eğer Config'de yazmasına rağmen bu hatayı alıyorsanız /cranks configyenile komutunu kullanın veya sunucuyu yeniden başlatın."));
                }
            }
        }else if (cmd.getName().equalsIgnoreCase("rutbebilgi")) {

            if (args.length != 1) {
                sender.sendMessage(colored("&6Kullanim : /rutbebilgi <rank>"));
                return false;
            }
            Rank r = getRankByName(args[0]);
            if (r != null) {
                sender.sendMessage(colored("    &cGerekenler:"));
                if (r.getMoney() > 0) {
                    sender.sendMessage(colored("&aPara:" + r.getMoney()));
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
                        sender.sendMessage(colored("&b" + item.getType() +":"+item.getAmount()));
                    }
                }
                if (r.getPlayerKill() > 0) {
                    sender.sendMessage(colored("&6Oyuncu Oldurme: " + r.getPlayerKill()));
                }
                if (r.getMobKill() > 0) {
                    sender.sendMessage(colored("&6Mob Oldurme: " + r.getMobKill()));
                }
            }
            else {
                sender.sendMessage(colored("&6Böyle bir rank yok."));
            }
        }
        return false;
    }

    private void yetersiz(Player player, Rank nextRank) {
        String ykm = config.getConfig().getString("YetersizKosulMesaji");
        ykm = ykm.replaceAll("<player>", player.getName());
        ykm = ykm.replaceAll("<rank>", nextRank.getName());
        player.sendMessage(colored(ykm));
        player.sendMessage(colored("    &cGerekenler:"));
        if (nextRank.getMoney() > 0) {
            player.sendMessage(colored("&aPara:" + nextRank.getMoney()));
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
                player.sendMessage(colored("&b" + item.getType() +":"+item.getAmount()));
            }
        }
        if (nextRank.getPlayerKill() > 0) {
            player.sendMessage(colored("&6Oyuncu Oldurme: " + nextRank.getPlayerKill()));
        }
        if (nextRank.getMobKill() > 0) {
            player.sendMessage(colored("&6Mob Oldurme: " + nextRank.getMobKill()));
        }
    }

    private void set(Player player, Rank currentRank, Rank nextRank) {
        if (nextRank.getItems() != null) {
            for (ItemStack item : nextRank.getItems()) {
                player.getInventory().removeItem(item);
            }
            player.setLevel((int) (player.getLevel() - nextRank.getExp()));

            VaultM.perms.playerRemoveGroup(player,currentRank.getGroupName());
            VaultM.perms.playerAddGroup(player, nextRank.getGroupName());

            String rtb =config.getConfig().getString("RutbeAtlamaMesaji");
            rtb = rtb.replaceAll("<player>",player.getName());
            rtb = rtb.replaceAll("<rank>", nextRank.getName());
            player.sendMessage(colored(rtb));
            rank.put(player, nextRank.getId());
            playerConfig.get(player).getConfig().set("R", CRanks.rank.get(player));
            playerConfig.get(player).saveConfig();
            Bukkit.getPluginManager().callEvent(new PlayerRankUpEvent(player, nextRank, currentRank));
        }
    }



}