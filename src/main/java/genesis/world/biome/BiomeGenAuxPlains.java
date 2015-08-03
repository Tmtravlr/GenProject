package genesis.world.biome;

import java.util.Random;

import net.minecraft.world.gen.feature.WorldGenerator;
import genesis.common.GenesisBlocks;
import genesis.world.biome.decorate.BiomeDecoratorGenesis;
import genesis.world.biome.decorate.WorldGenArchaeomarasmius;
import genesis.world.biome.decorate.WorldGenGrowingPlant;
import genesis.world.biome.decorate.WorldGenPhlebopteris;
import genesis.world.biome.decorate.WorldGenGrowingPlant.GrowingPlantType;
import genesis.world.biome.decorate.WorldGenPalaeoagaracites;
import genesis.world.biome.decorate.WorldGenRockBoulders;
import genesis.world.gen.feature.WorldGenTreeAraucarioxylon;

public class BiomeGenAuxPlains extends BiomeGenBaseGenesis
{
	public BiomeGenAuxPlains(int id)
	{
		super(id);
		setBiomeName("Araucarioxylon Plains");
		setTemperatureRainfall(1.1F, 1.0F);
		theBiomeDecorator.grassPerChunk = 1;
		
		addDecoration(new WorldGenArchaeomarasmius().setPatchSize(3).setCountPerChunk(5));
		addDecoration(new WorldGenPalaeoagaracites().setPatchSize(5).setCountPerChunk(10));
		addDecoration(new WorldGenGrowingPlant(GenesisBlocks.programinis).setPlantType(GrowingPlantType.NORMAL).setPatchSize(5).setCountPerChunk(7));
		addDecoration(new WorldGenRockBoulders().setCountPerChunk(5));
		
		addTree(new WorldGenTreeAraucarioxylon(25, 30, true).setTreeCountPerChunk(1));
	}
	
	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random rand)
	{
		return new WorldGenPhlebopteris();
	}
}