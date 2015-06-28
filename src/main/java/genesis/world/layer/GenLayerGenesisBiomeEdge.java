package genesis.world.layer;

import genesis.common.GenesisBiomes;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerGenesisBiomeEdge extends GenLayerGenesis
{

	public GenLayerGenesisBiomeEdge(long seed, GenLayer parent)
	{
		super(seed);
		this.parent = parent;
	}

	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
	{
		int[] aint = parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
		int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);

		for (int i1 = 0; i1 < areaHeight; ++i1)
		{
			for (int j1 = 0; j1 < areaWidth; ++j1)
			{
				initChunkSeed(j1 + areaX, i1 + areaY);
				int k1 = aint[j1 + 1 + (i1 + 1) * (areaWidth + 2)];
				// TODO replace with edge biomes
				if (!replaceBiomeEdgeIfNecessary(aint, aint1, j1, i1, areaWidth, k1, GenesisBiomes.rainforest.biomeID, GenesisBiomes.rainforestEdge.biomeID)
						&& !replaceBiomeEdge(aint, aint1, j1, i1, areaWidth, k1, GenesisBiomes.auxForest.biomeID, GenesisBiomes.auxForestEdge.biomeID))
				{
					aint1[j1 + i1 * areaWidth] = k1;
				}
				else
				{
					aint1[j1 + i1 * areaWidth] = k1;
				}

			}
		}

		return aint1;
	}

	private boolean replaceBiomeEdgeIfNecessary(int[] p_151636_1_, int[] p_151636_2_, int p_151636_3_, int p_151636_4_, int p_151636_5_,
			int p_151636_6_, int p_151636_7_, int p_151636_8_)
	{
		if (!biomesEqualOrMesaPlateau(p_151636_6_, p_151636_7_))
		{
			return false;
		}
		else
		{
			int k1 = p_151636_1_[p_151636_3_ + 1 + (p_151636_4_ + 1 - 1) * (p_151636_5_ + 2)];
			int l1 = p_151636_1_[p_151636_3_ + 1 + 1 + (p_151636_4_ + 1) * (p_151636_5_ + 2)];
			int i2 = p_151636_1_[p_151636_3_ + 1 - 1 + (p_151636_4_ + 1) * (p_151636_5_ + 2)];
			int j2 = p_151636_1_[p_151636_3_ + 1 + (p_151636_4_ + 1 + 1) * (p_151636_5_ + 2)];

			if (canBiomesBeNeighbors(k1, p_151636_7_) && canBiomesBeNeighbors(l1, p_151636_7_)
					&& canBiomesBeNeighbors(i2, p_151636_7_) && canBiomesBeNeighbors(j2, p_151636_7_))
			{
				p_151636_2_[p_151636_3_ + p_151636_4_ * p_151636_5_] = p_151636_6_;
			}
			else
			{
				p_151636_2_[p_151636_3_ + p_151636_4_ * p_151636_5_] = p_151636_8_;
			}

			return true;
		}
	}

	private boolean replaceBiomeEdge(int[] p_151635_1_, int[] p_151635_2_, int p_151635_3_, int p_151635_4_, int p_151635_5_, int p_151635_6_,
			int p_151635_7_, int p_151635_8_)
	{
		if (p_151635_6_ != p_151635_7_)
		{
			return false;
		}
		else
		{
			int k1 = p_151635_1_[p_151635_3_ + 1 + (p_151635_4_ + 1 - 1) * (p_151635_5_ + 2)];
			int l1 = p_151635_1_[p_151635_3_ + 1 + 1 + (p_151635_4_ + 1) * (p_151635_5_ + 2)];
			int i2 = p_151635_1_[p_151635_3_ + 1 - 1 + (p_151635_4_ + 1) * (p_151635_5_ + 2)];
			int j2 = p_151635_1_[p_151635_3_ + 1 + (p_151635_4_ + 1 + 1) * (p_151635_5_ + 2)];

			if (biomesEqualOrMesaPlateau(k1, p_151635_7_) && biomesEqualOrMesaPlateau(l1, p_151635_7_) && biomesEqualOrMesaPlateau(i2, p_151635_7_)
					&& biomesEqualOrMesaPlateau(j2, p_151635_7_))
			{
				p_151635_2_[p_151635_3_ + p_151635_4_ * p_151635_5_] = p_151635_6_;
			}
			else
			{
				p_151635_2_[p_151635_3_ + p_151635_4_ * p_151635_5_] = p_151635_8_;
			}

			return true;
		}
	}

	private boolean canBiomesBeNeighbors(int p_151634_1_, int p_151634_2_)
	{
		if (biomesEqualOrMesaPlateau(p_151634_1_, p_151634_2_))
		{
			return true;
		}
		else
		{
			BiomeGenBase biomegenbase = BiomeGenBase.getBiome(p_151634_1_);
			BiomeGenBase biomegenbase1 = BiomeGenBase.getBiome(p_151634_2_);

			if (biomegenbase != null && biomegenbase1 != null)
			{
				BiomeGenBase.TempCategory tempcategory = biomegenbase.getTempCategory();
				BiomeGenBase.TempCategory tempcategory1 = biomegenbase1.getTempCategory();
				return tempcategory == tempcategory1 || tempcategory == BiomeGenBase.TempCategory.MEDIUM
						|| tempcategory1 == BiomeGenBase.TempCategory.MEDIUM;
			}
			else
			{
				return false;
			}
		}
	}

}
