package com.sample.gui.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiTileEntity extends GuiContainer
{
	private final static ResourceLocation GUI_BACKGROUND = new ResourceLocation("textures/gui/container/dispenser.png");
	private TileEntityGuiSample tileEntity;

	public GuiTileEntity(IInventory inventory, TileEntityGuiSample tileEntity)
	{
		super(new ContainerGuiTileEntity(inventory, tileEntity));
		this.tileEntity = tileEntity;
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int xMouse, int yMouse)
	{
		this.fontRendererObj.drawString(StatCollector.translateToLocal(this.tileEntity.getInventoryName()), 8, 6, 0x404040);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialRenderTick, int xMouse, int yMouse)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_BACKGROUND);
		int x = (this.width  - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}
}
