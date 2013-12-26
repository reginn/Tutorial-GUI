package com.sample.gui.container;

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
	modid = "GuiContainer",
	name = "GuiContainer",
	version = "0.0.0"
)
/*
 * GUIを使う場合NetworkModアノテーションの付与が必要.
 * 基本的にclientSideRequired = true, serverSideRequired = falseでよい.
 */
@NetworkMod(
	clientSideRequired = true,
	serverSideRequired = false
)
public class SampleGuiContainerCore {

	/*
	 * このクラスの静的なインスタンスを生成する.
	 * openGuiの引数に必要.
	 */
	@Mod.Instance("GuiContainer")
	public static SampleGuiContainerCore instance;

	public static Block blockGui;

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

		blockGui = (new BlockGui(4008, Material.rock))
				.setUnlocalizedName("blockGui")
				.setTextureName("cauldron_inner")
				.setCreativeTab(CreativeTabs.tabBlock);

		GameRegistry.registerBlock(blockGui, "blockGui");
		LanguageRegistry.addName(blockGui, "Block Gui");

		/*
		 * IGuiHandlerを実装したクラスを, このmodのクラスと関連付けて登録するメソッド.
		 */
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
	}
}
