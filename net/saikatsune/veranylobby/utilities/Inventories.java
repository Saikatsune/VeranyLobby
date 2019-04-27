package net.saikatsune.veranylobby.utilities;

import net.saikatsune.veranylobby.data.VeranyLobby;
import net.saikatsune.veranylobby.manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class Inventories {

    private VeranyLobby veranyLobby = VeranyLobby.getInstance();

    private ItemStack clearGlassStack = new ItemManager(Material.STAINED_GLASS_PANE).build();

    private String main = veranyLobby.getMainColorColor();
    private String secondary = veranyLobby.getSecondaryColor();

    public void setSetupInventory(Player p) {
        Inventory inventory = Bukkit.createInventory(null, 9*1, veranyLobby.getPrefix() + secondary + "Setup");

        ItemStack comingSoonStack = new ItemStack(Material.INK_SACK, 1, (byte) 1);
        ItemMeta comingSoonMeta = comingSoonStack.getItemMeta();
        comingSoonMeta.setDisplayName(veranyLobby.getPrefix() + "§cComing Soon");
        comingSoonStack.setItemMeta(comingSoonMeta);

        inventory.setItem(4, new ItemManager(Material.ANVIL).setDisplayName(veranyLobby.getPrefix() + secondary + "Join Location").
                setLore(Arrays.asList("§7§m----------------", secondary + "Click to set the", secondary + "Join Location", secondary + "for all players!", "§7§m----------------")).build());

        inventory.setItem(2, comingSoonStack);
        inventory.setItem(6, comingSoonStack);

        inventory.setItem(0, clearGlassStack);
        inventory.setItem(1, clearGlassStack);
        inventory.setItem(3, clearGlassStack);
        inventory.setItem(5, clearGlassStack);
        inventory.setItem(7, clearGlassStack);
        inventory.setItem(8, clearGlassStack);

        p.openInventory(inventory);
    }

    public void setStaffInventory(Player p) {
        Inventory inventory = Bukkit.createInventory(null, 9*1, veranyLobby.getPrefix() + secondary + "Staff");

        ItemStack comingSoonStack = new ItemStack(Material.INK_SACK, 1, (byte) 1);
        ItemMeta comingSoonMeta = comingSoonStack.getItemMeta();
        comingSoonMeta.setDisplayName(veranyLobby.getPrefix() + "§cComing Soon");
        comingSoonStack.setItemMeta(comingSoonMeta);

        if(veranyLobby.isSilenced()) {
            inventory.setItem(2, new ItemManager(Material.PAPER).setDisplayName(veranyLobby.getPrefix() + secondary + "Silence Server §7(§a✔§7)").build());
        } else {
            inventory.setItem(2, new ItemManager(Material.PAPER).setDisplayName(veranyLobby.getPrefix() + secondary + "Silence Server §7(§c✘§7)").build());
        }

        inventory.setItem(4, comingSoonStack);

        if(veranyLobby.getEditorMode().contains(p)) {
            inventory.setItem(6, new ItemManager(Material.STICK).setDisplayName(veranyLobby.getPrefix() + secondary + "Editor Mode §7(§a✔§7)").build());
        } else {
            inventory.setItem(6, new ItemManager(Material.STICK).setDisplayName(veranyLobby.getPrefix() + secondary + "Editor Mode §7(§c✘§7)").build());
        }

        inventory.setItem(0, clearGlassStack);
        inventory.setItem(1, clearGlassStack);
        inventory.setItem(3, clearGlassStack);
        inventory.setItem(5, clearGlassStack);
        inventory.setItem(7, clearGlassStack);
        inventory.setItem(8, clearGlassStack);

        p.openInventory(inventory);
    }

    public void setHotbarInventory(Player p) {
        p.getInventory().clear();

        ItemStack ProfileStack = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta) ProfileStack.getItemMeta();
        skullMeta.setDisplayName(main + "§lProfile");
        skullMeta.setOwner(p.getName());

        ProfileStack.setItemMeta(skullMeta);

        p.getInventory().setItem(0, new ItemManager(Material.COMPASS).setDisplayName(main + "§lNavigator").build());
        /*
        p.getInventory().setItem(4, new ItemManager(Material.WATCH).setDisplayName(main + "§lTournament").build());
         */
        p.getInventory().setItem(8, ProfileStack);
    }

    public void setNavigatorInventory(Player p) {
        Inventory inventory = Bukkit.createInventory(null, 9*1, veranyLobby.getPrefix() + secondary + "Navigator");

        ItemStack comingSoonStack = new ItemStack(Material.INK_SACK, 1, (byte) 1);
        ItemMeta comingSoonMeta = comingSoonStack.getItemMeta();
        comingSoonMeta.setDisplayName(veranyLobby.getPrefix() + "§cComing Soon");
        comingSoonStack.setItemMeta(comingSoonMeta);

        inventory.setItem(2, new ItemManager(Material.GOLDEN_APPLE).setDisplayName(main + "Ultra Hardcore").build());
        inventory.setItem(4, comingSoonStack);
        inventory.setItem(6, new ItemManager(Material.DIAMOND_SWORD).setDisplayName(main + "Practice").build());

        inventory.setItem(0, clearGlassStack);
        inventory.setItem(1, clearGlassStack);
        inventory.setItem(3, clearGlassStack);
        inventory.setItem(5, clearGlassStack);
        inventory.setItem(7, clearGlassStack);
        inventory.setItem(8, clearGlassStack);

        p.openInventory(inventory);
    }

    public void setProfileInventory(Player p) {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.BREWING, veranyLobby.getPrefix() + secondary + "Profile");

        ItemStack hidePlayersTrueStack = new ItemStack(Material.INK_SACK, 1, (byte) 10);
        ItemMeta hidePlayersTrueMeta = hidePlayersTrueStack.getItemMeta();
        hidePlayersTrueMeta.setDisplayName(main + "Hide Players §7> §aTRUE");
        hidePlayersTrueStack.setItemMeta(hidePlayersTrueMeta);

        ItemStack hidePlayersFalseStack = new ItemStack(Material.INK_SACK, 1, (byte) 1);
        ItemMeta hidePlayersFalseMeta = hidePlayersFalseStack.getItemMeta();
        hidePlayersFalseMeta.setDisplayName(main + "Hide Players §7> §cFALSE");
        hidePlayersFalseStack.setItemMeta(hidePlayersFalseMeta);

        if(!veranyLobby.getHidePlayers().contains(p)) {
            inventory.setItem(1, hidePlayersFalseStack);
        } else {
            inventory.setItem(1, hidePlayersTrueStack);
        }

        inventory.setItem(3, new ItemManager(Material.NETHER_STAR).setDisplayName(main + "Profile: " + secondary + p.getName()).build());

        p.openInventory(inventory);
    }

    public void setLMSInventory(Player p ) {
        Inventory inventory = Bukkit.createInventory(null, 9, veranyLobby.getPrefix() + secondary + "LMS");

        ItemStack joinStack = new ItemStack(Material.WOOL, 1, (byte) 13);
        ItemMeta joinMeta = joinStack.getItemMeta();
        joinMeta.setDisplayName(main + "Join LMS");
        joinStack.setItemMeta(joinMeta);

        ItemStack leaveStack = new ItemStack(Material.WOOL, 1, (byte) 14);
        ItemMeta leaveMeta = leaveStack.getItemMeta();
        leaveMeta.setDisplayName(main + "Leave LMS");
        leaveStack.setItemMeta(leaveMeta);

        inventory.setItem(1, new ItemManager(Material.REDSTONE_TORCH_ON).setDisplayName(main + "Create LMS").build());
        inventory.setItem(3, joinStack);
        inventory.setItem(5, leaveStack);
        inventory.setItem(7, new ItemManager(Material.ITEM_FRAME).setDisplayName(main + "Spectate LMS").build());

        inventory.setItem(0, clearGlassStack);
        inventory.setItem(2, clearGlassStack);
        inventory.setItem(4, clearGlassStack);
        inventory.setItem(6, clearGlassStack);
        inventory.setItem(8, clearGlassStack);

        p.openInventory(inventory);
    }

    public void setUHCInventory(Player p) {
        PlayerInventory inventory = p.getInventory();

        inventory.setHelmet(new ItemManager(Material.DIAMOND_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
        inventory.setChestplate(new ItemManager(Material.DIAMOND_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
        inventory.setLeggings(new ItemManager(Material.DIAMOND_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
        inventory.setBoots(new ItemManager(Material.DIAMOND_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());

        inventory.setItem(0, new ItemManager(Material.DIAMOND_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 2).build());
        inventory.setItem(1, new ItemStack(Material.FISHING_ROD));
        inventory.setItem(2, new ItemManager(Material.BOW).addEnchantment(Enchantment.ARROW_DAMAGE, 1).build());
        inventory.setItem(3, new ItemStack(Material.GOLDEN_APPLE, 5));
        inventory.setItem(8, new ItemStack(Material.ARROW, 32));
    }

    public void setSoupInventory(Player p) {
        PlayerInventory inventory = p.getInventory();

        inventory.setHelmet(new ItemManager(Material.IRON_HELMET).build());
        inventory.setChestplate(new ItemManager(Material.IRON_CHESTPLATE).build());
        inventory.setLeggings(new ItemManager(Material.IRON_LEGGINGS).build());
        inventory.setBoots(new ItemManager(Material.IRON_BOOTS).build());

        inventory.setItem(0, new ItemManager(Material.IRON_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 1).build());

        inventory.setItem(13, new ItemStack(Material.RED_MUSHROOM, 32));
        inventory.setItem(14, new ItemStack(Material.BROWN_MUSHROOM, 32));
        inventory.setItem(15, new ItemStack(Material.BOWL, 32));

        for (int i = 0; i < 32; i++) {
            inventory.addItem(new ItemStack(Material.MUSHROOM_SOUP, 1));
        }

    }

    public void setKitSelectorInventory(Player p) {
        Inventory inventory = Bukkit.createInventory(null, 9*1, "§a§l§nSelect a Kit!");

        inventory.setItem(2, new ItemManager(Material.GOLDEN_APPLE).setDisplayName(main + "UHC").build());
        inventory.setItem(6, new ItemManager(Material.MUSHROOM_SOUP).setDisplayName(main + "Soup").build());

        p.openInventory(inventory);
    }

}
