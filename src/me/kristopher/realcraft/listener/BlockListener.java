package me.kristopher.realcraft.listener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import me.kristopher.realcraft.objects.Messages;
import me.kristopher.realcraft.objects.ThickStickConfig;
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

public class BlockListener implements Listener {

	private Realcraft plugin;
	private Map<UUID, Long> playersCooldown;

	public BlockListener(Realcraft plugin) {
		this.plugin = plugin;
		playersCooldown = new HashMap<>();
	}
	//Removing wooden and stone blocks only with special tool
	@EventHandler
	public void blockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		Random random = new Random();

		ThickStickConfig stickCfg = plugin.getStickCfg();
		ItemStack stackInHand = p.getInventory().getItemInMainHand();
		ItemStack thickstick = stickCfg.getItem();

		//Checking item in hand when you are breaking wooden blocks
		if (b.getType().name().contains("LOG") || b.getType().name().contains("PLANKS") || b.getType().name().contains("OAK") || b.getType().name().contains("SPRUCE") || b.getType().name().contains("BIRCH") || b.getType().name().contains("JUNGLE") || b.getType().name().contains("ACACIA")) {
			if (!b.getType().name().contains("LEAVES") && !b.getType().name().contains("SAPLING")) {
					if (!p.getInventory().getItemInMainHand().getType().name().contains("AXE") || p.getInventory().getItemInMainHand().getType().name().contains("GOLDEN_AXE") || p.getInventory().getItemInMainHand().getType().name().contains("PICKAXE") || !stackInHand.getItemMeta().getDisplayName().equals(thickstick.getItemMeta().getDisplayName())) {
							e.setCancelled(true);

							if (canSendMessage(p.getUniqueId()))
								p.sendMessage(StringUtil.inColor(plugin.getMsgs().getCantBreakBlock()));
					}
			}
		} else if (b.getType().name().contains("STONE") || (b.getType().name().contains("GRANITE")) || b.getType().name().contains("DIORITE") || b.getType().name().contains("ANDESITE") || b.getType().name().contains("ORE") || b.getType().name().contains("ANDESITE") || b.getType().name().contains("QUARTZ")) {
			if (!p.getInventory().getItemInMainHand().getType().name().contains("PICKAXE")){
				e.setCancelled(true);

				if (canSendMessage(p.getUniqueId()))
					p.sendMessage(StringUtil.inColor(plugin.getMsgs().getCantBreakBlock()));
			}
		}

		//New drop for leaves (10%) (0.1f)
		if (b.getType().name().contains("LEAVES")) {
			double chance = 0.1f;
			if(random.nextDouble() <= chance) {
				b.getLocation().getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.STICK));
			}
		}

		//New drop for sand
		if (b.getType().name().equals("SAND")) {
			double chance = 0.03f;
			if(random.nextDouble() <= chance) {
				b.getLocation().getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.FLINT));
			}
		}

		//Chance of breaking thick stick (25%) (0.25f)
		if (stackInHand != null && stackInHand.hasItemMeta() && stackInHand.getItemMeta().hasDisplayName() && stackInHand.getItemMeta().hasLore()) {
			if (stackInHand.getItemMeta().getDisplayName().equals(thickstick.getItemMeta().getDisplayName()) || stackInHand.getItemMeta().getLore().equals(thickstick.getItemMeta().getLore())) {
				double chance = 0.25f;
				if(random.nextDouble() <= chance) {
					stackInHand.setAmount(stackInHand.getAmount() - 1);
					p.updateInventory();
					p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 1.0F, 1.0F);
					return;
				}
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
		if (b.getType().name().contains("LOG") || b.getType().name().contains("PLANKS") || b.getType().name().contains("OAK") || b.getType().name().contains("SPRUCE") || b.getType().name().contains("BIRCH") || b.getType().name().contains("JUNGLE") || b.getType().name().contains("ACACIA")) {
			if (!b.getType().name().contains("LEAVES") && !b.getType().name().contains("SAPLING")) {
				if (stackOffHand == null || stackOffHand.getType() == Material.AIR || stackOffHand.getType() != hammer.getType() || !stackOffHand.getItemMeta().getDisplayName().equals(hammer.getItemMeta().getDisplayName()) || !stackOffHand.getItemMeta().getLore().equals(hammer.getItemMeta().getLore())) {
					e.setCancelled(true);

					if (canSendMessage(p.getUniqueId()))
						p.sendMessage(StringUtil.inColor(messagesConfig.getCantPlaceWithoutHammer()));
					return;
				}

				//Removing durability from hammer when placeing blocks
				net.minecraft.server.v1_14_R1.ItemStack stack = CraftItemStack.asNMSCopy(stackOffHand);

				if (!stack.hasTag() || !stack.getTag().hasKey("durability")) {
					NBTTagCompound nbt = (stack.hasTag() ? stack.getTag() : new NBTTagCompound());
					nbt.setInt("durability", 512);  //place 512 blocks before breaking tool
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
