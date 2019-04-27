package net.saikatsune.veranylobby.commands;

import net.saikatsune.veranylobby.data.VeranyLobby;
import net.saikatsune.veranylobby.manager.LocationManager;
import net.saikatsune.veranylobby.utilities.Inventories;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SetupCommand implements CommandExecutor, Listener {

    private VeranyLobby veranyLobby = VeranyLobby.getInstance();
    private Inventories inventories = veranyLobby.getInventories();
    private LocationManager locationManager = veranyLobby.getLocationManager();

    private String main = veranyLobby.getMainColorColor();
    private String secondary = veranyLobby.getSecondaryColor();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("setup")) {
            if(sender instanceof Player) {
                Player p = (Player) sender;
                if( p.hasPermission("verany.*")) {
                    inventories.setSetupInventory(p);
                }
            }
        }
        return false;
    }

    @EventHandler
    public void on(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getClickedInventory() != null) {
            if(e.getCurrentItem() != null) {
                if(e.getCurrentItem().getType().equals(Material.ANVIL)) {
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(veranyLobby.getPrefix() + "§fJoin Location")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        locationManager.setLocation("Join Location", p.getLocation());
                        p.sendMessage(veranyLobby.getPrefix() + secondary + "You successfully " + main + "set " + secondary + "the " + main + "Join Location" + secondary + "!");
                    }
                } else if(e.getCurrentItem().getType().equals(Material.INK_SACK)) {
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(veranyLobby.getPrefix() + "§ccoming soon")) {
                        e.setCancelled(true);
                    }
                } else if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) {
                    e.setCancelled(true);
                }
            }
        }
    }
}