package com.sample.gui.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerGuiTileEntity extends Container
{
	private IInventory inventoryPlayer;
	private TileEntityGuiSample tileEntity;

	public ContainerGuiTileEntity(IInventory inventoryPlayer, TileEntityGuiSample tileEntity)
	{
		this.inventoryPlayer = inventoryPlayer;
		this.tileEntity      = tileEntity;

		/*
		 * 3*3のインベントリ
		 */
		for (int col = 0; col < 3; ++col)
		{
			for (int row = 0; row < 3; ++row)
			{
				this.addSlotToContainer(new Slot(tileEntity, row + col * 3,  62 + row * 18, 17 + col * 18));
			}
		}

		/*
		 *  3*9のプレイヤーインベントリ
		 */
		for (int col = 0; col < 3; ++col)
		{
			for (int row = 0; row < 9; ++row)
			{
				this.addSlotToContainer(new Slot(this.inventoryPlayer, row + col * 9 + 9, 8 + row * 18, 84 + col * 18));
			}
		}

		/*
		 *  1*9のプレイヤーインベントリ
		 */
		for (int row = 0; row < 9; ++row)
		{
			this.addSlotToContainer(new Slot(this.inventoryPlayer, row, 8 + row * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		 return tileEntity.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex)
	{
		Slot slot = (Slot)this.inventorySlots.get(slotIndex);
		ItemStack srcItemStack = null;

		if (slot != null && slot.getHasStack())
		{
			ItemStack destItemStack = slot.getStack();
			srcItemStack = destItemStack.copy();

			if (slotIndex < 9 && !this.mergeItemStack(destItemStack, 9, 45, false))
			{
				return null;
			}

			if (slotIndex >= 9 && !this.mergeItemStack(destItemStack, 0, 9, false))
			{
				return null;
			}

			if (destItemStack.stackSize == 0)
			{
				slot.putStack((ItemStack)null);
			}
			else
			{
				slot.onSlotChanged();
			}
		}

		return srcItemStack;
	}
}
