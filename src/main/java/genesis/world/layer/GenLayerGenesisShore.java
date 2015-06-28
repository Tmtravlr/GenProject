package genesis.world.layer;

import genesis.common.GenesisBiomes;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerGenesisShore extends GenLayerGenesis
{

	public GenLayerGenesisShore(long seed, GenLayer parent)
	{
		super(seed);
		this.parent = parent;
	}

	@Override
	public int[] getInts(int par1, int par2, int par3, int par4)
	{
		int[] var5 = parent.getInts(par1 - 1, par2 - 1, par3 + 2, par4 + 2);
		int[] var6 = IntCache.getIntCache(par3 * par4);

		for (int var7 = 0; var7 < par4; ++var7)
		{
			for (int var8 = 0; var8 < par3; ++var8)
			{
				initChunkSeed(var8 + par1, var7 + par2);
				int var9 = var5[var8 + 1 + (var7 + 1) * (par3 + 2)];
				int var10;
				int var11;
				int var12;
				int var13;

				if (var9 == GenesisBiomes.rainforest.biomeID)// || var9 == GenesisBiomes.rainforestM.biomeID)
				{
					var10 = var5[var8 + 1 + (var7 + 1 - 1) * (par3 + 2)];
					var11 = var5[var8 + 1 + 1 + (var7 + 1) * (par3 + 2)];
					var12 = var5[var8 + 1 - 1 + (var7 + 1) * (par3 + 2)];
					var13 = var5[var8 + 1 + (var7 + 1 + 1) * (par3 + 2)];

					if (var10 == GenesisBiomes.rainforest.biomeID)// || var10 == GenesisBiomes.rainforestM.biomeID)
					{
						if (var11 == GenesisBiomes.rainforest.biomeID)// || var11 == GenesisBiomes.rainforestM.biomeID)
						{
							if (var12 == GenesisBiomes.rainforest.biomeID)// || var12 == GenesisBiomes.rainforestM.biomeID)
							{
								if (var13 == GenesisBiomes.rainforest.biomeID)// || var13 == GenesisBiomes.rainforestM.biomeID)
								{
									var6[var8 + var7 * par3] = var9;
								}
								else
								{
									var6[var8 + var7 * par3] = GenesisBiomes.rainforestEdge.biomeID;
								}
							}
							else
							{
								var6[var8 + var7 * par3] = GenesisBiomes.rainforestEdge.biomeID;
							}
						}
						else
						{
							var6[var8 + var7 * par3] = GenesisBiomes.rainforestEdge.biomeID;
						}
					}
					else
					{
						var6[var8 + var7 * par3] = GenesisBiomes.rainforestEdge.biomeID;
					}
				}
				else
				{
					var6[var8 + var7 * par3] = var9;
				}
			}
		}
		return var6;
	}

}
