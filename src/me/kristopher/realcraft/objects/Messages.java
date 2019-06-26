package me.kristopher.realcraft.objects;

import org.bukkit.plugin.Plugin;

//Default messages for plugin
public class Messages {
	
	private ConfigManager cfg;
	private String cantBreakBlock;
	private String cantPlaceWithoutHammer;
	private String cantHitWithoutSword;
	private String freezing;
	private String tooHigh;

	public Messages(Plugin plugin) {
		cfg = new ConfigManager(plugin, "message");
		cfg.create();
		load();
	}
	
	private void load() {
		cfg.getConfiguration().addDefault("cantBreakBlock", "&cYou can't break this block by using this tool.");
		cfg.getConfiguration().addDefault("cantPlaceWithoutHammer", "&cYou can't place this block without woodworker`s hammer.");
		cfg.getConfiguration().addDefault("cantHitWithoutSword", "&cYou can't fight without special tool.");
		cfg.getConfiguration().addDefault("Freezing", "&bYou are freezing!");
		cfg.getConfiguration().addDefault("tooHigh", "&cYou are too high. You need breathing effect.");
		cfg.getConfiguration().options().copyDefaults(true);
		cfg.save();
		cantBreakBlock = cfg.getConfiguration().getString("cantBreakBlock", "&cYou can't break this block by using this tool.");
		cantPlaceWithoutHammer = cfg.getConfiguration().getString("cantPlaceWithoutHammer", "&cYou can't place this block without woodworker`s hammer.");
		cantHitWithoutSword = cfg.getConfiguration().getString("cantHitWithoutSword", "&cYou can't fight without special tools.");
		freezing = cfg.getConfiguration().getString("Freezing", "&bYou are freezing!");
		tooHigh = cfg.getConfiguration().getString("getTooHigh", "&cYou are too high. You need breathing effect.");
	}
	
	public String getCantBreakBlock() {
		return cantBreakBlock;
	}

	public String getCantPlaceWithoutHammer() {
		return cantPlaceWithoutHammer;
	}

	public String getCantHitWithoutSword() {
		return cantHitWithoutSword;
	}

	public String getFreezing() {
		return freezing;
	}

	public String getTooHigh() {
		return tooHigh;
	}
}