package me.kristopher.realcraft.objects;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import me.kristopher.realcraft.Realcraft;

//Four crafting recipes for hammer
public class Recipe{
	private Realcraft plugin;
	public Recipe(Realcraft plugin) {
		this.plugin = plugin;
	}
	public void registerRecipes() {
		ShapedRecipe woodworkerZeroRecipe = new ShapedRecipe(new NamespacedKey(plugin, "woodworkerzero"), plugin.getWoodCfg().getItem());
		ShapedRecipe woodworkerOneRecipe = new ShapedRecipe(new NamespacedKey(plugin, "woodworkerone"), plugin.getWoodCfg().getItem());
		ShapedRecipe woodworkerTwoRecipe = new ShapedRecipe(new NamespacedKey(plugin, "woodworkertwo"), plugin.getWoodCfg().getItem());
		ShapedRecipe woodworkerThreeRecipe = new ShapedRecipe(new NamespacedKey(plugin, "woodworkerthree"), plugin.getWoodCfg().getItem());

		woodworkerZeroRecipe.shape("   ", "## ", "*# ");
		woodworkerOneRecipe.shape("## ", "*# ", "   ");
		woodworkerTwoRecipe.shape(" ##", " *#", "   ");
		woodworkerThreeRecipe.shape("   ", " ##", " *#");
		
		woodworkerZeroRecipe.setIngredient('#', Material.FLINT);
		woodworkerZeroRecipe.setIngredient('*', Material.STICK);
		woodworkerOneRecipe.setIngredient('#', Material.FLINT);
		woodworkerOneRecipe.setIngredient('*', Material.STICK);
		woodworkerTwoRecipe.setIngredient('#', Material.FLINT);
		woodworkerTwoRecipe.setIngredient('*', Material.STICK);
		woodworkerThreeRecipe.setIngredient('#', Material.FLINT);
		woodworkerThreeRecipe.setIngredient('*', Material.STICK);

		plugin.getServer().addRecipe(new FurnaceRecipe(new NamespacedKey(plugin, "RottenFlashKey"), new ItemStack(Material.BEEF), Material.ROTTEN_FLESH, 2, 300));
		plugin.getServer().addRecipe(woodworkerZeroRecipe);
		plugin.getServer().addRecipe(woodworkerOneRecipe);
		plugin.getServer().addRecipe(woodworkerTwoRecipe);
		plugin.getServer().addRecipe(woodworkerThreeRecipe);
	}
}
