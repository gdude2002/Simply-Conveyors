package com.momnop.simplyconveyors.recipes;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.config.ConfigHandler;
import com.momnop.simplyconveyors.items.SimplyConveyorsItems;

/**
 * Handles the recipes
 */
public class RecipeHandler
{
	public int woolMeta[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };

	/**
	 * Registers the mod's recipes
	 */
	public static void doRecipes()
	{
		GameRegistry.addRecipe(new ItemStack(SimplyConveyorsItems.wrench, 1), new Object[] { "X X", " X ", " X ", 'X', Items.IRON_INGOT });

		GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockSlowMovingPath, ConfigHandler.normalConveyorRecipeAmount), new Object[] { "XXX", "SGS", 'X', Blocks.RAIL, 'G',
				Items.IRON_INGOT, 'S', Blocks.GLASS });
		GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockFastMovingPath, ConfigHandler.normalConveyorRecipeAmount), new Object[] { "XXX", "SGS", 'X', Blocks.RAIL, 'G',
				Items.GOLD_INGOT, 'S', Blocks.GLASS });
		GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockFastestMovingPath, ConfigHandler.normalConveyorRecipeAmount), new Object[] { "XXX", "SGS", 'X', Blocks.RAIL, 'G',
				Items.DIAMOND, 'S', Blocks.GLASS });

		GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockDropperMovingPath, 6), new Object[] { "RRR", "QHQ", 'R', Blocks.RAIL, 'Q', Blocks.QUARTZ_BLOCK, 'H', Blocks.HOPPER });

		GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockGrabberMovingPath), new Object[] { "RER", "QrQ", 'R', Blocks.RAIL, 'E', Items.ENDER_PEARL, 'Q', Blocks.QUARTZ_BLOCK, 'r',
				Items.REDSTONE });

		GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockBlockMovingPath), new Object[] { "RSR", "QEQ", 'R', Blocks.RAIL, 'E', Items.ENDER_PEARL, 'Q', Blocks.QUARTZ_BLOCK, 'S',
				Blocks.SAND });

		GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockDetectorMovingPath), new Object[] { "PTP", "QFQ", 'P', Blocks.STONE_PRESSURE_PLATE, 'T', Blocks.REDSTONE_TORCH, 'Q',
				Blocks.QUARTZ_BLOCK, 'F', SimplyConveyorsBlocks.blockFastMovingPath });

		addInverseRecipes(new ItemStack(SimplyConveyorsBlocks.blockDetectorMovingPath), new ItemStack(SimplyConveyorsBlocks.blockDetectorMovingBackwardsPath));

		addInverseRecipes(new ItemStack(SimplyConveyorsBlocks.blockDropperMovingPath), new ItemStack(SimplyConveyorsBlocks.blockDropperMovingBackwardsPath));

		GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockTransporterMovingPath), new Object[] { "HRH", "QQQ", 'H', Blocks.HOPPER, 'R', Blocks.RAIL, 'Q', Blocks.QUARTZ_BLOCK });

		GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockTrapDoorMovingPath), new Object[] { "RTR", "QQQ", 'R', Blocks.RAIL, 'T', Blocks.IRON_TRAPDOOR, 'Q', Blocks.QUARTZ_BLOCK });

		addInverseRecipes(new ItemStack(SimplyConveyorsBlocks.blockSlowMovingPath), new ItemStack(SimplyConveyorsBlocks.blockSlowMovingBackwardsPath));
		addInverseRecipes(new ItemStack(SimplyConveyorsBlocks.blockFastMovingPath), new ItemStack(SimplyConveyorsBlocks.blockFastMovingBackwardsPath));
		addInverseRecipes(new ItemStack(SimplyConveyorsBlocks.blockFastestMovingPath), new ItemStack(SimplyConveyorsBlocks.blockFastestMovingBackwardsPath));

		GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockSlowMovingStairPath, 6), new Object[] { "X  ", "XX ", "XXX", 'X', SimplyConveyorsBlocks.blockSlowMovingPath });
		GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockFastMovingStairPath, 6), new Object[] { "X  ", "XX ", "XXX", 'X', SimplyConveyorsBlocks.blockFastMovingPath });
		GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockFastestMovingStairPath, 6), new Object[] { "X  ", "XX ", "XXX", 'X', SimplyConveyorsBlocks.blockFastestMovingPath });

		addInverseRecipes(new ItemStack(SimplyConveyorsBlocks.blockSlowMovingStairPath), new ItemStack(SimplyConveyorsBlocks.blockSlowMovingVerticalPath), new ItemStack(
				SimplyConveyorsBlocks.blockSlowDownMovingVerticalPath));
		addInverseRecipes(new ItemStack(SimplyConveyorsBlocks.blockFastMovingStairPath), new ItemStack(SimplyConveyorsBlocks.blockFastMovingVerticalPath), new ItemStack(
				SimplyConveyorsBlocks.blockFastDownMovingVerticalPath));
		addInverseRecipes(new ItemStack(SimplyConveyorsBlocks.blockFastestMovingStairPath), new ItemStack(SimplyConveyorsBlocks.blockFastestMovingVerticalPath), new ItemStack(
				SimplyConveyorsBlocks.blockFastestDownMovingVerticalPath));

		GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockHoldingMovingPath), new Object[] { "rSr", "QRQ", 'S', Items.SLIME_BALL, 'I', Items.IRON_INGOT, 'Q', Blocks.QUARTZ_BLOCK, 'R',
				Blocks.REDSTONE_BLOCK, 'r', Blocks.RAIL });

		addInverseRecipes(new ItemStack(SimplyConveyorsBlocks.blockHoldingMovingPath), new ItemStack(SimplyConveyorsBlocks.blockHoldingMovingBackwardsPath));

		GameRegistry.addShapelessRecipe(new ItemStack(SimplyConveyorsItems.entityFilter), new Object[] { Items.PAPER, Items.EGG });

		GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockSlowSpongeMovingPath, 8), new Object[] { "CCC", "CSC", "CCC", 'S', Blocks.SPONGE, 'C',
				SimplyConveyorsBlocks.blockSlowMovingPath });
		GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockFastSpongeMovingPath, 8), new Object[] { "CCC", "CSC", "CCC", 'S', Blocks.SPONGE, 'C',
				SimplyConveyorsBlocks.blockFastMovingPath });
		GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockFastestSpongeMovingPath, 8), new Object[] { "CCC", "CSC", "CCC", 'S', Blocks.SPONGE, 'C',
				SimplyConveyorsBlocks.blockFastestMovingPath });

		GameRegistry.addShapelessRecipe(new ItemStack(SimplyConveyorsBlocks.blockSlowSpikeMovingPath), new Object[] { SimplyConveyorsBlocks.blockSlowMovingPath, Items.IRON_SWORD });
		GameRegistry.addShapelessRecipe(new ItemStack(SimplyConveyorsBlocks.blockFastSpikeMovingPath), new Object[] { SimplyConveyorsBlocks.blockFastMovingPath, Blocks.GOLD_BLOCK, Blocks.LAPIS_BLOCK,
				Items.GOLDEN_SWORD });
		GameRegistry.addShapelessRecipe(new ItemStack(SimplyConveyorsBlocks.blockFastestSpikeMovingPath), new Object[] { SimplyConveyorsBlocks.blockFastestMovingPath, Blocks.DIAMOND_BLOCK,
				Blocks.LAPIS_BLOCK, Items.DIAMOND_SWORD });
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(SimplyConveyorsItems.conveyorResistanceBoots), new Object[] { Items.LEATHER_BOOTS, "slimeball" }));
		
		GameRegistry.addShapelessRecipe(new ItemStack(SimplyConveyorsBlocks.blockSlowMobMovingPath, 1), new Object[] { Items.FERMENTED_SPIDER_EYE, SimplyConveyorsBlocks.blockSlowMovingPath });
		GameRegistry.addShapelessRecipe(new ItemStack(SimplyConveyorsBlocks.blockFastMobMovingPath, 1), new Object[] { Items.FERMENTED_SPIDER_EYE, SimplyConveyorsBlocks.blockFastMovingPath });
		GameRegistry.addShapelessRecipe(new ItemStack(SimplyConveyorsBlocks.blockFastestMobMovingPath, 1), new Object[] { Items.FERMENTED_SPIDER_EYE, SimplyConveyorsBlocks.blockFastestMovingPath });
		
		addColorRecipes(SimplyConveyorsBlocks.blockBrokenRoad);
		addColorRecipes(SimplyConveyorsBlocks.blockFullRoad);
		
		GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockAsphault, 8), new Object[] { "BBB", "BDB", "BBB", 'B', new ItemStack(Blocks.STONE, 1, 0), 'D', new ItemStack(Items.COAL, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockConcrete, 8), new Object[] { "BBB", "BDB", "BBB", 'B', new ItemStack(Blocks.STONE, 1, 0), 'D', new ItemStack(Blocks.DIRT, 1, 0) });
		
		for (int i = 0; i < 15; i++) {
			GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockBrokenRoad, 8, i), new Object[] { "D D", "AAA", 'A', new ItemStack(SimplyConveyorsBlocks.blockAsphault), 'D', new ItemStack(Items.DYE, 1, 15 - i) });
		}
		
		for (int i = 0; i < 15; i++) {
			GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockFullRoad, 8, i), new Object[] { "DDD", "AAA", 'A', new ItemStack(SimplyConveyorsBlocks.blockAsphault), 'D', new ItemStack(Items.DYE, 1, 15 - i) });
		}
	}
	
	public static void addColorRecipes(Block block) {
		for (int i = 0; i < 15; i++) {
			GameRegistry.addRecipe(new ItemStack(block, 8, i), new Object[] { "BBB", "BDB", "BBB", 'B', new ItemStack(block, 1, OreDictionary.WILDCARD_VALUE), 'D', new ItemStack(Items.DYE, 1, 15 - i) });
		}
	}

	public static void addInverseRecipe(ItemStack input, ItemStack output)
	{
		GameRegistry.addShapelessRecipe(input, new Object[] { output });
	}

	public static void addInverseRecipes(ItemStack input, ItemStack output)
	{
		GameRegistry.addShapelessRecipe(input, new Object[] { output });
		GameRegistry.addShapelessRecipe(output, new Object[] { input });
	}

	public static void addInverseRecipes(ItemStack input1, ItemStack input2, ItemStack input3)
	{
		GameRegistry.addShapelessRecipe(input1, new Object[] { input3 });
		GameRegistry.addShapelessRecipe(input2, new Object[] { input1 });
		GameRegistry.addShapelessRecipe(input3, new Object[] { input2 });
	}

}