package com.momnop.simplyconveyors.blocks.conveyors.normal;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.momnop.simplyconveyors.SimplyConveyorsCreativeTab;
import com.momnop.simplyconveyors.blocks.BlockPoweredConveyor;
import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.helpers.ConveyorHelper;

public class BlockMovingPath extends BlockPoweredConveyor {
	
	private final double speed;
	
	public BlockMovingPath(double speed, Material material, String unlocalizedName) {
		super(material);
		setCreativeTab(SimplyConveyorsCreativeTab.INSTANCE);
		setHardness(1.5F);
		setRegistryName(unlocalizedName);
		setUnlocalizedName(this.getRegistryName().toString()
				.replace("simplyconveyors:", ""));
		useNeighborBrightness = true;
		setHarvestLevel("pickaxe", 0);

		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false));
		this.speed = speed;
	}
	
	public double getSpeed() {
		return speed;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source,
			BlockPos pos) {
		return SimplyConveyorsBlocks.CONVEYOR_AABB;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState blockState) {
		return false;
	}
	
	public static EnumFacing getFacingFromEntity(BlockPos clickedBlock,
			EntityLivingBase entity) {
		return EnumFacing.getFacingFromVector(
				(float) (entity.posX - clickedBlock.getX()),
				(float) (entity.posY - clickedBlock.getY()),
				(float) (entity.posZ - clickedBlock.getZ()));
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos,
			IBlockState blockState, Entity entity) {
		final EnumFacing direction = blockState.getValue(FACING).getOpposite();
		
		if (!entity.isSneaking() && !blockState.getValue(POWERED)) {
			ConveyorHelper.centerBasedOnFacing(true, pos, entity, direction);
			
            entity.motionX += this.getSpeed() * direction.getFrontOffsetX();
            ConveyorHelper.lockSpeed(false, this.getSpeed(), entity, direction);
			
			entity.motionZ += this.getSpeed() * direction.getFrontOffsetZ();
			ConveyorHelper.lockSpeed(true, this.getSpeed(), entity, direction);

			if (entity instanceof EntityItem) {
				final EntityItem item = (EntityItem) entity;
				item.setAgeToCreativeDespawnTime();
			}
		}
	}
}