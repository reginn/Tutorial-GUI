package com.sample.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/*
 * Containerを継承したContainerBasicクラスを作る.
 */
public class ContainerBasic extends Container
{
	public ContainerBasic(InventoryPlayer inventoryPlayer)
	{
		/*
		 * プレイヤーのインベントリをコンテナに設定する.
		 * 3*9の部分
		 */
		for (int slotCol = 0; slotCol < 3; ++slotCol)
		{
			for (int slotRow = 0; slotRow < 9; ++slotRow)
			{
				/*
				 * addSlotToContainer(Slot)
				 * Slotの引数は(IInventory, slotIndex, xDisplayPosition, yDisplayPosition)
				 * xDisplayPositonとyDisplayPositionはGUI背景でのスロットの左上の座標なので注意.
				 * base.pngを見ながら計算するとわかりやすい.
				 */
				this.addSlotToContainer(new Slot(inventoryPlayer, slotRow + slotCol * 9 + 9, 8 + slotRow * 18, 84 + slotCol * 18));
			}
		}

		/*
		 * 1*9の部分
		 */
		for (int slotRow = 0; slotRow < 9; ++slotRow)
		{
			this.addSlotToContainer(new Slot(inventoryPlayer, slotRow, 8 + slotRow * 18, 142));
		}
	}

	/*
	 * このコンテナを開けるかどうかを返すメソッド.
	 * 今回は常にtrueを返しているので一度開いたら勝手に閉じることはない.
	 * ワークベンチやチェストなどのソースを見るとちゃんとブロックや距離で判定している.
	 */
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

	/*
	 * Shift+左クリックしたときの処理を行うメソッド.
	 * 引数はPlayerとスロット番号(左上から0)
	 * 戻り値は引数の移動後のスロットのItemStack(全部移動したならnull, 同種のItemStackがあって、スタックサイズの変更だけなら変更したスタックサイズのItemStackを返す)
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex)
	{
		Slot slot = (Slot)this.inventorySlots.get(slotIndex);
		ItemStack srcItemStack = null;

		/*
		 * Shift+左クリックしたスロットに何かしらのアイテムがあった場合.
		 */
		if (slot != null && slot.getHasStack())
		{
			/*
			 * スロットからItemStackを取り出し, 移動先のdestItemStackに入れておく.
			 */
			ItemStack destItemStack = slot.getStack();
			srcItemStack = destItemStack.copy();

			/*
			 * slotIndex < 27 <=> 上の3*9インベントリ内なら, 下の1*9インベントリに移動.
			 * mergeItemStackの引数は(移動するItemStack, 移動先の最小スロット番号, 移動先の最大スロット番号, 昇順か降順か)
			 * すなわち, この場合は, destItemStackをスロット番号27～36の間の空いてるスロットに, 昇順(左詰)で移動させる.
			 * このとき, 同種のItemがあり, スタック数が最大でない場合は適切にマージされ, destItemStackのスタックサイズが変化する.
			 */
			if (slotIndex < 27 && !this.mergeItemStack(destItemStack, 27, 36, false))
			{
				return null;
			}

			/*
			 * slotIndex >= 27 <=> 下の1*9インベントリ内なら, 上の3*9インベントリに移動.
			 */
			if (slotIndex >= 27 && !this.mergeItemStack(destItemStack, 0, 27, false))
			{
				return null;
			}

			/*
			 * 移動後に, スタック数がゼロならスロットを空にする.
			 */
			if (destItemStack.stackSize == 0)
			{
				slot.putStack((ItemStack)null);
			}
			else
			{
				/*
				 * そうでないならスロットに変更を通知する.
				 */
				slot.onSlotChanged();
			}
		}

		return srcItemStack;
	}
}
