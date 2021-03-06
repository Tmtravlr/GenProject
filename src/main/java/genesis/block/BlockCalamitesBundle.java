package genesis.block;

import genesis.common.GenesisSounds;
import genesis.common.GenesisCreativeTabs;
import genesis.util.Constants.Unlocalized;
import net.minecraft.block.BlockHay;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class BlockCalamitesBundle extends BlockHay
{
	public BlockCalamitesBundle()
	{
		setStepSound(GenesisSounds.CALAMITES);
		setCreativeTab(GenesisCreativeTabs.BLOCK);

		setHardness(1);
		setHarvestLevel("axe", 0);

		Blocks.fire.setFireInfo(this, 30, 5);
	}
}
