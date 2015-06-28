package genesis.world.biome;

import genesis.world.biome.decorate.BiomeDecoratorGenesis;
import genesis.world.biome.decorate.WorldGenZygopteris;
import java.util.Random;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenRainforest extends BiomeGenBaseGenesis
{

	public BiomeGenRainforest(int id)
	{
		super(id);
		biomeName = "Rainforest";
		rainfall = 1.0F;
		minHeight = 0.05F;
		maxHeight = .1F;
		theBiomeDecorator.treesPerChunk = 0;
		theBiomeDecorator.grassPerChunk = 2;
		((BiomeDecoratorGenesis) theBiomeDecorator).generateDefaultTrees = false;
		((BiomeDecoratorGenesis) theBiomeDecorator).odontopterisPerChunk = 0;
		((BiomeDecoratorGenesis) theBiomeDecorator).lepidodendtronPerChunk = 20;
	}

	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random rand)
	{
		return new WorldGenZygopteris();
	}

}
