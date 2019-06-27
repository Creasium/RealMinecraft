package me.kristopher.realcraft.BukkitRun;

import me.kristopher.realcraft.Realcraft;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Thirst extends BukkitRunnable{
	private Realcraft plugin;

	public Thirst(Realcraft plugin) {
		this.plugin = plugin;
	}

	public Thirst() {
	}

	@Override
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {

			plugin.getConfig().set("Users." + p.getName() + ".", "")
			//todo

		}
	}
}
