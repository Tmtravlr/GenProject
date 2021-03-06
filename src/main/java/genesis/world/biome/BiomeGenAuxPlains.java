package genesis.world.biome;

import genesis.common.GenesisBlocks;
import genesis.metadata.DungBlocksAndItems;
import genesis.metadata.EnumDung;
import genesis.metadata.EnumFern;
import genesis.world.biome.decorate.WorldGenArchaeomarasmius;
import genesis.world.biome.decorate.WorldGenGrass;
import genesis.world.biome.decorate.WorldGenGrassMulti;
import genesis.world.biome.decorate.WorldGenGrowingPlant;
import genesis.world.biome.decorate.WorldGenMossStages;
import genesis.world.biome.decorate.WorldGenPalaeoagaracites;
import genesis.world.biome.decorate.WorldGenRockBoulders;
import genesis.world.gen.feature.WorldGenTreeAraucarioxylon;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class BiomeGenAuxPlains extends BiomeGenBaseGenesis
{
	public BiomeGenAuxPlains(int id)
	{
		super(id);
		setBiomeName("Araucarioxylon Plains");
		setTemperatureRainfall(1.1F, 0.9F);
		
		theBiomeDecorator.grassPerChunk = 1;
		
		addDecoration(new WorldGenArchaeomarasmius().setPatchSize(3).setCountPerChunk(5));
		addDecoration(new WorldGenPalaeoagaracites().setPatchSize(10).setCountPerChunk(16));
		addDecoration(new WorldGenGrowingPlant(GenesisBlocks.programinis).setPatchSize(5).setCountPerChunk(7));
		addDecoration(new WorldGenRockBoulders().setRarity(10).setWaterRequired(false).setMaxHeight(3).addBlocks(GenesisBlocks.dungs.getBlockState(DungBlocksAndItems.DUNG_BLOCK, EnumDung.SAUROPODA)).setCountPerChunk(1));
		addDecoration(new WorldGenMossStages().setCountPerChunk(30));
		
		addTree(new WorldGenTreeAraucarioxylon(25, 30, true).setRarity(5).setTreeCountPerChunk(1));
	}
	
	@Override
	public WorldGenGrass getRandomWorldGenForGrass(Random rand)
	{
		return new WorldGenGrassMulti(GenesisBlocks.plants.getFernBlockState(EnumFern.PHLEBOPTERIS)).setVolume(64);
	}
	
	@Override
	public void generateBiomeTerrain(World world, Random rand, ChunkPrimer primer, int blockX, int blockZ, double d)
	{
		mossStages = new int[2];
		mossStages[0] = 1;
		mossStages[1] = 2;
		super.generateBiomeTerrain(world, rand, primer, blockX, blockZ, d);
	}
}
