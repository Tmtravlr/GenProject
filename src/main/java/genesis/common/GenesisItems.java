package genesis.common;

import genesis.item.ItemFlintAndMarcasite;
import genesis.item.ItemGenesis;
import genesis.item.ItemGenesisBucket;
import genesis.item.ItemGenesisBucketMilk;
import genesis.item.ItemGenesisFood;
import genesis.item.ItemGenesisSeedFood;
import genesis.item.ItemGenesisSeeds;
import genesis.metadata.DungBlocksAndItems;
import genesis.metadata.EnumNodule;
import genesis.metadata.ItemsCeramicBowls;
import genesis.metadata.ToolItems;
import genesis.metadata.TreeBlocksAndItems;
import genesis.metadata.VariantsCombo;
import genesis.metadata.VariantsOfTypesCombo.ObjectNamePosition;
import genesis.metadata.VariantsOfTypesCombo.ObjectType;
import genesis.util.Constants.Unlocalized;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

public final class GenesisItems
{
	/* Materials */
	public static final Item red_clay_ball = new ItemGenesis().setUnlocalizedName(Unlocalized.MATERIAL + "redClay");
	public static final Item red_clay_bowl = new ItemGenesis().setUnlocalizedName(Unlocalized.MATERIAL + "redClayBowl");
	public static final ItemsCeramicBowls bowls = new ItemsCeramicBowls();
	public static final Item red_clay_bucket = new ItemGenesis().setUnlocalizedName(Unlocalized.MATERIAL + "redClayBucket");
	public static final Item quartz = new ItemGenesis().setUnlocalizedName(Unlocalized.MATERIAL + "quartz");
	public static final Item zircon = new ItemGenesis().setUnlocalizedName(Unlocalized.MATERIAL + "zircon");
	public static final Item garnet = new ItemGenesis().setUnlocalizedName(Unlocalized.MATERIAL + "garnet");
	public static final Item hematite = new ItemGenesis().setUnlocalizedName(Unlocalized.MATERIAL + "hematite");
	public static final Item manganese = new ItemGenesis().setUnlocalizedName(Unlocalized.MATERIAL + "manganese");
	public static final Item malachite = new ItemGenesis().setUnlocalizedName(Unlocalized.MATERIAL + "malachite");
	public static final Item olivine = new ItemGenesis().setUnlocalizedName(Unlocalized.MATERIAL + "olivine");
	public static final VariantsCombo nodules = new VariantsCombo(new ObjectType("nodule", null, null).setNamePosition(ObjectNamePosition.PREFIX), EnumNodule.values());
	public static final Item resin = new ItemGenesis().setUnlocalizedName(Unlocalized.MATERIAL + "resin");
	public static final ItemGenesisSeeds calamites = (ItemGenesisSeeds) new ItemGenesisSeeds().setUnlocalizedName(Unlocalized.MATERIAL + "calamites");
	public static final Item sphenophyllum_fiber = new ItemGenesis().setUnlocalizedName(Unlocalized.MATERIAL + "sphenophyllumFiber");
	public static final Item odontopteris_fiddlehead = new ItemGenesis().setUnlocalizedName(Unlocalized.MATERIAL + "odontopterisFiddlehead");
	public static final ItemGenesisSeeds programinis_seeds = (ItemGenesisSeeds) new ItemGenesisSeeds().setUnlocalizedName(Unlocalized.MATERIAL + "programinisSeeds");
	public static final Item programinis = new ItemGenesis().setUnlocalizedName(Unlocalized.MATERIAL + "programinis");
	public static final Item prototaxites_flesh = new ItemGenesis().setUnlocalizedName(Unlocalized.MATERIAL + "prototaxitesFlesh");
	public static final Item arthropleura_chitin = new ItemGenesis().setUnlocalizedName(Unlocalized.MATERIAL + "arthropleuraChitin");
	public static final Item liopleurodon_tooth = new ItemGenesis().setUnlocalizedName(Unlocalized.MATERIAL + "liopleurodonTooth");
	public static final Item tyrannosaurus_saliva = new ItemGenesis().setUnlocalizedName(Unlocalized.MATERIAL + "tyrannosaurusSaliva");
	public static final Item tyrannosaurus_tooth = new ItemGenesis().setUnlocalizedName(Unlocalized.MATERIAL + "tyrannosaurusTooth");

