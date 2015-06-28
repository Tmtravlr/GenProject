package genesis.world.biome;

import genesis.world.biome.decorate.BiomeDecoratorGenesis;

public class BiomeGenRainforestEdge extends BiomeGenRainforest
{

	public BiomeGenRainforestEdge(int id)
	{
		super(id);
		theBiomeDecorator.treesPerChunk = 0;
		biomeName = "Rainforest Edge";
		((BiomeDecoratorGenesis) theBiomeDecorator).lepidodendtronPerChunk = 10;
	}

}
