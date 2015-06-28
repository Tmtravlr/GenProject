package genesis.metadata;

import genesis.block.BlockGenesisVariants;
import genesis.client.GenesisSounds;
import genesis.common.GenesisCreativeTabs;
import genesis.item.ItemBlockMulti;
import genesis.item.ItemDung;
import genesis.metadata.VariantsOfTypesCombo.ObjectType;
import genesis.util.FuelHandler;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class DungBlocksAndItems extends VariantsOfTypesCombo<ObjectType, EnumDung>
{
	public static final ObjectType<BlockGenesisVariants, ItemBlockMulti> DUNG_BLOCK =
			new ObjectType<BlockGenesisVariants, ItemBlockMulti>("dung_block", "dung", BlockGenesisVariants.class, null)
			{
		@Override
		public void afterConstructed(BlockGenesisVariants block, ItemBlockMulti item, List<IMetadata> variants)
		{
			super.afterConstructed(block, item, variants);

			BlockGenesisVariants dung = block;

			dung.setHardness(0.5F);
			dung.setStepSound(GenesisSounds.DUNG);

			dung.clearDrops();
			dung.addDrop(DungBlocksAndItems.DUNG, 4, 4);

			Blocks.fire.setFireInfo(dung, 5, 5);

			FuelHandler.setBurnTime(item,
					TileEntityFurnace.getItemBurnTime(new ItemStack(Blocks.log)) * 4, true);
		}
			}.setUseSeparateVariantJsons(false).setNamePosition(ObjectNamePosition.PREFIX).setBlockArguments(Material.ground);
			public static final ObjectType<Block, ItemDung> DUNG = new ObjectType<Block, ItemDung>("dung", null, ItemDung.class)
					{
				@Override
				public void afterConstructed(Block block, ItemDung item, List<IMetadata> variants)
				{
					super.afterConstructed(block, item, variants);
					FuelHandler.setBurnTime(item, TileEntityFurnace.getItemBurnTime(new ItemStack(Blocks.log)), true);
				}
					}
			.setCreativeTab(GenesisCreativeTabs.MATERIALS).setNamePosition(ObjectNamePosition.PREFIX);

			public DungBlocksAndItems()
			{
				super(new ObjectType[] { DUNG_BLOCK, DUNG }, EnumDung.values());
			}
}
