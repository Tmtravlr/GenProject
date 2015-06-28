package genesis.item;

import genesis.metadata.ToolItems;
import genesis.metadata.ToolItems.ToolObjectType;
import genesis.metadata.ToolTypes.ToolType;
import genesis.metadata.VariantsOfTypesCombo.ItemVariantCount;
import genesis.util.Constants.Unlocalized;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

@ItemVariantCount(1)
public class ItemGenesisAxe extends ItemAxe
{
	public final ToolItems owner;

	protected final ToolType type;
	protected final ToolObjectType objType;

	public ItemGenesisAxe(ToolType type, ToolItems owner, ToolObjectType objType)
	{
		super(type.toolMaterial);

		this.owner = owner;
		this.type = type;
		this.objType = objType;
	}

	@Override
	public int getMetadata(ItemStack stack)
	{
		return 0;
	}

	@Override
	public ItemGenesisAxe setUnlocalizedName(String unlocalizedName)
	{
		super.setUnlocalizedName(Unlocalized.TOOL + unlocalizedName);

		return this;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return owner.getUnlocalizedName(stack, super.getUnlocalizedName(stack));
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		super.addInformation(stack, playerIn, tooltip, advanced);
		owner.addToolInformation(stack, playerIn, tooltip, advanced);
	}
}
