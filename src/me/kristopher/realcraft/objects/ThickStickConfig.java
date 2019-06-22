package me.kristopher.realcraft.objects;

import com.google.common.collect.Lists;
import me.kristopher.realcraft.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class ThickStickConfig {
		private ConfigManager cfg;
		private String name;
		private List<String> lore;
		private String type;
		private ItemStack stack;

		public ThickStickConfig(Plugin plugin) {
			cfg = new ConfigManager(plugin, "ThickStickConfig");
			cfg.create();
			load();
		}

		private void load() {
			cfg.getConfiguration().addDefault("name", "&bThick stick");
			cfg.getConfiguration().addDefault("lore", Lists.newArrayList("You can use", "this tool to", "chop some tree."));
			cfg.getConfiguration().addDefault("type", "STICK");
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