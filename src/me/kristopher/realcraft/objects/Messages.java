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
	private String lowThirst;
	private String thirstIs;

	public Messages(Plugin plugin) {
		cfg = new ConfigManager(plugin, "Messages");
		cfg.create();
		load();
	}
	
	private void load() {
		cfg.getConfiguration().addDefault("cantBreakBlock", "&cYou can't break this block by using this tool.");
		cfg.getConfiguration().addDefault("cantPlaceWithoutHammer", "&cYou can't place this block without woodworker`s hammer.");
		cfg.getConfiguration().addDefault("cantHitWithoutSword", "&cYou can't fight without special tool.");
		cfg.getConfiguration().addDefault("Freezing", "&bYou are freezing!");
		cfg.getConfiguration().addDefault("tooHigh", "&cYou are too high. You need breathing effect.");
		cfg.getConfiguration().addDefault("lowThirst", "&cYou have to drink!");
		cfg.getConfiguration().addDefault("ThirstIs", "&cYour thirst is: ");
		cfg.getConfiguration().options().copyDefaults(true);
		cfg.save();
		cantBreakBlock = cfg.getConfiguration().getString("cantBreakBlock", "&cYou can't break this block by using this tool.");
		cantPlaceWithoutHammer = cfg.getConfiguration().getString("cantPlaceWithoutHammer", "&cYou can't place this block without woodworker`s hammer.");
		cantHitWithoutSword = cfg.getConfiguration().getString("cantHitWithoutSword", "&cYou can't fight without special tools.");
		freezing = cfg.getConfiguration().getString("Freezing", "&bYou are freezing!");
		tooHigh = cfg.getConfiguration().getString("getTooHigh", "&5You are too high. You need breathing effect.");
		lowThirst = cfg.getConfiguration().getString("lowThirst", "&6You have to drink!");
		thirstIs = cfg.getConfiguration().getString("thirstIs", "&6Your thirst is: ");
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

	public String getLowThirst() {
		return lowThirst;
	}

	public String getThirstIs() {
		return thirstIs;
	}
}