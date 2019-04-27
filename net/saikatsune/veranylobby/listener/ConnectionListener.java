package net.saikatsune.veranylobby.listener;

import net.saikatsune.veranylobby.data.VeranyLobby;
import net.saikatsune.veranylobby.lms.manager.LMSManager;
import net.saikatsune.veranylobby.manager.LocationManager;
import net.saikatsune.veranylobby.manager.PlayerManager;
import net.saikatsune.veranylobby.utilities.Inventories;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

    private VeranyLobby veranyLobby = VeranyLobby.getInstance();
    private LocationManager locationManager = veranyLobby.getLocationManager();
    private Inventories inventories = veranyLobby.getInventories();
    private PlayerManager playerManager = veranyLobby.getPlayerManager();
    private LMSManager lmsManager = veranyLobby.getLmsManager();

    @EventHandler
    public void on(PlayerJoinEvent e) {
        e.setJoinMessage("");

        Player p = e.getPlayer();

        p.teleport(locationManager.getLocation("Join Location"));

        veranyLobby.getEditorMode().remove(p);

        if(veranyLobby.getHidePlayers().contains(p)) {
            veranyLobby.getHidePlayers().remove(p);
            for (Player all : Bukkit.getOnlinePlayers()) {
                p.showPlayer(all);
            }
        }

        for (Player all : veranyLobby.getHidePlayers()) {
            all.hidePlayer(p);
        }

        playerManager.resetPlayer(p);

        inventories.setHotbarInventory(p);
        playerManager.sendJoinMessage(p);
    }

    @EventHandler
    public void on(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        e.setQuitMessage("");

        if(veranyLobby.getLmsPlayers().contains(p)) {
            lmsManager.leaveLMS(p);
        }

    }

}
