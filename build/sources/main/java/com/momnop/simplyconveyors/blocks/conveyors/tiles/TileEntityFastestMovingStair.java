package com.momnop.simplyconveyors.blocks.conveyors.tiles;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

import com.momnop.simplyconveyors.blocks.conveyors.normal.BlockMovingFastestStairPath;
import com.momnop.simplyconveyors.items.ItemConveyorResistanceBoots;

public class TileEntityFastestMovingStair extends TileEntity implements ITickable {
	
	@Override
    public void update()
    {
			BlockMovingFastestStairPath block = (BlockMovingFastestStairPath) blockType;
            List entities = this.getWorld().getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(
            		pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1.1F, pos.getZ() + 1));
            for (Object obj : entities)
               {
            	if (obj instanceof Entity && !this.getWorld().getBlockState(this.getPos()).getValue(block.POWERED)) {
            	   Entity entity = (Entity)obj;
            	   
            	if (entity instanceof EntityPlayer) {
           			EntityPlayer player = (EntityPlayer) entity;
           			if (player.inventory.player.inventory.armorInventory.get(EntityEquipmentSlot.FEET.getIndex()) != ItemStack.field_190927_a && player.inventory.armorInventory.get(EntityEquipmentSlot.FEET.getIndex()).getItem() instanceof ItemConveyorResistanceBoots) {
           				return;
           			}
           		}
            	   
                if (entity != null && entity.onGround && !entity.isInWater())
                {
                	entity.stepHeight = 0.6F;
                	if (this.getWorld().getBlockState(this.pos).getValue(block.FACING) == EnumFacing.EAST) {
            			entity.motionZ += 0.5f;
            			if (entity.motionZ > 0.5f) {
            				entity.motionZ = 0.5f;
            			}
            		} else if (this.getWorld().getBlockState(this.pos).getValue(block.FACING) == EnumFacing.SOUTH) {
            			entity.motionX += -0.5f;
            			if (entity.motionX < -0.5f) {
            				entity.motionX = -0.5f;
            			}
            		} else if (this.getWorld().getBlockState(this.pos).getValue(block.FACING) == EnumFacing.NORTH) {
            			entity.motionX += 0.5f;
            			if (entity.motionX > 0.5f) {
            				entity.motionX = 0.5f;
            			}
            		} else if (this.getWorld().getBlockState(this.pos).getValue(block.FACING) == EnumFacing.WEST) {
            			entity.motionZ += -0.5f;
            			if (entity.motionZ < -0.5f) {
            				entity.motionZ = -0.5f;
            			}
            		}
                }
            }
        }
    }
}
