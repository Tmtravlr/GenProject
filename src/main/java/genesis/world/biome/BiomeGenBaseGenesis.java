package genesis.world.biome;

import genesis.block.BlockMoss;
import genesis.common.GenesisBlocks;
import genesis.world.biome.decorate.BiomeDecoratorGenesis;
import net.minecraft.block.BlockGrass;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class BiomeGenBaseGenesis extends BiomeGenBase
{

	public BiomeGenBaseGenesis(int id)
	{
		super(id);
		theBiomeDecorator = new BiomeDecoratorGenesis();
		theBiomeDecorator.clayPerChunk = 1;
		topBlock = GenesisBlocks.moss.getDefaultState().withProperty(BlockMoss.STAGE, BlockMoss.STAGE_LAST).withProperty(BlockGrass.SNOWY, false);
		spawnableCaveCreatureList.clear();
		spawnableCreatureList.clear();
		spawnableMonsterList.clear();
		spawnableWaterCreatureList.clear();
		waterColorMultiplier = 0xaa791e;
	}

	@Override
	public int getFoliageColorAtPos(BlockPos p_180625_1_)
	{
		return 0x8DB360;
	}

}
