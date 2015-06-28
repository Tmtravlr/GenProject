package genesis.world.biome;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenRiver extends BiomeGenBaseGenesis
{

	public BiomeGenRiver(int id)
	{
		super(id);
		biomeName = "River";
		setHeight(BiomeGenBase.height_ShallowWaters);
	}

}
