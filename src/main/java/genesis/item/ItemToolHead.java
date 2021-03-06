package genesis.item;

import genesis.common.GenesisCreativeTabs;
import genesis.item.ItemGenesis;
import genesis.metadata.ToolItems.*;
import genesis.metadata.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ItemToolHead extends ItemGenesis
{
	protected final List<IMetadata> variants;
	public final ToolItems owner;
	public final ToolObjectType type;
	
	public ItemToolHead(List<IMetadata> variants, ToolItems owner, ToolObjectType type)
	{
		super();

		this.owner = owner;
		this.type = type;
		this.variants = variants;

		setHasSubtypes(true);
		setCreativeTab(GenesisCreativeTabs.TOOLS);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return owner.getUnlocalizedName(stack, super.getUnlocalizedName(stack));
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List subItems)
	{
		for (int i = 0; i < variants.size(); i++)
		{
			ItemStack stack = new ItemStack(itemIn, 1, i);
			subItems.add(stack);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		super.addInformation(stack, playerIn, tooltip, advanced);
		owner.addToolInformation(stack, playerIn, tooltip, advanced);
	}
}
