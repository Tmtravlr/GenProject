package genesis.world.biome;

import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class BiomeManagerGenesis
{

	private static final Map<BiomeType, List<BiomeEntry>> biomes = Maps.newEnumMap(BiomeType.class);

	public static boolean registerBiome(BiomeGenBase biome, BiomeType type, int weight)
	{
		checkEntry(type);
		return biomes.get(type).add(new BiomeEntry(biome, weight));
	}

	public static Map<BiomeType, List<BiomeEntry>> getEntriesMap()
	{
		return Maps.newEnumMap(biomes);
	}

	public static List<BiomeEntry> getEntries(BiomeType type)
	{
		checkEntry(type);
		return biomes.get(type);
	}

	private static void checkEntry(BiomeType type)
	{
		if (!biomes.containsKey(type))
		{
			biomes.put(type, new ArrayList<BiomeEntry>());
		}
	}

}
