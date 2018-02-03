package listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import utils.events.PlayerRankUpEvent;

public class PlayerRankUp implements Listener{



    @EventHandler
    public void onRankUp(PlayerRankUpEvent e) {
        e.getPlayer().sendMessage("Rank eventi gerçekleşti.");
    }




}
