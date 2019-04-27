package net.saikatsune.veranylobby.listener;

import net.saikatsune.veranylobby.data.VeranyLobby;
import net.saikatsune.veranylobby.lms.manager.LMSManager;
import net.saikatsune.veranylobby.manager.ConnectionManager;
import net.saikatsune.veranylobby.utilities.Inventories;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    private VeranyLobby veranyLobby = VeranyLobby.getInstance();

    private ConnectionManager connectionManager = veranyLobby.getConnectionManager();
    private LMSManager lmsManager = veranyLobby.getLmsManager();

    private Inventories inventories = veranyLobby.getInventories();

    private String main = veranyLobby.getMainColorColor();

    @EventHandler
    public void on(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if(e.getClickedInventory() != null) {
            if(e.getCurrentItem() != null) {
                if(e.getCurrentItem().getItemMeta() != null) {
                    if(e.getCurrentItem().getItemMeta().getDisplayName() != null) {
                        if(e.getCurrentItem().getType().equals(Material.COMPASS)) {
                            if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(main + "§lNavigator")) {
                                e.setCancelled(true);
                            }
                        } else if(e.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
                            if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(main + "§lProfile")) {
                                e.setCancelled(true);
                            }
                        } else if(e.getCurrentItem().getType().equals(Material.INK_SACK)) {
                            if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(main + "Hide Players §7> §cFALSE")) {
                                p.playSound(p.getLocation(), Sound.LEVEL_UP, 2F, 3F);
                                e.setCancelled(true);
                                p.closeInventory();
                                veranyLobby.getHidePlayers().add(p);
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    p.hidePlayer(all);
                                }
                                p.sendMessage(veranyLobby.getPrefix() + "§aAll players are now hidden!");
                            } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(main + "Hide Players §7> §aTRUE")) {
                                p.playSound(p.getLocation(), Sound.LEVEL_UP, 2F, 3F);
                                e.setCancelled(true);
                                p.closeInventory();
                                veranyLobby.getHidePlayers().remove(p);
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    p.showPlayer(all);
                                }
                                p.sendMessage(veranyLobby.getPrefix() + "§aAll players are no longer hidden!");
                            }
                        } else if(e.getCurrentItem().getType().equals(Material.NETHER_STAR)) {
                            if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(main + "Profile: " + veranyLobby.getSecondaryColor() + p.getName())) {
                                e.setCancelled(true);
                            }
                        } else if(e.getCurrentItem().getType().equals(Material.WATCH)) {
                            if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(main + "§lTournament")) {
                                e.setCancelled(true);
                            }
                        } else if(e.getCurrentItem().getType().equals(Material.REDSTONE)) {
                            if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cReturn to Lobby")) {
                                e.setCancelled(true);
                            }
                        } else if(e.getCurrentItem().getType().equals(Material.GOLDEN_APPLE)) {
                            if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(main + "UHC")) {
                                e.setCancelled(true);
                                veranyLobby.getTournamentKit().put("Kit", "UHC");
                                lmsManager.createLMS(p);
                            } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(main + "Ultra Hardcore")) {
                                e.setCancelled(true);
                            }
                        } else if(e.getCurrentItem().getType().equals(Material.MUSHROOM_SOUP)) {
                            if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(main + "Soup")) {
                                e.setCancelled(true);
                                veranyLobby.getTournamentKit().put("Kit", "Soup");
                                lmsManager.createLMS(p);
                            }
                        } else if(e.getCurrentItem().getType().equals(Material.DIAMOND_SWORD)) {
                            if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(main + "Practice")) {
                                e.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }
}
