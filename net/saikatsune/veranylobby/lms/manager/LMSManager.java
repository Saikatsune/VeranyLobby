package net.saikatsune.veranylobby.lms.manager;

import net.saikatsune.veranylobby.data.VeranyLobby;
import net.saikatsune.veranylobby.manager.ItemManager;
import net.saikatsune.veranylobby.manager.LocationManager;
import net.saikatsune.veranylobby.manager.PlayerManager;
import net.saikatsune.veranylobby.utilities.Inventories;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class LMSManager {

    private VeranyLobby veranyLobby = VeranyLobby.getInstance();

    private LocationManager locationManager = veranyLobby.getLocationManager();
    private PlayerManager playerManager = veranyLobby.getPlayerManager();

    private Inventories inventories = new Inventories();

    private boolean created = false;
    private boolean started = false;

    private boolean enabled = veranyLobby.getConfig().getBoolean("TOURNAMENTS.ENABLED");

    private int minPlayers = veranyLobby.getConfig().getInt("TOURNAMENTS.MIN_PLAYERS");
    private int maxPlayers = veranyLobby.getConfig().getInt("TOURNAMENTS.MAX_PLAYERS");

    private int taskID;

    private int seconds;

    public void createLMS(Player p) {
        if(enabled) {
            if(!created) {
                if(Bukkit.getOnlinePlayers().size() >= minPlayers) {
                    if(!started) {
                        created = true;
                        seconds = 120;
                        Bukkit.broadcastMessage(veranyLobby.getPrefix() + "§a" + p.getName() + " has created a tournament! §7(Kit: " + veranyLobby.getTournamentKit().get("Kit") + ")");
                        Bukkit.broadcastMessage(veranyLobby.getPrefix() + "§aType /lms to join the tournament!");
                        joinLMS(p);
                        clearDrops();
                        startLMS();
                    } else {
                        p.sendMessage(veranyLobby.getPrefix() + "§cA tournament has already started!");
                    }
                } else {
                    p.sendMessage(veranyLobby.getPrefix() + "§cThere must be at least " + minPlayers + " players online to create tournaments!");
                }
            } else {
                p.sendMessage(veranyLobby.getPrefix() + "§cThere is already a tournament starting!");
            }
        } else {
            p.sendMessage(veranyLobby.getPrefix() + "§cTournaments are currently disabled!");
        }
    }

    public void joinLMS(Player p) {
        if(created) {
            if(!started) {
                if(!veranyLobby.getLmsPlayers().contains(p)) {
                    if(veranyLobby.getLmsPlayers().size() < maxPlayers) {
                        veranyLobby.getLmsPlayers().add(p);
                        teleport(p, Bukkit.getWorld("arena"));
                        p.getInventory().clear();

                        for (Player all : Bukkit.getOnlinePlayers()) {
                            p.showPlayer(all);
                        }

                        for (Player all : veranyLobby.getLmsPlayers()) {
                            all.sendMessage(veranyLobby.getPrefix() + "§a" + p.getName() + " has joined the tournament! §9[" + veranyLobby.getLmsPlayers().size() + "/" + maxPlayers + "]");
                        }

                    } else {
                        p.sendMessage(veranyLobby.getPrefix() + "§cThe tournament is already full!");
                    }
                } else {
                    p.sendMessage(veranyLobby.getPrefix() + "§cYou are already in the tournament!");
                }
            } else {
                p.sendMessage(veranyLobby.getPrefix() + "§cThe tournament has already started!");
            }
        } else {
            p.sendMessage(veranyLobby.getPrefix() + "§cThere is currently no tournament starting!");
        }
    }

    public void leaveLMS(Player p) {
        if(veranyLobby.getLmsPlayers().contains(p)) {
            if(started) {
                veranyLobby.getLmsPlayers().remove(p);
                p.teleport(locationManager.getLocation("Join Location"));
                if(veranyLobby.getLmsPlayers().size() <= 1) {
                    veranyLobby.getLmsPlayers().remove(p);
                    playerManager.resetPlayer(p);
                    p.teleport(locationManager.getLocation("Join Location"));
                    finishLMS();
                }
            } else {
                veranyLobby.getLmsPlayers().remove(p);
                playerManager.resetPlayer(p);
                p.teleport(locationManager.getLocation("Join Location"));
            }

            for (Player all : veranyLobby.getLmsPlayers()) {
                all.sendMessage(veranyLobby.getPrefix() + "§c" + p.getName() + " has left the tournament! §9[" + veranyLobby.getLmsPlayers().size() + "/" + maxPlayers + "]");
            }

            inventories.setHotbarInventory(p);

        } else {
            p.sendMessage(veranyLobby.getPrefix() + "§cYou are not in a tournament!");
        }
    }

    public void spectateLMS(Player p) {
        if(started) {
            if(!veranyLobby.getLmsPlayers().contains(p)) {
                if(!veranyLobby.getLmsSpectators().contains(p)) {
                    veranyLobby.getLmsSpectators().add(p);

                    for (Player all : Bukkit.getOnlinePlayers()) {
                        p.showPlayer(all);
                    }

                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.hidePlayer(p);
                    }
                    teleport(p, Bukkit.getWorld("arena"));
                    p.sendMessage(veranyLobby.getPrefix() + "§aYou are now spectating the tournament!");
                    p.getInventory().clear();
                    p.setAllowFlight(true);

                    p.getInventory().setItem(8, new ItemManager(Material.REDSTONE).setDisplayName("§cReturn to Lobby").build());
                } else {
                    veranyLobby.getLmsSpectators().remove(p);
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.showPlayer(p);
                    }
                    p.teleport(locationManager.getLocation("Join Location"));
                    p.sendMessage(veranyLobby.getPrefix() + "§cYou are no longer spectating the tournament!");
                    p.setAllowFlight(false);
                    inventories.setHotbarInventory(p);
                }
            } else {
                p.sendMessage(veranyLobby.getPrefix() + "§cYou can't spectate while participating!");
            }
        } else {
            p.sendMessage(veranyLobby.getPrefix() + "§cThere is currently no tournament!");
        }
    }

    private void startLMS() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(veranyLobby, new BukkitRunnable() {
            @Override
            public void run() {
                switch (seconds) {
                    case 120:
                        Bukkit.broadcastMessage(veranyLobby.getPrefix() + "§aThe tournament begins in 2 minutes!");
                        break;
                    case 60:
                        Bukkit.broadcastMessage(veranyLobby.getPrefix() + "§aThe tournament begins in 1 minute!");
                        break;
                    case 30: case 20: case 10: case 5: case 4: case 3: case 2:
                        Bukkit.broadcastMessage(veranyLobby.getPrefix() + "§aThe tournament begins in " + seconds + " seconds!");
                        break;
                    case 1:
                        Bukkit.broadcastMessage(veranyLobby.getPrefix() + "§aThe tournament begins in " + seconds + " second!");
                        break;
                            case 0:
                                if(veranyLobby.getLmsPlayers().size() >= minPlayers) {
                                    Bukkit.broadcastMessage(veranyLobby.getPrefix() + "§a§lThe tournament has started!");
                                    started = true;
                                    for (Player all : veranyLobby.getLmsPlayers()) {
                                        if(veranyLobby.getTournamentKit().get("Kit").equals("UHC")) {
                                            inventories.setUHCInventory(all);
                                        } else if(veranyLobby.getTournamentKit().get("Kit").equals("Soup")) {
                                            inventories.setSoupInventory(all);
                                        }
                                    }
                                } else {
                                    Bukkit.getScheduler().cancelTask(taskID);

                                    for (Player all : veranyLobby.getLmsPlayers()) {
                                        all.teleport(locationManager.getLocation("Join Location"));
                                        inventories.setHotbarInventory(all);
                                    }

                                    veranyLobby.getLmsPlayers().clear();

                                    created = false;
                                    started = false;

                                    Bukkit.broadcastMessage(veranyLobby.getPrefix() + "§cThere must be at least " + minPlayers + " players to start!");
                                    Bukkit.broadcastMessage(veranyLobby.getPrefix() + "§cThe tournament has been cancelled!");
                                    seconds = 120;
                        }
                        break;

                    default:
                        break;
                }
                seconds--;
            }
        }, 0, 20);
    }

    private void teleport(Player p, World world) {
        Random random = new Random();

        int x = random.nextInt(35);
        int z = random.nextInt(35);
        int y = world.getHighestBlockYAt(x, z);

        Location location = new Location(world, x, y + 5, z);

        p.teleport(location);
    }

    private void finishLMS() {
        Bukkit.getScheduler().cancelTask(taskID);

        created = false;
        started = false;

        for (Player all : veranyLobby.getLmsPlayers()) {
            all.teleport(locationManager.getLocation("Join Location"));
            inventories.setHotbarInventory(all);
        }

        for (Player all : veranyLobby.getLmsPlayers()) {
            Bukkit.broadcastMessage(veranyLobby.getPrefix() + "§a§l" + all.getName() + " has won the tournament!");
            playerManager.resetPlayer(all);
            inventories.setHotbarInventory(all);
        }

        for (Player all : veranyLobby.getLmsSpectators()) {
            all.setAllowFlight(false);
            all.teleport(locationManager.getLocation("Join Location"));
            inventories.setHotbarInventory(all);
        }

        veranyLobby.getLmsSpectators().clear();
        veranyLobby.getLmsPlayers().clear();
        clearDrops();
        veranyLobby.getTournamentKit().put("Kit", "UHC");
    }

    private void clearDrops() {
        World world = Bukkit.getWorld("arena");
        List<Entity> mapList = world.getEntities();
        for (Entity current : mapList) {
            if (current.getType() == EntityType.DROPPED_ITEM) {
                current.remove();
            }
        }
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isCreated() {
        return created;
    }
}