	/* Food */
	public static final ItemGenesisSeedFood zingiberopsis_rhizome = (ItemGenesisSeedFood) new ItemGenesisSeedFood(2, 1.2F).setUnlocalizedName(Unlocalized.FOOD + "zingiberopsisRhizome");
	public static final ItemGenesisSeedFood odontopteris_seeds = (ItemGenesisSeedFood) new ItemGenesisSeedFood(1, 0.8F).setUnlocalizedName(Unlocalized.FOOD + "odontopterisSeeds");
	public static final Item spirifer = new ItemGenesisFood(2, 0.4F).setUnlocalizedName(Unlocalized.FOOD + "spiriferRaw");
	public static final Item cooked_spirifer = new ItemGenesisFood(3, 1.0F).setUnlocalizedName(Unlocalized.FOOD + "spiriferCooked");
	public static final Item climatius = new ItemGenesisFood(2, 0.4F).setUnlocalizedName(Unlocalized.FOOD + "climatiusRaw");
	public static final Item cooked_climatius = new ItemGenesisFood(5, 6.0F).setUnlocalizedName(Unlocalized.FOOD + "climatiusCooked");
	public static final Item aphthoroblattina = new ItemGenesisFood(1, 0.2F).setUnlocalizedName(Unlocalized.FOOD + "aphthoroblattinaRaw");
	public static final Item cooked_aphthoroblattina = new ItemGenesisFood(2, 0.8F).setUnlocalizedName(Unlocalized.FOOD + "aphthoroblattinaCooked");
	public static final Item eryops_leg = new ItemGenesisFood(2, 0.8F).setUnlocalizedName(Unlocalized.FOOD + "eryopsLegRaw");
	public static final Item cooked_eryops_leg = new ItemGenesisFood(5, 6.0F).setUnlocalizedName(Unlocalized.FOOD + "eryopsLegCooked");
	public static final Item gryphaea = new ItemGenesisFood(2, 0.4F).setUnlocalizedName(Unlocalized.FOOD + "gryphaeaRaw");
	public static final Item cooked_gryphaea = new ItemGenesisFood(3, 1.0F).setUnlocalizedName(Unlocalized.FOOD + "gryphaeaCooked");
	public static final Item ceratites = new ItemGenesisFood(2, 0.4F).setUnlocalizedName(Unlocalized.FOOD + "ceratitesRaw");
	public static final Item cooked_ceratites = new ItemGenesisFood(4, 1.8F).setUnlocalizedName(Unlocalized.FOOD + "ceratitesCooked");
	public static final Item liopleurodon = new ItemGenesisFood(4, 2.8F).setUnlocalizedName(Unlocalized.FOOD + "liopleurodonRaw");
	public static final Item cooked_liopleurodon = new ItemGenesisFood(16, 19.8F).setUnlocalizedName(Unlocalized.FOOD + "liopleurodonCooked");
	public static final Item tyrannosaurus = new ItemGenesisFood(4, 2.8F).setUnlocalizedName(Unlocalized.FOOD + "tyrannosaurusRaw");
	public static final Item cooked_tyrannosaurus = new ItemGenesisFood(16, 19.8F).setUnlocalizedName(Unlocalized.FOOD + "tyrannosaurusCooked");

	/* Tools */
	public static final Item flint_and_marcasite = new ItemFlintAndMarcasite().setUnlocalizedName(Unlocalized.TOOL + "flintAndMarcasite");
	public static final ToolItems tools = new ToolItems();

	/* Misc */
	public static final Item ceramic_bucket = new ItemGenesisBucket(Blocks.air).setUnlocalizedName(Unlocalized.MISC + "ceramicBucket");
	public static final Item ceramic_bucket_water = new ItemGenesisBucket(Blocks.flowing_water).setUnlocalizedName(Unlocalized.MISC + "ceramicBucketWater").setContainerItem(ceramic_bucket);
	public static final Item ceramic_bucket_milk = new ItemGenesisBucketMilk().setUnlocalizedName(Unlocalized.MISC + "ceramicBucketMilk").setContainerItem(ceramic_bucket);
	public static Item bucket_komatiitic_lava;

