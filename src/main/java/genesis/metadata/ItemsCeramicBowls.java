package genesis.metadata;

import genesis.metadata.VariantsOfTypesCombo.*;
import genesis.util.Constants.Unlocalized;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import genesis.item.*;

@SuppressWarnings({"rawtypes", "serial"})
public class ItemsCeramicBowls extends VariantsOfTypesCombo<ObjectType, IMetadata>
{
	public static enum EnumCeramicBowls implements IMetadata
	{
		BOWL(""),
		WATER_BOWL("water");
		
		protected String name;
		protected String unlocalizedName;
		
		private EnumCeramicBowls(String name, String unlocalizedName)
		{
			this.name = name;
			this.unlocalizedName = unlocalizedName;
		}

		private EnumCeramicBowls(String name)
		{
			this(name, name);
		}

		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public String getUnlocalizedName()
		{
			return unlocalizedName;
		}
	}
	
	public static final ObjectType<Block, ItemCeramicBowl> MAIN = new ObjectType<Block, ItemCeramicBowl>("ceramic_bowl", Unlocalized.Section.MATERIAL + "ceramicBowl", null, ItemCeramicBowl.class)
			.setValidVariants(Arrays.asList(EnumCeramicBowls.values()))
			.setNamePosition(ObjectNamePosition.PREFIX);
	public static final ObjectType<Block, ItemMulti> DYES = new ObjectType<Block, ItemMulti>("dye", Unlocalized.Section.MATERIAL + "dye", null, null)
			.setValidVariants(GenesisDye.valueList())
			.setNamePosition(ObjectNamePosition.PREFIX);
	public static final ObjectType<Block, ItemPorridge> PORRIDGE = new ObjectType<Block, ItemPorridge>("porridge", Unlocalized.Section.FOOD + "porridge", null, ItemPorridge.class)
			.setValidVariants(Arrays.asList(EnumPorridge.values()))
			.setNamePosition(ObjectNamePosition.PREFIX);
	
	public static final List<ObjectType> ALL_OBJECT_TYPES = new ArrayList<ObjectType>()
	{{
		add(MAIN);
		add(DYES);
		add(PORRIDGE);
	}};
	public static final List<IMetadata> ALL_VARIANTS = new ArrayList<IMetadata>()
	{{
		addAll(Arrays.asList(EnumCeramicBowls.values()));
		addAll(GenesisDye.valueList());
		addAll(Arrays.asList(EnumPorridge.values()));
	}};
	
	public ItemsCeramicBowls()
	{
		super(ALL_OBJECT_TYPES, ALL_VARIANTS);
		
		setUnlocalizedPrefix(Unlocalized.PREFIX);
	}
	
	public ItemStack getStack(EnumCeramicBowls bowlVariant, int size)
	{
		return super.getStack(MAIN, bowlVariant, size);
	}
	
	public ItemStack getStack(EnumCeramicBowls bowlVariant)
	{
		return getStack(bowlVariant, 1);
	}
	
	public ItemStack getStack(GenesisDye dyeVariant, int size)
	{
		return super.getStack(DYES, dyeVariant, size);
	}
	
	public ItemStack getStack(GenesisDye dyeVariant)
	{
		return getStack(dyeVariant, 1);
	}
	
	public ItemStack getStack(EnumDyeColor color, int size)
	{
		return getStack(GenesisDye.get(color), size);
	}
	
	public ItemStack getStack(EnumDyeColor color)
	{
		return getStack(color, 1);
	}
	
	public ItemStack getStack(EnumPorridge porridge, int size)
	{
		return getStack(PORRIDGE, porridge, size);
	}
	
	public ItemStack getStack(EnumPorridge porridge)
	{
		return getStack(porridge, 1);
	}
}
