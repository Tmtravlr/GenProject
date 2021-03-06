package genesis.util.render;

import net.minecraft.client.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;

public class ItemAsEntityPart extends CustomEntityPart
{
	public ItemStack itemStack;
	public ItemStack itemStackDef;
	
	public ItemAsEntityPart(ModelBase model)
	{
		super(model);
		
		offsetX += 0.5F;
		offsetZ += 0.5F;
		setDefaultState();
	}
	
	public ItemAsEntityPart setDefaultState()
	{
		super.setDefaultState();

		itemStackDef = itemStack;
		
		return this;
	}
	
	public void resetState()
	{
		super.resetState();
		
		itemStack = itemStackDef;
	}
	
	public void setStack(ItemStack stack)
	{
		itemStack = stack;
	}
	
	@Override
	public void doRender(float pxSize)
	{
		if (itemStack != null)
		{
			float scale = pxSize * 16;
			GlStateManager.scale(scale, scale, scale);
			Minecraft.getMinecraft().getRenderItem().renderItemModel(itemStack);
		}
	}
}
