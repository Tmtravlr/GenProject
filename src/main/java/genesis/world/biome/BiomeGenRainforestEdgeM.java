package genesis.world.biome;

import genesis.world.biome.decorate.BiomeDecoratorGenesis;

public class BiomeGenRainforestEdgeM extends BiomeGenRainforestEdge
{

	public BiomeGenRainforestEdgeM(int id)
	{
		super(id);
		biomeName = "Rainforest Edge M";
		minHeight = 0.7F;
		maxHeight = 1.5F;
		theBiomeDecorator.treesPerChunk = 0;
		((BiomeDecoratorGenesis) theBiomeDecorator).lepidodendtronPerChunk = 5;
	}

}
