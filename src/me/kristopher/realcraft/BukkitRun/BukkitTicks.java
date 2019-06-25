package me.kristopher.realcraft.BukkitRun;

import me.kristopher.realcraft.Realcraft;
import me.kristopher.realcraft.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BukkitTicks extends BukkitRunnable {

	private Realcraft plugin;
	private static Map<UUID, Long> playersCooldown;
	private Player p;
	private UUID uuid;

	public BukkitTicks(Realcraft plugin) {
		this.plugin = plugin;
		playersCooldown = new HashMap<>();
	}

	public BukkitTicks() { }
	boolean day = false; //day/night boolean

	@Override
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			World w = p.getWorld();

			//day or night boolean operation
			if(p.getWorld().getTime() < 13000 && !day)
			{
				day = true;
			}
			else if(p.getWorld().getTime() >= 13000 && day)
			{
				day = false;
			}

			//If player more than 200 blocks up - he we start suffocating
			if (p.getLocation().getBlockY() >= 200) {
				if (!p.hasPotionEffect(PotionEffectType.WATER_BREATHING)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 8000, 1));
					p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 8000, 1));
					if (canSendMessage(p.getUniqueId()))
						p.sendMessage(StringUtil.inColor(plugin.getMsgs().getTooHigh()));
				}
			} else if (p.getLocation().getBlockY() <= 200) { //But if lower than 200 all effects will disappear
				p.removePotionEffect(PotionEffectType.POISON);
				p.removePotionEffect(PotionEffectType.CONFUSION);
			}

			//Checking for heating sources near player
			int radius = 5; //With a radius 5
			Location loc = p.getLocation();

			if (w.hasStorm() || !day) {
				for (int x = -(radius); x <= radius; x++) {
					for (int y = -(radius); y <= radius; y++) {
						for (int z = -(radius); z <= radius; z++) {
							if (loc.getBlock().getRelative(x, y, z).getType() == Material.CAMPFIRE) {
								p.sendMessage("5 Material.CAMPFIRE");
								return;
							} else if (loc.getBlock().getRelative(x, y, z).getType() == Material.LAVA) {
								p.sendMessage("5 Material.LAVA");
								return;
							} else if (loc.getBlock().getRelative(x, y, z).getType() == Material.MAGMA_BLOCK) {
								p.sendMessage("5 MAGMA_BLOCK ");
								return;
							}
						}
					}
				}
				int lowradius = 3; //With a radius 3
				for (int x = -(lowradius); x <= lowradius; x++) {
					for (int y = -(lowradius); y <= lowradius; y++) {
						for (int z = -(lowradius); z <= lowradius; z++) {
							if (loc.getBlock().getRelative(x, y, z).getType() == Material.TORCH && (x != 0 || y != 0 || z != 0)) {
								p.sendMessage("3 Material.TORCH");
								return;
							} else if (loc.getBlock().getRelative(x, y, z).getType() == Material.LANTERN && (x != 0 || y != 0 || z != 0)) {
								p.sendMessage("3 Material.LANTERN");
								return;
							}
						}
					}
				}
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 1));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 80, 1));
				p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 80, 1));

			}
		}
	}

	//Delay before messages
	private boolean canSendMessage (UUID uuid){
		if (playersCooldown.containsKey(uuid)) {
			if (playersCooldown.get(uuid) < System.currentTimeMillis()) {
				playersCooldown.put(uuid, System.currentTimeMillis() + 1000);
				return true;
			}
		} else {
			playersCooldown.put(uuid, System.currentTimeMillis() + 1000);
			return true;
		}

		return false;
	}
}