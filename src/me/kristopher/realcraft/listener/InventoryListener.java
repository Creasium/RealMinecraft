package me.kristopher.realcraft.listener;

import me.kristopher.realcraft.objects.ThickStickConfig;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import me.kristopher.realcraft.Realcraft;
import me.kristopher.realcraft.objects.WoodworkerConfig;
import me.kristopher.realcraft.util.ItemUtil;

public class InventoryListener implements Listener{
	private Realcraft plugin;
	
	public InventoryListener(Realcraft plugin) {
		this.plugin = plugin;
	}

	//Inventory crafting realisation
	//Craft for woodworker's hammer
	@EventHandler
	public void onPrepareItemCraft(PrepareItemCraftEvent e) {
		final CraftingInventory craftingInventory = e.getInventory();
        final ItemStack[] matrix = craftingInventory.getMatrix();

        for (ItemStack stack: matrix) {
            if (stack == null || stack.getType() == Material.AIR)
                return;
        }
//        WoodworkerConfig woodCfg = plugin.getWoodCfg();
//        ThickStickConfig stickCfg = plugin.getStickCfg();
//        if (matrix[0].getType() == Material.FLINT && matrix[1].getType() == Material.FLINT
//                && matrix[2].getType() == Material.STICK && matrix[3].getType() == Material.FLINT) {
//	        e.getInventory().setResult(woodCfg.getItem());
//	        for (int i = 0; i <= 4; i++) {
//				if (matrix[i].getAmount() > 1){
//					matrix[i].setAmount(matrix[i].getAmount()-1);
//				}
//	        }
//        }
	}
}
