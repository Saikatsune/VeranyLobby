package net.saikatsune.veranylobby.lms.commands;

import net.saikatsune.veranylobby.data.VeranyLobby;
import net.saikatsune.veranylobby.lms.manager.LMSManager;
import net.saikatsune.veranylobby.utilities.Inventories;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class LMSCommand implements CommandExecutor, Listener {

    private VeranyLobby veranyLobby = VeranyLobby.getInstance();

    private LMSManager lmsManager = veranyLobby.getLmsManager();

    private Inventories inventories = veranyLobby.getInventories();

    private String main = veranyLobby.getMainColorColor();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("lms")) {
            if(sender instanceof Player) {
                Player p = (Player) sender;
                inventories.setLMSInventory(p);
            }
        }
        return false;
    }

    @EventHandler
    public void on(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getClickedInventory() != null) {
            if(e.getCurrentItem() != null) {
                if(e.getCurrentItem().getType().equals(Material.REDSTONE_TORCH_ON)) {
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(main + "Create LMS")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        if(p.hasPermission("verany.media") || p.hasPermission("verany.staff") || p.hasPermission("verany.trial") || p.hasPermission("verany.*") || p.hasPermission("verany.donator")) {
                            if(!lmsManager.isCreated()) {
                                inventories.setKitSelectorInventory(p);
                            } else {
                                p.sendMessage(veranyLobby.getPrefix() + "§cThere is already a tournament starting!");
                            }
                        } else {
                            p.sendMessage(veranyLobby.getPrefix() + "§cYou have to be §6DONATOR §cor higher to create tournaments!");
                        }
                    }
                } else if(e.getCurrentItem().getType().equals(Material.WOOL)) {
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(main + "Join LMS")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        lmsManager.joinLMS(p);
                    } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(main + "Leave LMS")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        lmsManager.leaveLMS(p);
                    }
                } else if(e.getCurrentItem().getType().equals(Material.ITEM_FRAME)) {
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(main + "Spectate LMS")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        lmsManager.spectateLMS(p);
                    }
                }
            }
        }
    }
}
