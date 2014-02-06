package com.sample.gui.tileentity;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiTileEntityHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntityGuiSample tileEntity = (TileEntityGuiSample)world.getTileEntity(x, y, z);

		if (ID == 0 && tileEntity != null)
		{
			return new ContainerGuiTileEntity(player.inventory, tileEntity);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntityGuiSample tileEntity = (TileEntityGuiSample)world.getTileEntity(x, y, z);

		if (ID == 0 && tileEntity != null)
		{
			return new GuiTileEntity(player.inventory, tileEntity);
		}
		return null;
	}
}
