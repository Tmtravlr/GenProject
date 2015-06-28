package genesis.metadata;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import java.util.Collection;
import java.util.Comparator;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class ToolTypes
{
	public static class ToolType implements IMetadata
	{
		public final EnumToolMaterial material;
		public final EnumToolQuality quality;
		public final ToolMaterial toolMaterial;

		public ToolType(EnumToolMaterial material, EnumToolQuality quality)
		{
			this.material = material;
			this.quality = quality;

			// ToolMaterial init
			int usesBase = material.getUses();
			float usesMult = quality.getUsesMult();

			float efficiencyBase = material.getEfficiency();
			float efficiencyMult = quality.getEfficiencyMult();

			float damageBase = material.getEntityDamage();
			float damageMult = quality.getEntityDamageMult();

			int enchantBase = material.getEnchantability();
			float enchantMult = quality.getEnchantabilityMult();

			if (usesBase >= 0 && usesMult >= 0 &&
					efficiencyBase >= 0 && efficiencyMult >= 0 &&
					damageBase >= 0 && damageMult >= 0 &&
					enchantBase >= 0 && enchantMult >= 0)
			{
				int uses = Math.round(usesBase * usesMult);
				float efficiency = efficiencyBase * efficiencyMult;
				float entityDamage = damageBase * damageMult;
				int enchantability = Math.round(enchantBase * enchantMult);

				toolMaterial = EnumHelper.addToolMaterial(getName(),
						material.getHarvestLevel(), uses, efficiency, entityDamage, enchantability);
			}
			else
			{
				toolMaterial = null;
			}
		}

		@Override
		public String getName()
		{
			String qualityName = quality.getName();

			return ("".equals(qualityName) ? "" : qualityName + "_") + material.getName();
		}

		@Override
		public String getUnlocalizedName()
		{
			return material.getUnlocalizedName();
		}

		@Override
		public String toString()
		{
			return super.toString() + "[quality=" + quality + ", material=" + material + "]";
		}
	}

	// Comparator to sort the table to the correct order.
	private static final Comparator<Enum> sorter = new Comparator<Enum>()
			{
		@Override
		public int compare(Enum m1, Enum m2)
		{
			return Integer.compare(m1.ordinal(), m2.ordinal());
		}
			};
			protected static final Table<EnumToolMaterial, EnumToolQuality, ToolType> table = TreeBasedTable.create(sorter, sorter);

			static
			{
				for (EnumToolMaterial material : EnumToolMaterial.values())
				{
					for (EnumToolQuality quality : EnumToolQuality.values())
					{
						ToolType toolType = new ToolType(material, quality);
						table.put(material, quality, toolType);
					}
				}
			}

			public static ToolType getToolHead(EnumToolMaterial material, EnumToolQuality quality)
			{
				return table.get(material, quality);
			}

			public static ToolType[] getAll()
			{
				Collection<ToolType> types = table.values();
				return types.toArray(new ToolType[types.size()]);
			}
}
