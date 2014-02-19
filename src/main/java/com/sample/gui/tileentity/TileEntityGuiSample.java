package com.sample.gui.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

public class TileEntityGuiSample extends TileEntity implements IInventory
{
	private ItemStack[] items = new ItemStack[9];

	// NBTの実装
	//---------------------------------------------------------------------------------------
	/*
	 * NBT(Named By Tag)の読み込み.
	 * TileEntityやEntity, ItemStackのように実行中にインスタンスを生成するようなクラスはフィールドを別途保存しておく必要がある.
	 * そのためにNBTという形式で保存/読み込みをしている.
	 */

	/*
	 * フィールドをNBTから読み込むメソッド.
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound)
	{
		super.readFromNBT(nbtTagCompound);

		/*
		 * NBTTagCompoundから"Items"タグがついたNBTTagListを取り出す.
		 * 1.7からNBTTypeが引数に必要になった.
		 * 引数とNBTTypeの対応は以下の通り.
		 * 0 => "END",    1 => "BYTE",   2 => "SHORT",     3 => "INT",
		 * 4 => "LONG",   5 => "FLOAT",  6 => "DOUBLE",    7 => "BYTE[]",
		 * 8 => "STRING", 9 => "LIST",  10 => "COMPOUND", 11 => "INT[]"
		 *
		 * 今回はNBTCompoundなので10を渡す.
		 * net.minecraftforge.common.util.Constantsに各NBTごとの定数が用意されているので利用する.
		 */
		NBTTagList itemsTagList = nbtTagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);

		this.items = new ItemStack[this.getSizeInventory()];

		/*
		 * "Items"タグがついかNBTTagListから, "Slot"タグのものを順次取り出す.
		 */
		for (int tagCounter = 0; tagCounter < itemsTagList.tagCount(); ++tagCounter)
		{
			NBTTagCompound itemTagCompound = (NBTTagCompound)itemsTagList.getCompoundTagAt(tagCounter);

			/*
			 * byteなので容量の節約のため.
			 * スロット番号は今回0~9なので, intやshortでは大きすぎる.
			 */
			byte slotIndex = itemTagCompound.getByte("Slot");

			if (slotIndex >= 0 && slotIndex < this.items.length)
			{
				/*
				 * NBTTagCompoundからItemStackのインスタンスを得るメソッドを利用する.
				 */
				this.items[slotIndex] = ItemStack.loadItemStackFromNBT(itemTagCompound);
			}
		}
	}

	/*
	 * フィールドの保存のためにNBTに変換するメソッド.
	 */
	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound)
	{
		super.writeToNBT(nbtTagCompound);
		NBTTagList itemsTagList = new NBTTagList();

		for (int slotIndex = 0; slotIndex < this.items.length; ++slotIndex)
		{
			/*
			 * ItemStackがあるスロットのデータだけ保存する.
			 */
			if (this.items[slotIndex] != null)
			{
				NBTTagCompound itemTagCompound = new NBTTagCompound();

				/*
				 * itemTagCompoundにスロット番号とItemStackの情報を書き込む.
				 */
				itemTagCompound.setByte("Slot", (byte)slotIndex);
				this.items[slotIndex].writeToNBT(itemTagCompound);

				/*
				 * itemTagListにitemTagCompoundを追加する.
				 */
				itemsTagList.appendTag(itemTagCompound);
			}
		}

		/*
		 * NBTTagListに変換された情報を引数のNBTTagCompoundにまとめて渡す.
		 */
		nbtTagCompound.setTag("Items", itemsTagList);
	}


	//IInventoryの実装
	//---------------------------------------------------------------------------------------
	/*
	 * IInventoryはインベントリ機能を提供するインタフェース.
	 * インベントリに必要なメソッドを適切にオーバーライドする.
	 */

	/*
	 * Inventoryの要素数を返すメソッド.
	 */
	@Override
	public int getSizeInventory()
	{
		return 9;
	}

	/*
	 * スロットの中身を返すメソッド.
	 * 引数はスロット番号
	 */
	@Override
	public ItemStack getStackInSlot(int slotIndex)
	{
		if (slotIndex >= 0 && slotIndex < this.items.length)
		{
			return this.items[slotIndex];
		}
		return null;
	}

	/*
	 * スロットの中身のスタックサイズを変更するメソッド.
	 * (メソッド名はおそらくdecrement stack size)
	 * 引数は(スロット番号, 分割するスタック数)
	 * 戻り値は分割後のItemStack
	 */
	@Override
	public ItemStack decrStackSize(int slotIndex, int splitStackSize)
	{
		if (this.items[slotIndex] != null)
		{
			/*
			 * 引数のスタック数より現在のスタック数が少ない場合は移動になる.
			 */
			if (this.items[slotIndex].stackSize <= splitStackSize)
			{
				ItemStack tmpItemStack = items[slotIndex];
				this.items[slotIndex] = null;

				/*
				 * スロットの変更を通知.
				 */
				this.markDirty();

				return tmpItemStack;
			}

			/*
			 * 引数のスタック数より現在のスタック数が多い場合は分割する.
			 */
			ItemStack splittedItemStack = this.items[slotIndex].splitStack(splitStackSize);

			/*
			 * 分割後にスタック数が0になった場合は空にする.
			 */
			if (this.items[slotIndex].stackSize == 0)
			{
				this.items[slotIndex] = null;
			}

			this.markDirty();

			return splittedItemStack;
		}
		return null;
	}

	/*
	 * コンテナが閉じられたときに, スロットにアイテムを保持できないタイプ(エンチャント台やワークベンチなど)の場合,
	 * 引数のスロットのItemStackを返すメソッド.
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(int slotIndex)
	{
		return this.items[slotIndex];
	}

	/*
	 * InventoryにItemStackを入れるメソッド.
	 * 引数は(スロット番号, そのスロットに入れるItemStack)
	 */
	@Override
	public void setInventorySlotContents(int slotIndex, ItemStack itemStack)
	{
		this.items[slotIndex] = itemStack;

		if (itemStack != null && itemStack.stackSize > getInventoryStackLimit())
		{
			/*
			 * スロットのスタック数制限を超えている場合, 制限を課す.
			 */
			itemStack.stackSize = getInventoryStackLimit();
		}
		this.markDirty();
	}

	/*
 	 * Inventoryの名前
 	 * (ここではunlocalizedNameだが, 金床で名前をつける場合の処理はやや異なるので, かまどやチェストのコードを参考のこと)
 	 */
	@Override
	public String getInventoryName()
	{
		return "container.tileEntityGuiSample";
	}

	/*
	 * 金床で名前をつけたかどうか
	 * 今回はつけないのでfalse
	 */
	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	/*
	 * 1スロットあたりの最大スタック数
	 */
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	/*
	 * 主にContainerで利用する, GUIを開けるかどうかを判定するメソッド.
	 */
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		/*
		 * この座標にあるTileEntityがこのクラスでないならfalse
		 */
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this)
		{
			return false;
		}

		/*
		 * その場合でも, プレイヤーとブロックの距離が離れすぎている場合もfalse
		 */
		return entityplayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
	}

	@Override
	public void openInventory()
	{
		/*
		 *  今回は利用しない.
		 *  利用する場合は, Containerのコンストラクタでこのメソッドを呼ぶ必要がある.
		 */
	}

	@Override
	public void closeInventory()
	{
		/*
		 *  今回は利用しない.
		 *  利用する場合は, ContainerのonContainerClosedメソッドでこのメソッドを呼ぶ必要がある.
		 */
	}

	/*
	 * trueではHopperでアイテムを送れるようになる.
	 */
	@Override
	public boolean isItemValidForSlot(int slotIndex, ItemStack itemstack)
	{
		return true;
	}
}
