package listeners;

import com.mhawthor.ranks.CRanks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import utils.RankMethod;

public class PlayerJoin implements Listener{


    @EventHandler
    public void Join(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPlayedBefore()) {
            RankMethod.setRank(p, (short) 0);
        }else {
            short str = (short) CRanks.var.getConfig().getInt(e.getPlayer().getName());
            CRanks.rank.put(e.getPlayer(),str);
            CRanks.var.getConfig().set(e.getPlayer().getName(), CRanks.rank.get(e.getPlayer()));
            CRanks.var.saveConfig();
        }
    }


}
