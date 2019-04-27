package net.saikatsune.veranylobby.listener;

import net.saikatsune.veranylobby.data.VeranyLobby;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class BlockChangeListener implements Listener {

    private VeranyLobby veranyLobby = VeranyLobby.getInstance();

    @EventHandler
    public void on(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if(!veranyLobby.getEditorMode().contains(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if(!veranyLobby.getEditorMode().contains(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(ChunkUnloadEvent e) {
        e.setCancelled(true);
    }

}
