package genesis.world.biome;

public class BiomeGenShallowOcean extends BiomeGenBaseGenesis
{

	public BiomeGenShallowOcean(int id)
	{
		super(id);
		biomeName = "Shallow Ocean";
		minHeight = -.8F;
		maxHeight = 0.1F;
		waterColorMultiplier = 0x008d49;
	}

}
