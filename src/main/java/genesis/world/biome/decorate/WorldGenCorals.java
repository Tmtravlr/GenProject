package genesis.world.biome.decorate;

import genesis.common.GenesisBlocks;
import genesis.metadata.EnumCoral;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenCorals extends WorldGenDecorationBase
{
	private EnumCoral coralType;
	private int minHeight;
	private int maxHeight;
	public int rarity = 1;
	
	public WorldGenCorals(int minHeight, int maxHeight, EnumCoral coralType)
	{
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.coralType = coralType;
	}
	
	public WorldGenCorals setRarity(int r)
	{
		rarity = r;
		return this;
	}
	
	@Override
	public boolean generate(World world, Random random, BlockPos pos)
	{
		Block block;
		
		if (random.nextInt(rarity) != 0)
			return false;
		
		do
		{
			block = world.getBlockState(pos).getBlock();
			if (!block.isAir(world, pos) && !(block == Blocks.water))
			{
				break;
			}
			pos = pos.down();
		}
		while (pos.getY() > 0);
		
		if (coralType == null)
			coralType = EnumCoral.FAVOSITES;
		
		int height = minHeight + random.nextInt(maxHeight);
		
		IBlockState coralBlock = GenesisBlocks.corals.getBlockState(coralType);
		
		if (random.nextInt(2) == 0)
		{
			if (!placeCoralFormation(world, pos, random, coralBlock, height))
				return false;
			
			BlockPos additionalPos;
			
			for (int i = 0; i < random.nextInt(5); ++i)
			{
				additionalPos = pos.add(random.nextInt(7) - 3, random.nextInt(3) - 1, random.nextInt(7) - 3);
				placeCoralFormation(world, additionalPos, random, coralBlock, height);
			}
		}
		else
		{
			if (!placeCoralFormationHorizontal(world, pos, coralBlock, random, height))
				return false;
		}
		
		return true;
	}
	
	private boolean placeCoralFormation(World world, BlockPos pos, Random random, IBlockState coralBlock, int height)
	{
		if (!isPositionSuitable(world, pos, height))
			return false;
		
		placeCoralColumn(world, pos, coralBlock, height);
		
		if (random.nextInt(2) == 0)
			placeCoralColumn(world, pos.north(), coralBlock, 1 + random.nextInt(height - 1));
		if (random.nextInt(2) == 0)
			placeCoralColumn(world, pos.south(), coralBlock, 1 + random.nextInt(height - 1));
		if (random.nextInt(2) == 0)
			placeCoralColumn(world, pos.east(), coralBlock, 1 + random.nextInt(height - 1));
		if (random.nextInt(2) == 0)
			placeCoralColumn(world, pos.west(), coralBlock, 1 + random.nextInt(height - 1));
		
		return true;
	}
	
	private void placeCoralColumn(World world, BlockPos pos, IBlockState coralBlock, int height)
	{
		for (int i = 0; i < height; ++i)
			setBlockInWorld(world, pos.add(0, i, 0), coralBlock, true);
	}
	
	private boolean placeCoralFormationHorizontal(World world, BlockPos pos, IBlockState coralBlock, Random random, int length)
	{
		if (!isPositionSuitable(world, pos, 5))
			return false;
		
		boolean placedSome = false;
		
		if (random.nextInt(10) < 7)
		{
			placeCoralBranch(world, pos, coralBlock, random, length, 1, 0);
			placedSome = true;
		}
		
		if (random.nextInt(10) < 7)
		{
			placeCoralBranch(world, pos, coralBlock, random, length, -1, 0);
			placedSome = true;
		}
		
		if (random.nextInt(10) < 7)
		{
			placeCoralBranch(world, pos, coralBlock, random, length, 0, 1);
			placedSome = true;
		}
		
		if (random.nextInt(10) < 7)
		{
			placeCoralBranch(world, pos, coralBlock, random, length, 0, -1);
			placedSome = true;
		}
		
		return placedSome;
	}
	
	private void placeCoralBranch(World world, BlockPos pos, IBlockState coralBlock, Random random, int length, int dirX, int dirZ)
	{
		BlockPos placePos = pos;
		Block block;
		boolean placeTop = true;
		
		for (int i = 1; i <= length; ++i)
		{
			block = world.getBlockState(placePos).getBlock();
			
			if (
					!(block == Blocks.dirt
					|| block == Blocks.clay
					|| block == GenesisBlocks.ooze
					|| block == coralBlock.getBlock()))
			{
				break;
			}
			
			setBlockInWorld(world, placePos, coralBlock, true);
			setBlockInWorld(world, placePos.north(), coralBlock, true);
			setBlockInWorld(world, placePos.south(), coralBlock, true);
			setBlockInWorld(world, placePos.east(), coralBlock, true);
			setBlockInWorld(world, placePos.west(), coralBlock, true);
			
			if (placeTop)
				setBlockInWorld(world, placePos.up(), coralBlock, true);
			
			if (random.nextInt(3) == 0)
				placeTop = false;
			
			placePos = placePos.add(1 * dirX, 0, 1 * dirZ);
		}
	}
	
	private boolean isPositionSuitable(World world, BlockPos pos, int height)
	{
		Block block = world.getBlockState(pos).getBlock();
		
		if (
				block == Blocks.water
				|| block.isAir(world, pos))
			return false;
		
		for (int i = 1; i <= height + 2; ++i)
		{
			if (world.getBlockState(pos.add(0, i, 0)).getBlock() != Blocks.water)
			{
				return false;
			}
		}
		
		return true;
	}
}
