package listeners;

import com.mhawthor.ranks.CRanks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener{



    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        CRanks.var.getConfig().set(e.getPlayer().getName(), CRanks.rank.get(e.getPlayer()));
        CRanks.var.saveConfig();
    }
}
