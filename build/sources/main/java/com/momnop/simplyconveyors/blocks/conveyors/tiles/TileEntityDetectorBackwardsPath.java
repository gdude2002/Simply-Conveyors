package com.momnop.simplyconveyors.blocks.conveyors.tiles;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingBackwardsDetectorPath;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingBackwardsDetectorPath;

public class TileEntityDetectorBackwardsPath extends TileEntity implements ITickable {
	
	/**
	 * This is the name of the class the entity should be filtered to.
	 */
	private String entityFilter = "net.minecraft.entity.Entity";

	@Override
	public void update() {
		
		List entities = this.worldObj.getEntitiesWithinAABB(Entity.class,
				new AxisAlignedBB(pos.getX() - 0, pos.getY() - 1, pos.getZ() - 0,
						pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1));
		
		for (int i = 0; i < entities.size(); i++) {
			Object obj = entities.get(i);
			if (obj instanceof Entity) {
				Entity ent = (Entity) obj;
				try {
					if (Class.forName(entityFilter).isInstance(ent)) {
						String oldEntityFilter = new String(entityFilter);
						this.getWorld().setBlockState(this.getPos(), this.getWorld().getBlockState(this.getPos()).withProperty(((BlockMovingBackwardsDetectorPath) blockType).FACING, this.getWorld().getBlockState(this.getPos()).getValue(((BlockMovingBackwardsDetectorPath) blockType).FACING)).withProperty(((BlockMovingBackwardsDetectorPath) blockType).POWERED, true));
						entityFilter = oldEntityFilter;
						return;
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		String oldEntityFilter = new String(entityFilter);
		this.getWorld().setBlockState(this.getPos(), this.getWorld().getBlockState(this.getPos()).withProperty(((BlockMovingBackwardsDetectorPath) blockType).FACING, this.getWorld().getBlockState(this.getPos()).getValue(((BlockMovingBackwardsDetectorPath) blockType).FACING)).withProperty(((BlockMovingBackwardsDetectorPath) blockType).POWERED, false));
		entityFilter = oldEntityFilter;
		
		markDirty();
	}

	public void setEntityFilter(Class filterClass) {
		entityFilter = filterClass.getName();
	}

	public String getEntityFilter() {
		return entityFilter;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setString("entityFilter", entityFilter);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		entityFilter = compound.getString("entityFilter");
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 1, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos,
			IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}
}