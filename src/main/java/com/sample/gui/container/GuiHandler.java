package com.sample.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;

/*
 * GUIの追加はIGuiHandlerインタフェースを実装する.
 * このクラスのインスタンスをNetworkRegistry.instance().registerGuiHandler()の第二引数に渡すと登録できる.
 */
public class GuiHandler implements IGuiHandler {

	/*
	 * サーバー側でGUIが開かれたときに呼ばれる.
	 * 通常Containerを継承したクラスのインスタンスを返す.
	 */
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		/*
		 * 今回はIDが0の場合のみ.
		 */
		if (ID == 0) {
			return new ContainerBasic(player.inventory);
		}
		return null;
	}

	/*
	 * クライアント側で開かれたときに呼ばれる.
	 * 通常GuiScreen, GuiContainerを継承したクラスを返す.
	 */
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		/*
		 * 今回はIDが0の場合のみ.
		 */
		if (ID == 0) {
			return new GuiContainerBasic(player.inventory);
		}
		return null;
	}

}
