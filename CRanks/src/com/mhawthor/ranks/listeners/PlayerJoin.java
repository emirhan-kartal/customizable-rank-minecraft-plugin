package com.mhawthor.ranks.listeners;

import com.mhawthor.ranks.CRanks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import com.mhawthor.ranks.utils.Configuration;

import java.io.File;

import static com.mhawthor.ranks.CRanks.playerConfig;
import static com.mhawthor.ranks.CRanks.rank;
import static com.mhawthor.ranks.listeners.KillListeners.mobkill;
import static com.mhawthor.ranks.listeners.KillListeners.playerkill;
import static com.mhawthor.ranks.utils.ReqMethods.setRank;

public class PlayerJoin implements Listener{


    @EventHandler
    public void Join(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        File f = new File(CRanks.f.getPath(),p.getUniqueId()+".yml");
        if (!f.exists()) {
            Configuration player = new Configuration(CRanks.f, e.getPlayer().getUniqueId() + ".yml");
            rank.put(p, (short) 0);
            playerConfig.put(e.getPlayer(),player);
            player.getConfig().set("K",0);
            player.getConfig().set("KM",0);
            player.saveConfig();
            mobkill.put(p,0);
            playerkill.put(p,0);
            setRank(e.getPlayer(), (short) 0);
        }else {
            Configuration player = new Configuration(CRanks.f, p.getUniqueId() + ".yml");
            short str = (short) player.getConfig().getInt("R");
            playerConfig.put(e.getPlayer(),player);
            CRanks.rank.put(e.getPlayer(),str);
            mobkill.put(p,player.getConfig().getInt("KM"));
            playerkill.put(p,player.getConfig().getInt("K"));
            player.saveConfig();
        }
    }


}
