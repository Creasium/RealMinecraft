package me.kristopher.realcraft.BukkitRun;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.kristopher.realcraft.Realcraft;
import me.kristopher.realcraft.util.StringUtil;
import org.bukkit.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;

public class BukkitTicks extends BukkitRunnable{

	private Realcraft plugin;
	private static Map<UUID, Long> playersCooldown;
	private Player p;
	private UUID uuid;

	public BukkitTicks(Realcraft plugin) {
		this.plugin = plugin;
	}

	static {
		playersCooldown = new HashMap<>();
	}

	public BukkitTicks() {
	}

	boolean day = false; //day/night boolean default

	@Override
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			World w = p.getWorld();

			//day or night boolean operation
			if (p.getWorld().getTime() < 13000 && !day) {
				day = true;
			} else if (p.getWorld().getTime() >= 13000 && day) {
				day = false;
			}
			//If player more than 200 blocks up - he we start suffocating
			if (p.getLocation().getBlockY() >= 200) {
				if (!p.hasPotionEffect(PotionEffectType.WATER_BREATHING)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 8000, 0, false, false, false));
					p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 8000, 1, false, false, false));
					if (canSendMessage(p.getUniqueId())){
						p.sendMessage(StringUtil.inColor("&cYou are too high. You need breathing effect."));
						//p.sendMessage(StringUtil.inColor(plugin.getMsgs().getTooHigh()));
					}
				} else {
					p.removePotionEffect(PotionEffectType.POISON);
					p.removePotionEffect(PotionEffectType.CONFUSION);
				}
			} else if (p.getLocation().getBlockY() <= 200 || p.hasPotionEffect(PotionEffectType.POISON) || p.hasPotionEffect(PotionEffectType.CONFUSION)) { //But if lower than 200 all effects will disappear
				p.removePotionEffect(PotionEffectType.POISON);
				p.removePotionEffect(PotionEffectType.CONFUSION);
			}

			//Checking for heating sources near player
			int radius = 5; //With a radius 5
			Location loc = p.getLocation();

			if (w.hasStorm() || !day) {
				if (p.getInventory().getBoots() == null || p.getInventory().getLeggings() == null || p.getInventory().getChestplate() == null || p.getInventory().getHelmet() == null) {
					for (int x = -(radius); x <= radius; x++) {
						for (int y = -(radius); y <= radius; y++) {
							for (int z = -(radius); z <= radius; z++) {
								if (loc.getBlock().getRelative(x, y, z).getType() == Material.CAMPFIRE) {
									p.sendMessage("|DEBUG| >>> 5 Material.CAMPFIRE"); //>>>>> DEBUG <<<<<
									return;
								} else if (loc.getBlock().getRelative(x, y, z).getType() == Material.LAVA) {
									p.sendMessage("|DEBUG| >>> 5 Material.LAVA"); //>>>>> DEBUG <<<<<
									return;
								} else if (loc.getBlock().getRelative(x, y, z).getType() == Material.MAGMA_BLOCK) {
									p.sendMessage("|DEBUG| >>> 5 MAGMA_BLOCK "); //>>>>> DEBUG <<<<<
									return;
								}
							}
						}
					}
					int lowradius = 3; //With a radius 3
					for (int x = -(lowradius); x <= lowradius; x++) {
						for (int y = -(lowradius); y <= lowradius; y++) {
							for (int z = -(lowradius); z <= lowradius; z++) {
								if (loc.getBlock().getRelative(x, y, z).getType() == Material.TORCH || loc.getBlock().getRelative(x, y, z).getType() == Material.WALL_TORCH) {
									p.sendMessage("|DEBUG| >>> 3 Material.TORCH"); //>>>>> DEBUG <<<<<
									return;
								} else if (loc.getBlock().getRelative(x, y, z).getType() == Material.LANTERN) {
									p.sendMessage("|DEBUG| >>> 3 Material.LANTERN"); //>>>>> DEBUG <<<<<
									return;
								}
							}
						}
					}
					//Add some effects when player without heating

					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 0, false, false, false));
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 120, 0, false, false, false));
					p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 120, 0, false, false, false));
					if (w.hasStorm()) {
						if (p.getHealth() >= 1) {
							p.setHealth(p.getHealth() - 1);
							p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_HURT, 1.0F, 1.0F);
						}
						System.out.println("|DEBUG| >>>" + p.getHealth()); //>>>>> DEBUG <<<<<
						//p.sendTitle("", StringUtil.inColor(plugin.getMsgs().getFreezing()), 5, 5, 5);
						p.sendTitle("", ChatColor.AQUA + "You are freezing!", 5, 5, 5);
						if (p.getHealth() <= 1){
							p.setHealth(0.0f);
						}
					}
				}

			}
			//Armor (full set) weight
			if (p.getInventory().getHelmet() == null || p.getInventory().getChestplate() == null || p.getInventory().getLeggings() == null || p.getInventory().getBoots() == null) {
				p.setWalkSpeed(0.2f);
				return;
			} else if (p.getInventory().getBoots().getType() == Material.LEATHER_BOOTS && p.getInventory().getLeggings().getType() == Material.LEATHER_LEGGINGS && p.getInventory().getChestplate().getType() == Material.LEATHER_CHESTPLATE && p.getInventory().getHelmet().getType() == Material.LEATHER_HELMET) {
				p.setWalkSpeed(0.17f);
			} else if (p.getInventory().getBoots().getType() == Material.IRON_BOOTS && p.getInventory().getLeggings().getType() == Material.IRON_LEGGINGS && p.getInventory().getChestplate().getType() == Material.IRON_CHESTPLATE && p.getInventory().getHelmet().getType() == Material.IRON_HELMET) {
				p.setWalkSpeed(0.16f);
			} else if (p.getInventory().getBoots().getType() == Material.DIAMOND_BOOTS && p.getInventory().getLeggings().getType() == Material.DIAMOND_LEGGINGS && p.getInventory().getChestplate().getType() == Material.DIAMOND_CHESTPLATE && p.getInventory().getHelmet().getType() == Material.DIAMOND_HELMET) {
				p.setWalkSpeed(0.14f);
			} else if (p.getInventory().getBoots().getType() == Material.CHAINMAIL_BOOTS && p.getInventory().getLeggings().getType() == Material.CHAINMAIL_LEGGINGS && p.getInventory().getChestplate().getType() == Material.CHAINMAIL_CHESTPLATE && p.getInventory().getHelmet().getType() == Material.CHAINMAIL_HELMET) {
				p.setWalkSpeed(0.18f);
			}


		}
	}
	//Delay before messages
	private boolean canSendMessage(UUID uuid) {
		if (playersCooldown.containsKey(uuid)) {
			if (playersCooldown.get(uuid) < System.currentTimeMillis()) {
				playersCooldown.put(uuid, System.currentTimeMillis()+60000);
				return true;
			}
		} else {
			playersCooldown.put(uuid, System.currentTimeMillis()+60000);
			return true;
		}
		return false;
	}
}