package genesis.world.biome.decorate;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.CLAY;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.GRASS;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.LAKE_WATER;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.TREE;

import genesis.common.GenesisBlocks;
import genesis.world.gen.feature.WorldGenTreeLepidodendron;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenClay;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class BiomeDecoratorGenesis extends BiomeDecorator
{
	public int odontopterisPerChunk = 0;
	public int lepidodendtronPerChunk = 0;
	public boolean generateDefaultTrees = true;

	public BiomeDecoratorGenesis()
	{
		WorldGenClay clayGen = new WorldGenClay(4);
		clayGen.field_150546_a = GenesisBlocks.red_clay;
		this.clayGen = clayGen;
		generateLakes = true;
	}

	@Override
	public void decorate(World world, Random random, BiomeGenBase biome, BlockPos chunkStart)
	{
		if (currentWorld != null)
		{
			throw new RuntimeException("Already decorating");
		}
		else
		{
			currentWorld = world;

			randomGenerator = random;
			field_180294_c = chunkStart;

			genDecorations(biome);
			currentWorld = null;
			randomGenerator = null;
		}
	}

	@Override
	protected void genDecorations(BiomeGenBase p_150513_1_)
	{
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(currentWorld, randomGenerator, field_180294_c));
		// this.generateOres();
		int i;
		int j;
		int k;

		boolean doGen = TerrainGen.decorate(currentWorld, randomGenerator, field_180294_c, CLAY);
		for (i = 0; doGen && i < clayPerChunk; ++i)
		{
			j = randomGenerator.nextInt(16) + 8;
			k = randomGenerator.nextInt(16) + 8;
			clayGen.generate(currentWorld, randomGenerator, currentWorld.getTopSolidOrLiquidBlock(field_180294_c.add(j, 0, k)));
		}

		i = treesPerChunk;

		if (randomGenerator.nextInt(10) == 0 && generateDefaultTrees)
		{
			++i;
		}

		int l;
		BlockPos blockpos;

		doGen = TerrainGen.decorate(currentWorld, randomGenerator, field_180294_c, TREE);
		for (j = 0; doGen && j < i; ++j)
		{
			k = randomGenerator.nextInt(16) + 8;
			l = randomGenerator.nextInt(16) + 8;
			WorldGenAbstractTree worldgenabstracttree = p_150513_1_.genBigTreeChance(randomGenerator);
			worldgenabstracttree.func_175904_e();
			blockpos = currentWorld.getHeight(field_180294_c.add(k, 0, l));

			if (worldgenabstracttree.generate(currentWorld, randomGenerator, blockpos))
			{
				worldgenabstracttree.func_180711_a(currentWorld, randomGenerator, blockpos);
			}
		}

		int i1;

		// TODO change to deadlog
		/*
		 * doGen = TerrainGen.decorate(this.currentWorld, this.randomGenerator, this.field_180294_c, FLOWERS);
		 * for (j = 0; doGen && j < this.flowersPerChunk; ++j)
		 * {
		 * k = this.randomGenerator.nextInt(16) + 8;
		 * l = this.randomGenerator.nextInt(16) + 8;
		 * i1 = this.nextInt(this.currentWorld.getHorizon(this.field_180294_c.add(k, 0, l)).getY() + 32);
		 * blockpos = this.field_180294_c.add(k, i1, l);
		 * BlockFlower.EnumFlowerType enumflowertype = p_150513_1_.pickRandomFlower(this.randomGenerator, blockpos);
		 * BlockFlower blockflower = enumflowertype.getBlockType().getBlock();
		 *
		 * if (blockflower.getMaterial() != Material.air)
		 * {
		 * this.yellowFlowerGen.setGeneratedBlock(blockflower, enumflowertype);
		 * this.yellowFlowerGen.generate(this.currentWorld, this.randomGenerator, blockpos);
		 * }
		 * }
		 */

		// TALL GRASS

		doGen = TerrainGen.decorate(currentWorld, randomGenerator, field_180294_c, GRASS);
		for (j = 0; doGen && j < grassPerChunk; ++j)
		{
			k = randomGenerator.nextInt(16) + 8;
			l = randomGenerator.nextInt(16) + 8;
			i1 = nextInt(currentWorld.getHeight(field_180294_c.add(k, 0, l)).getY() * 2);
			p_150513_1_.getRandomWorldGenForGrass(randomGenerator).generate(currentWorld, randomGenerator, field_180294_c.add(k, i1, l));
		}

		// doGen = TerrainGen.decorate(this.currentWorld, this.randomGenerator, this.field_180294_c, CUSTOM);
		for (j = 0; doGen && j < odontopterisPerChunk; ++j)
		{
			k = randomGenerator.nextInt(16) + 8;
			l = randomGenerator.nextInt(16) + 8;
			i1 = nextInt(currentWorld.getHeight(field_180294_c.add(k, 0, l)).getY() * 2);
			(new WorldGenOdontopteris()).generate(currentWorld, randomGenerator, field_180294_c.add(k, i1, l));
		}

		for (j = 0; doGen && j < lepidodendtronPerChunk; ++j)
		{
			k = randomGenerator.nextInt(16) + 8;
			l = randomGenerator.nextInt(16) + 8;
			i1 = nextInt(currentWorld.getHeight(field_180294_c.add(k, 0, l)).getY() * 2);
			(new WorldGenTreeLepidodendron(14, 18, true)).generate(currentWorld, randomGenerator, field_180294_c.add(k, i1, l));
		}

		if (generateLakes)
		{
			BlockPos blockpos1;

			doGen = TerrainGen.decorate(currentWorld, randomGenerator, field_180294_c, LAKE_WATER);
			for (j = 0; doGen && j < 50; ++j)
			{
				blockpos1 = field_180294_c.add(randomGenerator.nextInt(16) + 8, randomGenerator.nextInt(randomGenerator.nextInt(248) + 8), randomGenerator.nextInt(16) + 8);
				(new WorldGenLiquids(Blocks.flowing_water)).generate(currentWorld, randomGenerator, blockpos1);
			}

			/*
			 * doGen = TerrainGen.decorate(currentWorld, randomGenerator, field_180294_c, LAKE_LAVA);
			 * for (j = 0; doGen && j < 20; ++j)
			 * {
			 * blockpos1 = this.field_180294_c.add(this.randomGenerator.nextInt(16) + 8, this.randomGenerator.nextInt(this.randomGenerator.nextInt(this.randomGenerator.nextInt(240) + 8) + 8), this.randomGenerator.nextInt(16) + 8);
			 * (new WorldGenLiquids(Blocks.flowing_lava)).generate(this.currentWorld, this.randomGenerator, blockpos1);
			 * }
			 */
		}

		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(currentWorld, randomGenerator, field_180294_c));
	}

	private int nextInt(int i)
	{ // Safety wrapper to prevent exceptions.
		if (i <= 1)
		{
			return 0;
		}
		return randomGenerator.nextInt(i);
	}

}
