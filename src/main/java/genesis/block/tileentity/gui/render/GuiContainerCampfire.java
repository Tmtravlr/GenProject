package genesis.block.tileentity.gui.render;

import genesis.block.tileentity.*;
import genesis.block.tileentity.gui.*;
import genesis.util.Constants;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.util.ResourceLocation;

public class GuiContainerCampfire extends GuiContainerBase
{
	public static final ResourceLocation CAMPFIRE_TEX = new ResourceLocation(Constants.ASSETS_PREFIX + "textures/gui/campfire.png");
	
	protected final TileEntityCampfire campfire;
	protected final ContainerCampfire containerCampfire = (ContainerCampfire) container;
	
	public GuiContainerCampfire(EntityPlayer player, TileEntityCampfire te)
	{
		super(new ContainerCampfire(player, te), te);
		
		campfire = te;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTick, mouseX, mouseY);
		
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(CAMPFIRE_TEX);
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(guiLeft, guiTop, 0);
		
		// BURNER PROGRESS
		// Render burner background.
		final int burnerW = 14;
		final int burnerH = 14;
		drawTexBetweenSlots(0, 0, burnerW, burnerH,
							0, 0, burnerW, burnerH,
							containerCampfire.input, containerCampfire.fuel);
		// Render burner fire or water overlay.
		int burnerU = campfire.isWet() ? burnerW * 2 : burnerW;
		
		final int burnH = campfire.getBurnTimeLeftScaled(burnerH - 1);
		final int burnY = burnerH - burnH;
		drawTexBetweenSlots(0, burnY / 2, burnerW, burnH,
							burnerU, burnY, burnerW, burnH,
							containerCampfire.input, containerCampfire.fuel);
		
		// COOKING PROGRESS
		// Render cook progress bar background.
		final Slot[] allSlots = {containerCampfire.ingredient1, containerCampfire.ingredient2, containerCampfire.input, containerCampfire.fuel, containerCampfire.output};
		final int cookerW = 22;
		final int cookerH = 16;
		drawTexBetweenSlots(0, 0, cookerW, cookerH,
							0, burnerH, cookerW, cookerH,
							allSlots);
		// Render cook progress bar overlay.
		final int cookW = campfire.getCookProgressScaled(cookerW);
		drawTexBetweenSlots(-cookerW / 2, 0, cookW, cookerH,
							cookerW, burnerH, cookW, cookerH,
							false, true,
							allSlots);
		
		GlStateManager.popMatrix();
	}
}
