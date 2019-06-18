package me.kristopher.realcraft.listener;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.kristopher.realcraft.objects.Messages;
import me.kristopher.realcraft.objects.WoodworkerConfig;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.kristopher.realcraft.Realcraft;
import me.kristopher.realcraft.util.StringUtil;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class BlockListener implements Listener {

	private Realcraft plugin;
	private Map<UUID, Long> playersCooldown;

	public BlockListener(Realcraft plugin) {
		this.plugin = plugin;
		playersCooldown = new HashMap<>();
	}
	//Removing wooden blocks only with axes
	@EventHandler
	public void blockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();

		if (b.getType().name().contains("LOG") || b.getType().name().contains("PLANKS")) {
			if (!p.getInventory().getItemInMainHand().getType().name().contains("AXE") || p.getInventory().getItemInMainHand().getType().name().contains("GOLDEN_AXE") || p.getInventory().getItemInMainHand().getType().name().contains("PICKAXE")) {

				e.setCancelled(true);

				if (canSendMessage(p.getUniqueId()))
					p.sendMessage(StringUtil.inColor(plugin.getMsgs().getCantBreakBlock()));
			}
		}
	}
	//Woodworker's hammer realisation
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		WoodworkerConfig woodCfg = plugin.getWoodCfg();
		Messages messagesConfig = plugin.getMsgs();
		ItemStack stackOffHand = p.getInventory().getItemInOffHand();
		ItemStack hammer = woodCfg.getItem();

		//We can place wooden blocks only with hammer in left hand
		if (stackOffHand == null || stackOffHand.getType() == Material.AIR || stackOffHand.getType() != hammer.getType() || !stackOffHand.getItemMeta().getDisplayName().equals(hammer.getItemMeta().getDisplayName()) || !stackOffHand.getItemMeta().getLore().equals(hammer.getItemMeta().getLore())) {
			if (b.getType().name().contains("LOG") || b.getType().name().contains("PLANKS")) {
				e.setCancelled(true);

				if (canSendMessage(p.getUniqueId()))
					p.sendMessage(StringUtil.inColor(messagesConfig.getCantPlaceWithoutHammer()));
				return;
			}
			return;
		}

		//Removing durability from hammer when placeing blocks
		net.minecraft.server.v1_14_R1.ItemStack stack = CraftItemStack.asNMSCopy(stackOffHand);

		if (!stack.hasTag() || !stack.getTag().hasKey("durability")) {
			NBTTagCompound nbt = (stack.hasTag() ? stack.getTag() : new NBTTagCompound());
			nbt.setInt("durability", 512);  //remove 512 blocks before breaking tool
			stack.setTag(nbt);
			stackOffHand = CraftItemStack.asCraftMirror(stack);
		}

		NBTTagCompound nbt = stack.getTag();

		if (nbt.getInt("durability") <= 0) {
			p.getInventory().setItemInOffHand(null);
			p.updateInventory();
			p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
			return;
		}
		//Math operation to calculate durability for GOLDEN_AXE
		//Which have durability only 32
		int durability = nbt.getInt("durability") - 1;
		int procentMain = (int) (((double) durability / 512.f * 100.f));
		int durabilityChange = (int) (((double) 32.f * procentMain / 100.f));

		stack = CraftItemStack.asNMSCopy(stackOffHand);
		nbt.setInt("durability", durability);
		stack.setTag(nbt);
		stackOffHand = CraftItemStack.asBukkitCopy(stack);

		Damageable damageableMeta = (Damageable) stackOffHand.getItemMeta();

		if (damageableMeta == null)
			return;

		damageableMeta.setDamage(32 - durabilityChange);
		stackOffHand.setItemMeta((ItemMeta) damageableMeta);

		p.getInventory().setItemInOffHand(stackOffHand);
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
