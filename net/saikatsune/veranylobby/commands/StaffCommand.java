package net.saikatsune.veranylobby.commands;

import net.saikatsune.veranylobby.data.VeranyLobby;
import net.saikatsune.veranylobby.utilities.Inventories;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class StaffCommand implements CommandExecutor, Listener {

    private VeranyLobby veranyLobby = VeranyLobby.getInstance();
    private Inventories inventories = veranyLobby.getInventories();

    private String main = veranyLobby.getMainColorColor();
    private String secondary = veranyLobby.getSecondaryColor();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("staff")) {
            if(sender instanceof Player) {
                Player p = (Player) sender;
                if(p.hasPermission("verany.staff") || p.hasPermission("verany.*")) {
                    inventories.setStaffInventory(p);
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
                if(e.getCurrentItem().getType().equals(Material.STICK)) {
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(veranyLobby.getPrefix() + secondary + "Editor Mode §7(§a✔§7)")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        veranyLobby.getEditorMode().remove(p);
                        p.setGameMode(GameMode.SURVIVAL);
                        p.sendMessage(veranyLobby.getPrefix() + secondary + "You're no longer in " + main + "Editor Mode" + secondary + "!");
                    } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(veranyLobby.getPrefix() + secondary + "Editor Mode §7(§c✘§7)")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        veranyLobby.getEditorMode().add(p);
                        p.setGameMode(GameMode.CREATIVE);
                        p.sendMessage(veranyLobby.getPrefix() + secondary + "You're now in " + main + "Editor Mode" + secondary + "!");
                    }
                } else if(e.getCurrentItem().getType().equals(Material.PAPER)) {
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(veranyLobby.getPrefix() + secondary + "Silence Server §7(§a✔§7)")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        veranyLobby.setSilenced(false);

                        for (int i = 0; i < 100; i++) {
                            Bukkit.broadcastMessage("");
                        }
                        Bukkit.broadcastMessage(veranyLobby.getPrefix() + "§aThe server is no longer silenced!");
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(veranyLobby.getPrefix() + secondary + "Silence Server §7(§c✘§7)")) {
                        e.setCancelled(true);
                        p.closeInventory();
                        veranyLobby.setSilenced(true);

                        for (int i = 0; i < 100; i++) {
                            Bukkit.broadcastMessage("");
                        }
                        Bukkit.broadcastMessage(veranyLobby.getPrefix() + "§aThe server is now silenced!");
                    }
                }
            }
        }
    }
}
