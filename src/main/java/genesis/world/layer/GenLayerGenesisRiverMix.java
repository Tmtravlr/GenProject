package genesis.world.layer;

import genesis.common.GenesisBiomes;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerGenesisRiverMix extends GenLayerGenesis
{

	private GenLayer biomePatternGeneratorChain;
	private GenLayer riverPatternGeneratorChain;

	public GenLayerGenesisRiverMix(long seed, GenLayer biomeLayer, GenLayer riverLayer)
	{
		super(seed);
		biomePatternGeneratorChain = biomeLayer;
		riverPatternGeneratorChain = riverLayer;
	}

	/**
	 * Initialize layer's local worldGenSeed based on its own baseSeed and the world's global seed (passed in as an argument).
	 */
	@Override
	public void initWorldGenSeed(long seed)
	{
		biomePatternGeneratorChain.initWorldGenSeed(seed);
		riverPatternGeneratorChain.initWorldGenSeed(seed);
		super.initWorldGenSeed(seed);
	}

	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
	{
		int[] aint = biomePatternGeneratorChain.getInts(areaX, areaY, areaWidth, areaHeight);
		int[] aint1 = riverPatternGeneratorChain.getInts(areaX, areaY, areaWidth, areaHeight);
		int[] aint2 = IntCache.getIntCache(areaWidth * areaHeight);

		for (int i1 = 0; i1 < areaWidth * areaHeight; ++i1)
		{
			if (aint[i1] != BiomeGenBase.ocean.biomeID && aint[i1] != BiomeGenBase.deepOcean.biomeID)
			{
				if (aint1[i1] == GenesisBiomes.river.biomeID)
				{
					aint2[i1] = aint1[i1] & 255;
				}
				else
				{
					aint2[i1] = aint[i1];
				}
			}
			else
			{
				aint2[i1] = aint[i1];
			}
		}
		return aint2;
	}

}
