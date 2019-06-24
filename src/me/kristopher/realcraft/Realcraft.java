package me.kristopher.realcraft;

import me.kristopher.realcraft.BukkitRun.BukkitTicks;
import me.kristopher.realcraft.objects.ThickStickConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import me.kristopher.realcraft.listener.AttackListener;
import me.kristopher.realcraft.listener.BlockListener;
import me.kristopher.realcraft.listener.InventoryListener;
//import me.kristopher.realcraft.listener.PlayerListener;
import me.kristopher.realcraft.objects.Messages;
import me.kristopher.realcraft.objects.Recipe;
import me.kristopher.realcraft.objects.WoodworkerConfig;
import me.kristopher.realcraft.BukkitRun.BukkitTicks;

public class Realcraft extends JavaPlugin{

	private Messages msgs;
	private WoodworkerConfig woodCfg;
	private ThickStickConfig stickCfg;

	@Override
	public void onEnable() {
		new BukkitTicks().runTaskTimer(this, 0, 20);
		msgs = new Messages(this);
		woodCfg = new WoodworkerConfig(this);
		stickCfg = new ThickStickConfig(this);
		Recipe recipe = new Recipe(this);
		recipe.registerRecipes();
		Bukkit.getPluginManager().registerEvents(new AttackListener(this), this);
		Bukkit.getPluginManager().registerEvents(new BlockListener(this), this);
		Bukkit.getPluginManager().registerEvents(new InventoryListener(this), this);
		//Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
		getLogger().info(ChatColor.GOLD + "-x+x-");
		getLogger().info(ChatColor.GOLD + "realcraft Plguin by _Kristopher_");
		getLogger().info(ChatColor.GOLD + "realcraft Plguin enabled");
		getLogger().info(ChatColor.GOLD + "-x+x-");
	}

	@Override
	public void onDisable() {
		getLogger().info(ChatColor.GOLD + "-x+x-");
		getLogger().info(ChatColor.GOLD + "realcraft Plguin by _Kristopher_");
		getLogger().info(ChatColor.GOLD + "realcraft Plguin disabled");
		getLogger().info(ChatColor.GOLD + "-x+x-");
	}
	
	public Messages getMsgs() {
		return msgs;
	}
	public WoodworkerConfig getWoodCfg() {
		return woodCfg;
	}
	public ThickStickConfig getStickCfg() {
		return stickCfg;
	}
}