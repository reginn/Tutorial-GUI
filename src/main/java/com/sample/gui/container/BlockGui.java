package com.sample.gui.container;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockGui extends Block {

	public BlockGui(int blockID, Material material) {
		super(blockID, material);
	}

	/*
	 * Blockを右クリックしたときに呼ばれるメソッド.
	 * 引数は
	 * (world, x, y, z, player, 右クリックされた面, 右クリックされた位置dx, 右クリックされた位置dy, 右クリックされた位置dz)
	 * dx, dy, dzはブロック上での位置(0.0~1.0)
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float dx, float dy, float dz) {

		/*
		 * サーバー側のみopenGuiメソッドを使ってGuiを呼ぶ.
		 * openGuiの引数でModで追加されるGUIを判定する.
		 */
		if (!world.isRemote)
		{
			/*
			 * 引数は(modのインスタンス, GUI ID, wold, x, y, z).
			 * GUI IDは”Mod内で重複しない数値”でよい.
			 */
			player.openGui(SampleGuiContainerCore.instance, 0, world, x, y, z);
		}

		/*
		 * trueを返すとブロック設置処理がキャンセルされる.
		 * falseだとブロックが(右クリックした面に)設置される.
		 */
		return true;
	}


}
