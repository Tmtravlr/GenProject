package genesis.metadata;

import genesis.block.*;
import genesis.common.GenesisCreativeTabs;
import genesis.item.*;
import genesis.util.*;
import genesis.util.Constants.Unlocalized;
import genesis.metadata.VariantsOfTypesCombo.*;

import java.util.*;

import net.minecraft.block.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntityFurnace;

@SuppressWarnings("rawtypes")
public class TreeBlocksAndItems extends VariantsOfTypesCombo<ObjectType, EnumTree>
{
	public static final ObjectType<BlockGenesisLogs, ItemBlockMulti> LOG = new ObjectType<BlockGenesisLogs, ItemBlockMulti>("log", BlockGenesisLogs.class, null);
	public static final ObjectType<BlockGenesisSaplings, ItemBlockMulti> SAPLING = new ObjectType<BlockGenesisSaplings, ItemBlockMulti>("sapling", BlockGenesisSaplings.class, null)
			{
				@Override
				public void afterConstructed(BlockGenesisSaplings block, ItemBlockMulti item, List<? extends IMetadata> variants)
				{
					super.afterConstructed(block, item, variants);
					FuelHandler.setBurnTime(item, TileEntityFurnace.getItemBurnTime(new ItemStack(Blocks.sapling)), true);
				}
			}
			.setIgnoredProperties(BlockSapling.STAGE);
	public static final ObjectType<BlockGenesisLeaves, ItemBlockMulti> LEAVES = new ObjectType<BlockGenesisLeaves, ItemBlockMulti>("leaves", BlockGenesisLeaves.class, null)
			.setIgnoredProperties(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE);
	public static final ObjectType<Block, ItemMulti> BILLET = new ObjectType<Block, ItemMulti>("billet", Unlocalized.Section.MATERIAL + "billet", null, ItemMulti.class, EnumTree.NO_BILLET)
			{
				@Override
				public void afterConstructed(Block block, ItemMulti item, List<? extends IMetadata> variants)
				{
					super.afterConstructed(block, item, variants);
					FuelHandler.setBurnTime(item, TileEntityFurnace.getItemBurnTime(new ItemStack(Blocks.planks)), true);
				}
			};
	public static final ObjectType<BlockWattleFence, ItemBlockMulti> WATTLE_FENCE = new ObjectType<BlockWattleFence, ItemBlockMulti>("wattle_fence", "wattleFence", BlockWattleFence.class, null, EnumTree.NO_BILLET);
	public static final ObjectType<BlockGenesisRottenLogs, ItemBlockMulti> ROTTEN_LOG = new ObjectType<BlockGenesisRottenLogs, ItemBlockMulti>("rotten_log", "log.rotten", BlockGenesisRottenLogs.class, null, EnumTree.PSARONIUS)
			.setCreativeTab(GenesisCreativeTabs.DECORATIONS);
	
	public TreeBlocksAndItems()
	{
		super(new ObjectType[]{LOG, SAPLING, LEAVES, BILLET, WATTLE_FENCE, ROTTEN_LOG}, EnumTree.values());

		setUnlocalizedPrefix(Constants.Unlocalized.PREFIX);
	}
}
