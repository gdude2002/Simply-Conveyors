package com.momnop.simplyconveyors.blocks.conveyors.special;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.SimplyConveyorsSpecialCreativeTab;
import com.momnop.simplyconveyors.blocks.BlockPoweredConveyor;
import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.blocks.conveyors.tiles.TileEntityDetectorBackwardsPath;
import com.momnop.simplyconveyors.helpers.ConveyorHelper;
import com.momnop.simplyconveyors.items.ItemConveyorResistanceBoots;
import com.momnop.simplyconveyors.items.ItemWrench;

public class BlockMovingBackwardsDetectorPath extends BlockPoweredConveyor implements ITileEntityProvider {
	
	private final double speed;
	
	public BlockMovingBackwardsDetectorPath(double speed, Material material, String unlocalizedName) {
		super(material);
		setCreativeTab(SimplyConveyorsSpecialCreativeTab.INSTANCE);
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
	public void neighborChanged(IBlockState state, World worldIn,
			BlockPos pos, Block blockIn, BlockPos pos2) {
		
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source,
			BlockPos pos) {
		return SimplyConveyorsBlocks.UPSIDE_DOWN_CONVEYOR_AABB;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState blockState) {
		return false;
	}
	
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
		if (blockState.getValue(POWERED) == true && side != blockState.getValue(FACING)) {
			return 15;
		}
        return 0;
    }

	@Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
		if (blockState.getValue(POWERED) == true && side != blockState.getValue(FACING)) {
			return 15;
		}
        return 0;
    }
	
	private void notifyNeighbors(World worldIn, BlockPos pos, EnumFacing facing)
    {
        worldIn.notifyNeighborsOfStateChange(pos, this, false);
        worldIn.notifyNeighborsOfStateChange(pos.offset(facing.getOpposite()), this, false);
    }
	
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            if (((Boolean)state.getValue(POWERED)).booleanValue())
            {
            	worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(false)));
                this.notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue(FACING));
                worldIn.markBlockRangeForRenderUpdate(pos, pos);
            }
        }
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    @Override
    public boolean canProvidePower(IBlockState state)
    {
        return true;
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
		
		if (entity instanceof EntityPlayer) {
   			EntityPlayer player = (EntityPlayer) entity;
   			if (player.inventory.player.inventory.armorInventory.get(EntityEquipmentSlot.FEET.getIndex()) != ItemStack.field_190927_a && player.inventory.armorInventory.get(EntityEquipmentSlot.FEET.getIndex()).getItem() instanceof ItemConveyorResistanceBoots) {
   				return;
   			}
   		}
		
		if (!entity.isSneaking()) {
			ConveyorHelper.centerBasedOnFacing(false, pos, entity, direction);
			
            entity.motionX += this.getSpeed() * direction.getFrontOffsetX();
            ConveyorHelper.lockSpeed(false, this.getSpeed(), entity, direction);
			
			entity.motionZ += this.getSpeed() * direction.getFrontOffsetZ();
			ConveyorHelper.lockSpeed(true, this.getSpeed(), entity, direction);
			
			entity.motionY += 0.65F;
			if (entity.motionY > 0.65F) {
				entity.motionY = 0.65F;
			}

			if (entity instanceof EntityItem) {
				final EntityItem item = (EntityItem) entity;
				item.setAgeToCreativeDespawnTime();
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos,
			IBlockState state, EntityPlayer playerIn, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!playerIn.isSneaking() && playerIn.getHeldItemMainhand() == ItemStack.field_190927_a && playerIn.getHeldItemOffhand() == ItemStack.field_190927_a) {
			TileEntityDetectorBackwardsPath grabber = (TileEntityDetectorBackwardsPath) worldIn.getTileEntity(pos);
			try {
				if (!worldIn.isRemote && !grabber.getFilterList().isEmpty()) {
					if (grabber.getBlacklisted() == false) {
						playerIn.addChatMessage(new TextComponentString("Currently whitelisting: "));
					} else {
						playerIn.addChatMessage(new TextComponentString("Currently blacklisting: "));
					}
					for (String string : grabber.getFilterList()) {
						playerIn.addChatMessage(new TextComponentString(Class.forName(string).getSimpleName()));
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return true;
		} else if (playerIn.getHeldItemMainhand() == ItemStack.field_190927_a && playerIn.getHeldItemOffhand() == ItemStack.field_190927_a) {
			TileEntityDetectorBackwardsPath grabber = (TileEntityDetectorBackwardsPath) worldIn.getTileEntity(pos);
			grabber.setFilterList(new ArrayList<String>());
			if (worldIn.isRemote) {
				playerIn.addChatMessage(new TextComponentString("Cleared all filters."));
			}
			return true;
		} else if (playerIn.getHeldItemMainhand() != ItemStack.field_190927_a && playerIn.getHeldItemMainhand().getItem() instanceof ItemWrench) {
			TileEntityDetectorBackwardsPath grabber = (TileEntityDetectorBackwardsPath) worldIn.getTileEntity(pos);
			grabber.setBlacklisted(!grabber.getBlacklisted());
			if (grabber.getBlacklisted() && worldIn.isRemote) {
				playerIn.addChatMessage(new TextComponentString("Now blacklisting."));
			} else if (worldIn.isRemote) {
				playerIn.addChatMessage(new TextComponentString("Now whitelisting."));
			}
			return true;
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityDetectorBackwardsPath();
	}
}