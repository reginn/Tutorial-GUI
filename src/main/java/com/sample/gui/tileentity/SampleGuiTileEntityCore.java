package com.sample.gui.tileentity;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

@Mod(modid = SampleGuiTileEntityCore.MODID, version = SampleGuiTileEntityCore.VERSION)
public class SampleGuiTileEntityCore
{
	public static final String MODID = "GuiTileEntity";
	public static final String VERSION = "0.0.0";

	@Mod.Instance("GuiTileEntity")
	public static SampleGuiTileEntityCore instance;

	public static Block blockGuiTileEntity;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		blockGuiTileEntity = (new BlockGuiTileEntity(Material.rock))
				.setBlockName("blockGuiTileEntity")
				.setBlockTextureName("planks_oak")
				.setCreativeTab(CreativeTabs.tabBlock);

		GameRegistry.registerBlock(blockGuiTileEntity, "blockGuiTileEntity");
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		/*
		 * TileEntityを登録するメソッド.
		 * 引数はTileEntityのクラスと, キー(文字列)
		 */
		GameRegistry.registerTileEntity(TileEntityGuiSample.class, "TileEntityGuiSample");

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiTileEntityHandler());
	}
}
