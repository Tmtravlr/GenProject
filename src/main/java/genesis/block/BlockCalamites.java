package genesis.block;

import genesis.block.BlockGrowingPlant.GrowingPlantProperties;
import genesis.block.BlockGrowingPlant.IGrowingPlantCustoms;
import genesis.block.BlockGrowingPlant.IGrowingPlantCustoms.CanStayOptions;
import genesis.common.Genesis;
import genesis.common.GenesisItems;
import genesis.util.WorldUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCalamites extends BlockGrowingPlant implements IGrowingPlantCustoms
{
	public BlockCalamites(boolean topPropertyIn, int maxAgeIn, int height)
	{
		super(topPropertyIn, maxAgeIn, height);
		
		BlockCalamitesModel.register();
	}

	@Override
	public ArrayList<ItemStack> getDrops(BlockGrowingPlant plant, World worldIn, BlockPos pos, IBlockState state, int fortune, boolean firstBlock)
	{
		return null;
	}

	@Override
	public void updateTick(BlockGrowingPlant plant, World worldIn, BlockPos pos, IBlockState state, Random rand, boolean grew)
	{
	}

	@Override
	public CanStayOptions canStayAt(BlockGrowingPlant plant, World worldIn, BlockPos pos, boolean placed)
	{
		if (placed && worldIn.getBlockState(pos.down()).getBlock() == plant)
		{
			return CanStayOptions.YES;
		}
		else if (WorldUtils.waterInRange(worldIn, pos, 2, 1))
		{
			final ArrayList<Block> plantableOn = new ArrayList<Block>(){{
				add(Blocks.grass);
				add(Blocks.dirt);
				add(Blocks.sand);
			}};
			
			if (plantableOn.contains(worldIn.getBlockState(pos.down()).getBlock()))
			{
				return CanStayOptions.YES;
			}
		}
		
		return CanStayOptions.NO;
	}
}
