package net.saikatsune.veranylobby.manager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemManager {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemManager(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemManager(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemManager setDisplayName(String name) {
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        return this;
    }

    public ItemManager setMetaID(byte metaID) {
        itemStack.getData().setData(metaID);
        return this;
    }

    public ItemManager setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemManager setDurability(short durability) {
        this.itemStack.setDurability(durability);
        return this;
    }

    public ItemManager addEnchantment(Enchantment enchantment, int lvl) {
        this.itemMeta.addEnchant(enchantment, lvl, false);
        return this;
    }

    public ItemManager clearEnchantments() {
        this.itemMeta.getEnchants().forEach((enchantment, integer) -> this.itemMeta.removeEnchant(enchantment));
        return this;
    }

    public ItemManager removeEnchantment(Enchantment enchantment) {
        if (this.itemMeta.getEnchants().containsKey(enchantment))
            this.itemMeta.removeEnchant(enchantment);
        return this;
    }

    public ItemManager setLore(List<String> lines) {
        this.itemMeta.setLore(lines);
        return this;
    }

    public ItemManager setLore(String... lines) {
        this.itemMeta.setLore(Arrays.asList(lines));
        return this;
    }

    public ItemManager resetLore() {
        this.itemMeta.getLore().clear();
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