	public static void registerItems()
	{
		GenesisBlocks.trees.registerVariants(TreeBlocksAndItems.BILLET);
		tools.registerVariants(ToolItems.PEBBLE);
		Genesis.proxy.registerItem(red_clay_ball, "red_clay_ball");
		Genesis.proxy.registerItem(red_clay_bowl, "red_clay_bowl");
		bowls.registerAll();
		Genesis.proxy.registerItem(red_clay_bucket, "red_clay_bucket");
		Genesis.proxy.registerItem(quartz, "quartz");
		Genesis.proxy.registerItem(zircon, "zircon");
		Genesis.proxy.registerItem(garnet, "garnet");
		Genesis.proxy.registerItem(hematite, "hematite");
		Genesis.proxy.registerItem(manganese, "manganese");
		Genesis.proxy.registerItem(malachite, "malachite");
		Genesis.proxy.registerItem(olivine, "olivine");
		nodules.registerAll();
		tools.registerVariants(ToolItems.FLAKE);
		Genesis.proxy.registerItem(resin, "resin");
		Genesis.proxy.registerItem(calamites, "calamites");
		Genesis.proxy.registerItem(sphenophyllum_fiber, "sphenophyllum_fiber");
		Genesis.proxy.registerItem(odontopteris_fiddlehead, "odontopteris_fiddlehead");
		Genesis.proxy.registerItem(programinis_seeds, "programinis_seeds");
		Genesis.proxy.registerItem(programinis, "programinis");
		Genesis.proxy.registerItem(prototaxites_flesh, "prototaxites_flesh");
		GenesisBlocks.dungs.registerVariants(DungBlocksAndItems.DUNG);
		Genesis.proxy.registerItem(arthropleura_chitin, "arthropleura_chitin");
		Genesis.proxy.registerItem(liopleurodon_tooth, "liopleurodon_tooth");
		Genesis.proxy.registerItem(tyrannosaurus_saliva, "tyrannosaurus_saliva");
		Genesis.proxy.registerItem(tyrannosaurus_tooth, "tyrannosaurus_tooth");

		Genesis.proxy.registerItem(zingiberopsis_rhizome, "zingiberopsis_rhizome");
		Genesis.proxy.registerItem(odontopteris_seeds, "odontopteris_seeds");
		Genesis.proxy.registerItem(spirifer, "spirifer");
		Genesis.proxy.registerItem(cooked_spirifer, "cooked_spirifer");
		Genesis.proxy.registerItem(climatius, "climatius");
		Genesis.proxy.registerItem(cooked_climatius, "cooked_climatius");
		Genesis.proxy.registerItem(aphthoroblattina, "aphthoroblattina");
		Genesis.proxy.registerItem(cooked_aphthoroblattina, "cooked_aphthoroblattina");
		Genesis.proxy.registerItem(eryops_leg, "eryops_leg");
		Genesis.proxy.registerItem(cooked_eryops_leg, "cooked_eryops_leg");
		Genesis.proxy.registerItem(gryphaea, "gryphaea");
		Genesis.proxy.registerItem(cooked_gryphaea, "cooked_gryphaea");
		Genesis.proxy.registerItem(ceratites, "ceratites");
		Genesis.proxy.registerItem(cooked_ceratites, "cooked_ceratites");
		Genesis.proxy.registerItem(liopleurodon, "liopleurodon");
		Genesis.proxy.registerItem(cooked_liopleurodon, "cooked_liopleurodon");
		Genesis.proxy.registerItem(tyrannosaurus, "tyrannosaurus");
		Genesis.proxy.registerItem(cooked_tyrannosaurus, "cooked_tyrannosaurus");

		Genesis.proxy.registerItem(flint_and_marcasite, "flint_and_marcasite");

		tools.registerAll();

		bucket_komatiitic_lava = new ItemGenesisBucket(GenesisBlocks.komatiitic_lava).setUnlocalizedName(Unlocalized.MISC + "bucketKomatiiticLava");
		Genesis.proxy.registerItem(ceramic_bucket, "ceramic_bucket");
		Genesis.proxy.registerItem(ceramic_bucket_water, "ceramic_bucket_water");
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid("water"),
				new ItemStack(ceramic_bucket_water), new ItemStack(ceramic_bucket));
		Genesis.proxy.registerItem(ceramic_bucket_milk, "ceramic_bucket_milk");
		Genesis.proxy.registerItem(bucket_komatiitic_lava, "bucket_komatiitic_lava");
	}
}
