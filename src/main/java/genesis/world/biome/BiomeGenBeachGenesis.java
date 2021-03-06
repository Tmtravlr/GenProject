package genesis.world.biome;

import genesis.common.GenesisBlocks;
import genesis.metadata.EnumSilt;
import genesis.metadata.SiltBlocks;
import genesis.world.biome.decorate.WorldGenPebbles;

public class BiomeGenBeachGenesis extends BiomeGenBaseGenesis
{
	public BiomeGenBeachGenesis (int id)
	{
		super(id);
		setBiomeName("Beach");
		topBlock = GenesisBlocks.silt.getBlock(SiltBlocks.SILT, EnumSilt.SILT).getDefaultState();
		fillerBlock = GenesisBlocks.silt.getBlockState(SiltBlocks.SILTSTONE, EnumSilt.SILT);
		setHeight(0.05F, 0.1F);
		setHeight(height_Shores);
		
		addDecoration(new WorldGenPebbles().setCountPerChunk(25));
	}
}
