package genesis.world.layer;

import genesis.common.GenesisBiomes;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;

public class GenLayerReplaceOcean extends GenLayerGenesis
{

	private final GenLayer parent;

	public GenLayerReplaceOcean(long seed, GenLayer parent)
	{
		super(seed);
		this.parent = parent;
	}

	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
	{
		int[] aint = parent.getInts(areaX, areaY, areaWidth, areaHeight);
		for (int i = 0; i < aint.length; i++)
		{
			if (aint[i] == BiomeGenBase.ocean.biomeID)
			{
				aint[i] = GenesisBiomes.shallowOcean.biomeID;
			}
		}
		return aint;
	}

}
