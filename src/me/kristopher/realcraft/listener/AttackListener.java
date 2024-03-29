package me.kristopher.realcraft.listener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import me.kristopher.realcraft.Realcraft;
import me.kristopher.realcraft.objects.Messages;
import me.kristopher.realcraft.util.StringUtil;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class AttackListener implements Listener {

	private Realcraft plugin;
	private Map<UUID, Long> playersCooldown;

	public AttackListener(Realcraft plugin) {
		this.plugin = plugin;
		playersCooldown = new HashMap<>();
	}

	//Every mob will drop leather with 40% chance
	@EventHandler
	public void entityDeath(EntityDeathEvent e){
		Random random = new Random();

		double chance = 0.4f;
		if(random.nextDouble() <= chance) {
			e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.LEATHER));
		}
	}

	//Can attack mobs only with special tools
	@EventHandler
	public void attackMobs(EntityDamageByEntityEvent e) {
		Messages messagesConfig = plugin.getMsgs();

		if (e.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) { //Just my magic code w(o.o)w
			return;
		}else if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			ItemStack pInv = p.getInventory().getItemInMainHand();

			if (!pInv.getType().name().contains("SWORD") && !pInv.getType().name().contains("AXE")) {
				if (!(e.getEntity() instanceof Chicken)) {
					e.setCancelled(true);

					if (canSendMessage(p.getUniqueId()))
						p.sendMessage(StringUtil.inColor(messagesConfig.getCantHitWithoutSword()));
					return;
				}
			}
		}
	}

	//Delay before messages
	private boolean canSendMessage(UUID uuid) {
		if (playersCooldown.containsKey(uuid)) {
			if (playersCooldown.get(uuid) < System.currentTimeMillis()) {
				playersCooldown.put(uuid, System.currentTimeMillis()+1000);
				return true;
			}
		} else {
			playersCooldown.put(uuid, System.currentTimeMillis()+1000);
			return true;
		}

		return false;
	}
}
