//package me.kristopher.realcraft.listener;
//
//import com.sun.media.jfxmedia.events.PlayerTimeListener;
//import me.kristopher.realcraft.Realcraft;
//import me.kristopher.realcraft.util.StringUtil;
//import org.bukkit.ChatColor;
//import org.bukkit.Location;
//import org.bukkit.Material;
//import org.bukkit.block.Block;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.player.PlayerMoveEvent;
//import org.bukkit.potion.PotionEffect;
//import org.bukkit.potion.PotionEffectType;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//public class PlayerListener implements Listener {
//	private Realcraft plugin;
//	private Map<UUID, Long> playersCooldown;
//
//	public PlayerListener(Realcraft plugin) {
//		this.plugin = plugin;
//		playersCooldown = new HashMap<>();
//	}
//
//	@EventHandler
//	public void PlayerMove(PlayerMoveEvent e){
//		Player p = e.getPlayer();
//
//		if (p.getLocation().getBlockY() >= 200) {
//			if (!p.hasPotionEffect(PotionEffectType.WATER_BREATHING)){
//				p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 8000, 1));
//				p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 8000, 1));
//				if (canSendMessage(p.getUniqueId()))
//					p.sendMessage(StringUtil.inColor(plugin.getMsgs().getTooHigh()));
//			}
//		}else if (p.getLocation().getBlockY() <= 200) {
//			p.removePotionEffect(PotionEffectType.POISON);
//			p.removePotionEffect(PotionEffectType.CONFUSION);
//		}
//
//		int radius = 8;
//		Location loc = p.getLocation();
//
//		for (int x = -(radius); x <= radius; x++)
//		{
//			for (int y = -(radius); y <= radius; y++)
//			{
//				for (int z = -(radius); z <= radius; z++)
//				{
//					if (loc.getBlock().getRelative(x,y,z).getType() == Material.CAMPFIRE && (x != 0 || y != 0 || z != 0))
//					{
//						p.sendMessage("Пошёл ты на хуй и не мёрзни");
//					}
//				}
//			}
//		}
//	}
//
//	//Delay before messages
//	private boolean canSendMessage(UUID uuid) {
//		if (playersCooldown.containsKey(uuid)) {
//			if (playersCooldown.get(uuid) < System.currentTimeMillis()) {
//				playersCooldown.put(uuid, System.currentTimeMillis()+60000);
//				return true;
//			}
//		} else {
//			playersCooldown.put(uuid, System.currentTimeMillis()+60000);
//			return true;
//		}
//
//		return false;
//	}
//}
