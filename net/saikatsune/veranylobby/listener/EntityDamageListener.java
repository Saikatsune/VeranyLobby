package net.saikatsune.veranylobby.listener;


import net.saikatsune.veranylobby.data.VeranyLobby;
import net.saikatsune.veranylobby.lms.manager.LMSManager;
import net.saikatsune.veranylobby.manager.LocationManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class EntityDamageListener implements Listener {

    private VeranyLobby veranyLobby = VeranyLobby.getInstance();

    private LMSManager lmsManager = veranyLobby.getLmsManager();
    private LocationManager locationManager = veranyLobby.getLocationManager();

    @EventHandler
    public void on(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if(!veranyLobby.getLmsPlayers().contains(p)) {
                e.setCancelled(true);
                if(e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
                    p.teleport(veranyLobby.getLocationManager().getLocation("Join Location"));
                }
            } else {
                if(!lmsManager.isStarted()) {
                    e.setCancelled(true);
                } else {
                    if(veranyLobby.getLmsSpectators().contains(p.getKiller())) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void on(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player) {
            if(e.getDamager() instanceof Player) {
                Player p = (Player) e.getEntity();
                Player a = (Player) e.getDamager();

                if(veranyLobby.getLmsSpectators().contains(a)) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void on(PlayerDeathEvent e) {
        Player p = e.getEntity();
        e.setDeathMessage("");
        p.getKiller().setHealth(20);

        new BukkitRunnable() {
            @Override
            public void run() {
                p.spigot().respawn();
            }
        }.runTaskLater(veranyLobby, 20);
    }

    @EventHandler
    public void on(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        e.setRespawnLocation(locationManager.getLocation("Join Location"));
        lmsManager.leaveLMS(p);
    }

    @EventHandler
    public void on(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

}
