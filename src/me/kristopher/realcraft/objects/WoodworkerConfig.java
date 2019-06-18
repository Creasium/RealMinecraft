package me.kristopher.realcraft.objects;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.Lists;

import me.kristopher.realcraft.util.ItemUtil;

//Creating new item
//Woodworker's hammer
public class WoodworkerConfig {
	private ConfigManager cfg;
	private String name;
	private List<String> lore;
	private String type;
	private ItemStack stack;
	
	public WoodworkerConfig(Plugin plugin) {
		cfg = new ConfigManager(plugin, "WoodworkerConfig");
		cfg.create();
		load();
	}

	private void load() {
		cfg.getConfiguration().addDefault("name", "&bWoodworker's hammer");
		cfg.getConfiguration().addDefault("lore", Lists.newArrayList("You can use this", "tool to build", "wood constructions.", "You have to hold", "hammer in left hand."));
		cfg.getConfiguration().addDefault("type", "GOLDEN_AXE");
		cfg.getConfiguration().options().copyDefaults(true);
		cfg.save();

		name = cfg.getConfiguration().getString("name");
		lore = cfg.getConfiguration().getStringList("lore");
		type = cfg.getConfiguration().getString("type");
		stack = ItemUtil.create(Material.valueOf(type)).setName(name).setLore(lore).getStack();
	}
	public String getName() {
		return name;
	}
	public List<String> getLore() {
		return lore;
	}
	public String getType() {
		return type;
	}
	public ItemStack getItem() {
		return stack;
	}
}