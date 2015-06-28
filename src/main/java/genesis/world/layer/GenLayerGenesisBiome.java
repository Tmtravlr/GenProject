package genesis.world.layer;

import static net.minecraftforge.common.BiomeManager.BiomeType.WARM;

import genesis.world.biome.BiomeManagerGenesis;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class GenLayerGenesisBiome extends GenLayerGenesis
{

	private final List<BiomeEntry>[] allowedBiomes;

	public GenLayerGenesisBiome(long seed, GenLayer parentGenLayer)
	{
		super(seed);
		List<BiomeEntry>[] biomes = new ArrayList[BiomeType.values().length];
		for (BiomeType type : BiomeType.values())
		{
			biomes[type.ordinal()] = BiomeManagerGenesis.getEntries(type);
		}
		allowedBiomes = biomes;
		parent = parentGenLayer;
	}

	/*
	 * @Override
	 * public int[] getInts(int par1, int par2, int par3, int par4)
	 * {
	 * int[] aint1 = IntCache.getIntCache(par3 * par4);
	 *
	 * for (int i1 = 0; i1 < par4; ++i1)
	 * for (int j1 = 0; j1 < par3; ++j1)
	 * {
	 * initChunkSeed(j1 + par1, i1 + par2);
	 * BiomeGenBase biome = allowedBiomes[nextInt(allowedBiomes.length)];
	 * aint1[j1 + i1 * par3] = biome.biomeID;
	 * }
	 * return aint1;
	 * }
	 */

	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
	{
		int[] aint = parent.getInts(areaX, areaY, areaWidth, areaHeight);
		int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);

		for (int i1 = 0; i1 < areaHeight; ++i1)
		{
			for (int j1 = 0; j1 < areaWidth; ++j1)
			{
				initChunkSeed(j1 + areaX, i1 + areaY);
				int k1 = aint[j1 + i1 * areaWidth];
				int l1 = (k1 & 3840) >> 8;
			k1 &= -3841;

			if (isBiomeOceanic(k1))
			{
				aint1[j1 + i1 * areaWidth] = k1;
			}
			else
			{
				aint1[j1 + i1 * areaWidth] = getWeightedBiomeEntry(WARM).biome.biomeID;
			}
			/*
			 * else if (k1 == 1)
			 * {
			 * //Should be DESERT, but we don't have any desert biomes
			 * aint1[j1 + i1 * areaWidth] = getWeightedBiomeEntry(WARM).biome.biomeID;
			 * }
			 * else if (k1 == 2)
			 * {
			 * aint1[j1 + i1 * areaWidth] = getWeightedBiomeEntry(WARM).biome.biomeID;
			 * }
			 * else if (k1 == 3)
			 * {
			 * aint1[j1 + i1 * areaWidth] = getWeightedBiomeEntry(COOL).biome.biomeID;
			 * }
			 * else if (k1 == 4)
			 * {
			 * //Should be ICY, but we don't have any icy biomes
			 * aint1[j1 + i1 * areaWidth] = getWeightedBiomeEntry(COOL).biome.biomeID;
			 * }
			 * else
			 * {
			 * aint1[j1 + i1 * areaWidth] = BiomeGenBase.mushroomIsland.biomeID;
			 * }
			 */
			}
		}

		return aint1;
	}

	protected BiomeEntry getWeightedBiomeEntry(BiomeManager.BiomeType type)
	{
		List<BiomeEntry> biomeList = allowedBiomes[type.ordinal()];
		int totalWeight = WeightedRandom.getTotalWeight(biomeList);
		int weight = BiomeManager.isTypeListModded(type) ? nextInt(totalWeight) : nextInt(totalWeight / 10) * 10;
		return (BiomeEntry) WeightedRandom.getRandomItem(biomeList, weight);
	}

}
