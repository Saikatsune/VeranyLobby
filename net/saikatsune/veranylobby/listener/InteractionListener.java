package net.saikatsune.veranylobby.listener;

import net.saikatsune.veranylobby.data.VeranyLobby;
import net.saikatsune.veranylobby.lms.manager.LMSManager;
import net.saikatsune.veranylobby.utilities.Inventories;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractionListener implements Listener {

    private VeranyLobby veranyLobby = VeranyLobby.getInstance();

    private LMSManager lmsManager = veranyLobby.getLmsManager();

    private Inventories inventories = veranyLobby.getInventories();

    private String main = veranyLobby.getMainColorColor();

    @EventHandler
    public void on(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if(p.getItemInHand().getType().equals(Material.COMPASS)) {
                if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(main + "§lNavigator")) {
                    inventories.setNavigatorInventory(p);
                }
            } else if(p.getItemInHand().getType().equals(Material.SKULL_ITEM)) {
                if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(main + "§lProfile")) {
                    inventories.setProfileInventory(p);
                }
            } else if(p.getItemInHand().getType().equals(Material.WATCH)) {
                if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(main + "§lTournament")) {
                    inventories.setLMSInventory(p);
                }
            } else if(p.getItemInHand().getType().equals(Material.REDSTONE)) {
                if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§cReturn to Lobby")) {
                    lmsManager.spectateLMS(p);
                }
            } else if(p.getItemInHand().getType().equals(Material.MUSHROOM_SOUP)) {
                int healing = veranyLobby.getSoupHealing();
                if(veranyLobby.getTournamentKit().get("Kit").equals("Soup")) {
                    if(p.getHealth() != p.getMaxHealth()) {
                        p.setHealth(p.getHealth() + healing > p.getMaxHealth() ? p.getMaxHealth() : p.getHealth() + healing);
                        p.getItemInHand().setType(Material.BOWL);
                    }
                } else {
                    p.sendMessage(veranyLobby.getPrefix() + "§cSoup healing is currently disabled!");
                }
            }
        }
    }
}
