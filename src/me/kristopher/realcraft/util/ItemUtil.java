package me.kristopher.realcraft.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemUtil {
	private ItemStack stack;

    private ItemUtil(Material mat) {
        this.stack = new ItemStack(mat);
    }

    private ItemUtil(ItemStack stack) {
        this.stack = stack;
    }

    public static ItemUtil create(Material mat) {
        return new ItemUtil(mat);
    }

    public static ItemUtil create(ItemStack stack) {
        ItemUtil itemUtil = create();
        itemUtil.stack = stack;
        return itemUtil;
    }

    public static ItemUtil create() {
        return new ItemUtil(Material.BEDROCK);
    }

    public static ItemUtil set(ItemStack stack) {
        return new ItemUtil(stack);
    }

    public ItemUtil setName(String name) {
        ItemMeta meta = this.getMeta();
        meta.setDisplayName(this.i(name));
        this.stack.setItemMeta(meta);
        return this;
    }

    public ItemUtil setLore(List<String> lore) {
        ArrayList realLore = new ArrayList();
        lore.forEach((s) -> {
            String copyS = this.i(s);
            realLore.add(copyS);
        });
        lore.clear();
        ItemMeta meta = this.getMeta();
        meta.setLore(realLore);
        this.stack.setItemMeta(meta);
        return this;
    }

    public ItemUtil addLore(String... lore) {
        ArrayList realLore = new ArrayList();
        String[] meta = lore;
        int var4 = lore.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String s = meta[var5];
            s = this.i(s);
            realLore.add(s);
        }

        ItemMeta var7 = this.getMeta();
        var7.setLore(realLore);
        this.stack.setItemMeta(var7);
        return this;
    }

    public ItemUtil addEnchant(Enchantment ench, int level) {
        ItemMeta meta = this.getMeta();
        meta.addEnchant(ench, level, true);
        this.stack.setItemMeta(meta);
        return this;
    }

    public ItemUtil removeEnchant(Enchantment ench) {
        ItemMeta meta = this.getMeta();
        meta.removeEnchant(ench);
        this.stack.setItemMeta(meta);
        return this;
    }

    public ItemUtil addFlags(String... flag) {
        ItemMeta meta = this.getMeta();
        String[] var3 = flag;
        int var4 = flag.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String f = var3[var5];
            meta.addItemFlags(new ItemFlag[]{ItemFlag.valueOf(f)});
        }

        this.stack.setItemMeta(meta);
        return this;
    }

    public ItemUtil clearEnchant() {
        ItemMeta meta = this.getMeta();
        Iterator var2 = meta.getEnchants().keySet().iterator();

        while(var2.hasNext()) {
            Enchantment ench = (Enchantment)var2.next();
            meta.removeEnchant(ench);
        }

        this.stack.setItemMeta(meta);
        return this;
    }

    public ItemUtil setCount(int count) {
        this.stack.setAmount(count);
        return this;
    }

    public ItemUtil setDurabillity(int durabillity) {
        this.stack.setDurability((short)durabillity);
        return this;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    private String i(String i) {
        return ChatColor.translateAlternateColorCodes('&', i);
    }

    private ItemMeta getMeta() {
        return this.stack.getItemMeta();
    }
}
