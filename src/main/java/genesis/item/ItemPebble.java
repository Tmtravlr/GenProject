package genesis.item;

import genesis.metadata.ToolItems;
import genesis.metadata.ToolItems.ToolObjectType;
import genesis.metadata.ToolTypes.ToolType;
import genesis.metadata.VariantsOfTypesCombo.ItemVariantCount;
import genesis.util.Constants.Unlocalized;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

@ItemVariantCount(1)
public class ItemPebble extends ItemGenesis
{
	public final ToolItems owner;

	protected final ToolType toolType;
	protected final ToolObjectType<?> objType;

	public ItemPebble(ToolType toolType, ToolItems owner, ToolObjectType<?> type)
	{
		super();

		this.owner = owner;

		this.toolType = toolType;
		objType = type;

		setMaxDamage(toolType.toolMaterial.getMaxUses());
		setMaxStackSize(1);
	}

	@Override
	public int getMetadata(ItemStack stack)
	{
		return 0;
	}

	@Override
	public Item setUnlocalizedName(String unlocalizedName)
	{
		return super.setUnlocalizedName(Unlocalized.MATERIAL + unlocalizedName);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return owner.getUnlocalizedName(stack, super.getUnlocalizedName(stack));
	}

	public ToolType getToolType()
	{
		return toolType;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		IBlockState state = world.getBlockState(pos);

		if (state.getBlock().getMaterial() == Material.rock && state.getBlock().getBlockHardness(world, pos) >= 1)
		{
			player.swingItem();
			stack.setItemDamage(stack.getItemDamage() + 10);

			// If the pebble was destroyed
			if (stack.getItemDamage() > stack.getMaxDamage() || player.capabilities.isCreativeMode)
			{
				ItemStack choppingTool = owner.getStack(ToolItems.CHOPPING_TOOL, toolType.material);
				stack.stackSize--;		// Remove an item from the stack
				stack.setItemDamage(0);	// Set the stack damage to 0

				if (stack.stackSize <= 0)	// Remove the stack from the inventory if it's an empty stack.
				{
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
				}

				if (!player.inventory.addItemStackToInventory(choppingTool.copy()))
				{	// Add to inventory or drop as EntityItem.
					player.dropPlayerItemWithRandomChoice(choppingTool, false);
				}
			}

			return true;
		}

		return false;
	}
}
