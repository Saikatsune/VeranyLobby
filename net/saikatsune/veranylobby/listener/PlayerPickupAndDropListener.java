package net.saikatsune.veranylobby.listener;

import net.saikatsune.veranylobby.data.VeranyLobby;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickupAndDropListener implements Listener {

    private VeranyLobby veranyLobby = VeranyLobby.getInstance();

    @EventHandler
    public void on(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if(!veranyLobby.getEditorMode().contains(p)) {
            if(!veranyLobby.getLmsPlayers().contains(p)) {
                e.setCancelled(true);
                if(veranyLobby.getLmsSpectators().contains(p)) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void on(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        if(!veranyLobby.getEditorMode().contains(p)) {
            if(!veranyLobby.getLmsPlayers().contains(p)) {
                e.setCancelled(true);
                if(veranyLobby.getLmsSpectators().contains(p)) {
                   e.setCancelled(true);
                }
            }
        }
    }

}
