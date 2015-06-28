package genesis.metadata;

import genesis.item.ItemMulti;
import genesis.metadata.VariantsOfTypesCombo.ObjectType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

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

	public static final ObjectType<Block, ItemMulti> main = new ObjectType("ceramic_bowl", "ceramicBowl", null, null, EnumDye.valueList())
	.setNamePosition(ObjectNamePosition.PREFIX);
	public static final ObjectType<Block, ItemMulti> dyes = new ObjectType("dye", null, null)
	.setValidVariants(EnumDye.valueList())
	.setNamePosition(ObjectNamePosition.PREFIX);

	public static final List<ObjectType> allObjectTypes = new ArrayList<ObjectType>()
			{
		{
			add(main);
			add(dyes);
		}
			};
			public static final List<IMetadata> allVariants = new ArrayList()
			{
				{
					addAll(Arrays.asList(EnumCeramicBowls.values()));
					addAll(EnumDye.valueList());
				}
			};

			public ItemsCeramicBowls()
			{
				super(allObjectTypes, allVariants);
			}

			public ItemStack getStack(EnumCeramicBowls bowlVariant, int size)
			{
				return super.getStack(main, bowlVariant, size);
			}

			public ItemStack getStack(EnumCeramicBowls bowlVariant)
			{
				return getStack(bowlVariant, 1);
			}

			public ItemStack getStack(EnumDye dyeVariant, int size)
			{
				return super.getStack(dyes, dyeVariant, size);
			}

			public ItemStack getStack(EnumDye dyeVariant)
			{
				return getStack(dyeVariant, 1);
			}

			public ItemStack getStack(EnumDyeColor color, int size)
			{
				return getStack(EnumDye.get(color), size);
			}

			public ItemStack getStack(EnumDyeColor color)
			{
				return getStack(color, 1);
			}
}
