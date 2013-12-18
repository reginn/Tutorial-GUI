package com.sample.gui.container;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

/*
 * 今回はContainerを使うのでGuiContainerを継承する.
 */
public class GuiContainerBasic extends GuiContainer {

	/*
	 * GUIの背景の画像.
	 * ResourceLocationの引数はsetTextureNameやregisterIconとほぼ同等.
	 * domain:pathで構成されるが, ".png"を忘れずに.
	 * またdomainに"/"を含む文字列は使えないので注意.
	 * static final推奨.
	 */
	private static final ResourceLocation GUI_BACKGROUND = new ResourceLocation("gui:textures/gui/container/base.png");

	/*
	 * コンストラクタ
	 * スーパークラスのコンストラクタの引数はContainerな点に注意.
	 */
	public GuiContainerBasic(InventoryPlayer inventoryPlayer) {
		super(new ContainerBasic(inventoryPlayer));

		/*
		 * GUIの背景リソース(gui:textures/gui/container/base.png)内の画像サイズ(base.pngを開いて確認すること)
		 */
		this.xSize = 176;
		this.ySize = 166;
	}

	/*
	 * Guiに文字を書いたりするメソッド.
	 * 引数は画面の左上からのマウスカーソルの座標(x, y)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int xMouse, int yMouse)
	{
		/*
		 * StatCollector.translateToLocal()はキーを引数に現在の設定言語の文字列を返すメソッド.
		 * 英語なら"Inventory"で, 日本語なら"インベントリ"を返す.
		 * 基本はLanguageRegistryと同等.
		 *
		 * drawStringの引数は(表示文字列, GUIの左上からx, GUIの左上からy, 文字列の色(RBG)).
		 */
		this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 0x404040);
	}

	/*
	 * Guiの背景を描画するメソッド.
	 * 第一引数は描画のtick, 第二, 三引数はマウスカーソルの座標(x, y)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float renderPartialTicks, int xMouse, int yMouse) {

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		/*
		 * 読み込んだResourceLocationをbindTextureに渡す.
		 */
		this.mc.getTextureManager().bindTexture(GUI_BACKGROUND);

		/*
		 * マイクラの画面サイズ(width, height)と, GUIサイズ(xSize, ySize)からGUIの左上座標を計算し, そこから描画する.
		 */
		int x = (this.width  - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		/*
		 * bindされたテクスチャを四角形で描画する.
		 * (x, y)を左上始点として, リソース内の(0, 0) ~ (xSize, ySize)までを描画する.
		 */
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

	}
}
