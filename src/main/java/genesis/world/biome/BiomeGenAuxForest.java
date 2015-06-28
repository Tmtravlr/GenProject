package genesis.world.biome;

public class BiomeGenAuxForest extends BiomeGenBaseGenesis
{

	public BiomeGenAuxForest(int id)
	{
		super(id);
		theBiomeDecorator.treesPerChunk = 5;
		theBiomeDecorator.grassPerChunk = 0;
		biomeName = "Araucarioxylon Forest";
	}

}
