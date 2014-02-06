package com.sample.gui.container;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

@Mod(modid = SampleGuiContainerCore.MODID, version = SampleGuiContainerCore.VERSION)
public class SampleGuiContainerCore
{
	public static final String MODID = "GuiContainer";
	public static final String VERSION = "0.0.0";

	/*
	 * このクラスの静的なインスタンスを生成する.
	 * openGuiの引数に必要.
	 */
	@Mod.Instance("GuiContainer")
	public static SampleGuiContainerCore instance;

	public static Block blockGui;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		blockGui = (new BlockGui(Material.rock))
				.setBlockName("blockGui")
				.setBlockTextureName("cauldron_inner")
				.setCreativeTab(CreativeTabs.tabBlock);

		GameRegistry.registerBlock(blockGui, "blockGui");

		/*
		 * IGuiHandlerを実装したクラスを, このmodのクラスと関連付けて登録するメソッド.
		 */
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
	}
}
