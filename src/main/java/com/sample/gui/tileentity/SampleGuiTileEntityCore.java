package com.sample.gui.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;


@Mod(
	modid   = "GuiTileEntity",
	name    = "GuiTileEntity",
	version = "0.0.0"
)
@NetworkMod(
	clientSideRequired = true,
	serverSideRequired = false
)
public class SampleGuiTileEntityCore {

	@Mod.Instance("GuiTileEntity")
	public static SampleGuiTileEntityCore instance;

	public static Block blockGuiTileEntity;

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

		blockGuiTileEntity = (new BlockGuiTileEntity(4009, Material.rock))
				.setUnlocalizedName("blockGuiTileEntity")
				.setTextureName("planks_oak")
				.setCreativeTab(CreativeTabs.tabBlock);

		GameRegistry.registerBlock(blockGuiTileEntity, "blockGuiTileEntity");

		/*
		 * TileEntityを登録するメソッド.
		 * 引数はTileEntityのクラスと, キー(文字列)
		 */
		GameRegistry.registerTileEntity(TileEntityGuiSample.class, "TileEntityGuiSample");

		LanguageRegistry.addName(blockGuiTileEntity, "Block GUI with TileEntity");

		/*
		 * コンテナのunlocalizedNameにローカライズを設定するメソッド.
		 */
		LanguageRegistry.instance().addStringLocalization("container.tileEntityGuiSample", "GUI with TileEntity");

		NetworkRegistry.instance().registerGuiHandler(this, new GuiTileEntityHandler());
	}
}
