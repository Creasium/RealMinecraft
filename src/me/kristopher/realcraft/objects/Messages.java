package me.kristopher.realcraft.objects;

import org.bukkit.plugin.Plugin;

//Messages for plugin
public class Messages {
	
	private ConfigManager cfg;
	private String cantBreakBlock;
	private String cantPlaceWithoutHammer;

	public Messages(Plugin plugin) {
		cfg = new ConfigManager(plugin, "message");
		cfg.create();
		load();
	}
	
	private void load() {
		cfg.getConfiguration().addDefault("cantBreakBlock", "&cYou can't break this block by using this tool.");
		cfg.getConfiguration().addDefault("cantPlaceWithoutHammer", "&cYou can't place this block without woodworker`s hammer.");
		cfg.getConfiguration().options().copyDefaults(true);
		cfg.save();
		cantBreakBlock = cfg.getConfiguration().getString("cantBreakBlock", "&cYou can't break this block by using this tool.");
		cantPlaceWithoutHammer = cfg.getConfiguration().getString("cantPlaceWithoutHammer", "&cYou can't place this block without woodworker`s hammer.");
	}
	
	public String getCantBreakBlock() {
		return cantBreakBlock;
	}

	public String getCantPlaceWithoutHammer() {
		return cantPlaceWithoutHammer;
	}

}