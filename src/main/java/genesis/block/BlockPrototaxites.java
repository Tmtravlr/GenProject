package genesis.block;

import genesis.client.GenesisClient;
import genesis.common.Genesis;
import genesis.common.GenesisBlocks;
import genesis.common.GenesisCreativeTabs;
import genesis.common.GenesisItems;
import genesis.util.BlockStateToMetadata;
import genesis.util.FlexibleStateMap;
import genesis.util.SidedFunction;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPrototaxites extends BlockGenesis
{
	public BlockPrototaxites()
	{
		super(Material.wood);

		setDefaultState(getBlockState().getBaseState());
		Genesis.proxy.callSided(new SidedFunction()
		{
			@Override
			@SideOnly(Side.CLIENT)
			public void client(GenesisClient client)
			{
				client.registerModelStateMap(BlockPrototaxites.this, new FlexibleStateMap().addIgnoredProperties(BlockCactus.AGE));
			}
		});

		setHardness(0.75F);
		setTickRandomly(true);
		setCreativeTab(GenesisCreativeTabs.DECORATIONS);
		setHarvestLevel("axe", 0);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return GenesisItems.prototaxites_flesh;
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, BlockCactus.AGE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return BlockStateToMetadata.getBlockStateFromMeta(getDefaultState(), meta);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((Integer) state.getValue(BlockCactus.AGE)).intValue();
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		BlockPos topBlock = pos.up();

		if (worldIn.isAirBlock(topBlock))
		{
			int size = 1;

			while (worldIn.getBlockState(pos.down(size)).getBlock() == this)
			{
				++size;
			}

			if (size < 8)
			{
				int age = ((Integer) state.getValue(BlockCactus.AGE)).intValue();

				if (age == 15)
				{
					worldIn.setBlockState(topBlock, getDefaultState());
					IBlockState ageReset = state.withProperty(BlockCactus.AGE, 0);
					worldIn.setBlockState(pos, ageReset, 4);
					onNeighborBlockChange(worldIn, topBlock, ageReset, this);
				}
				else if (rand.nextInt(4) > 0)
				{
					worldIn.setBlockState(pos, state.withProperty(BlockCactus.AGE, age + 1), 4);
				}
			}
		}
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return super.canPlaceBlockAt(worldIn, pos) && canBlockStay(worldIn, pos);
	}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		if (!canBlockStay(worldIn, pos))
		{
			worldIn.destroyBlock(pos, true);
		}
	}

	public boolean canBlockStay(World worldIn, BlockPos pos)
	{
		Block block = worldIn.getBlockState(pos.down()).getBlock();
		return (block == GenesisBlocks.prototaxites) || (block == GenesisBlocks.prototaxites_mycelium);
	}
}
