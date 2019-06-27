package me.kristopher.realcraft.objects;

import org.bukkit.plugin.Plugin;

public class ThirstData {

	private ConfigManager cfg;

	public ThirstData(Plugin plugin) {
		cfg = new ConfigManager(plugin, "ThirstData");
		cfg.create();
		load();
	}

	private void load() {
		cfg.getConfiguration().options().copyDefaults(true);
		cfg.save();
	}
}
