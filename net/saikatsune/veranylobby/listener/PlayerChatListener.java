package net.saikatsune.veranylobby.listener;

import net.saikatsune.veranylobby.data.VeranyLobby;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    private VeranyLobby veranyLobby = VeranyLobby.getInstance();

    @EventHandler(priority = EventPriority.MONITOR)
    public void on(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if(veranyLobby.isSilenced()) {
            if(!p.hasPermission("verany.bypass.silence")) {
                e.setCancelled(true);
                p.sendMessage(veranyLobby.getPrefix() + "§cThe server is currently silenced!");
            }
        }
    }

}
