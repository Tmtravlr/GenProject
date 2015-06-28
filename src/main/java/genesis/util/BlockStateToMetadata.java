package genesis.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;

public class BlockStateToMetadata
{
	public static final BitwiseMask MAXMETAVALUE = new BitwiseMask(16);

	public static class BitwiseMask
	{
		private int mask;
		private int bits;

		/**
		 * Create a bitwise mask from the number of values it must be able to represent.
		 */
		public BitwiseMask(int valueCount)
		{
			// Find last used bit
			int maxBit = 0;
			int curBit = 1;

			while (((valueCount - 1) / (float) curBit) >= 1)
			{
				maxBit = curBit;
				curBit *= 2;
			}

			// Add up all used bits
			int allBits = 0;
			int bitCount = 0;

			for (int i = maxBit; i >= 1; i /= 2)
			{
				allBits += i;
				bitCount += 1;
			}

			mask = allBits;
			bits = bitCount;
		}

		/**
		 * Get the bitwise mask.
		 */
		public int getMask()
		{
			return mask;
		}

		/**
		 * Get the number of bits used by the mask.
		 */
		public int getBitCount()
		{
			return bits;
		}
	}

	private static final HashSet<Class<? extends IProperty>> SORT_PROPERTIES = new HashSet()
	{
		{
			add(PropertyBool.class);
		}
	};
	private static final HashMap<IProperty, List<Comparable>> PROPERTY_VALUES = new HashMap();

	private static List<Comparable> getSortedValues(IProperty property)
	{
		List<Comparable> values = PROPERTY_VALUES.get(property);

		if (!PROPERTY_VALUES.containsKey(property))
		{
			values = new ArrayList(property.getAllowedValues());

			if (SORT_PROPERTIES.contains(property.getClass()))
			{
				Collections.sort(values);
			}

			PROPERTY_VALUES.put(property, values);
		}

		return values;
	}

	/**
	 * Gets the IBlockState represented by the metadata passed to the function, filtered by an array of properties.
	 *
	 * @param state
	 *            The state to convert to metadata.
	 * @param properties
	 *            The properties to store in the metadata, in the desired order.
	 * @return The metadata to represent the IBlockState.
	 */
	public static int getMetaForBlockState(IBlockState state, IProperty... properties)
	{
		int metadata = 0;
		int offset = 0;

		for (IProperty property : properties)
		{
			List<Comparable> values = getSortedValues(property);

			Comparable value = state.getValue(property);

			int index = values.indexOf(value);

			BitwiseMask mask = new BitwiseMask(values.size());
			metadata |= (index & mask.getMask()) << offset;

			offset += mask.getBitCount();
		}

		if (offset > MAXMETAVALUE.getBitCount())
		{
			throw new RuntimeException("Attempted to store an IBlockState that requires " + offset + " bits in " + MAXMETAVALUE.getBitCount() + " bits of metadata");
		}

		return metadata;
	}

	/**
	 * Gets the IBlockState represented by the metadata passed to the function.
	 *
	 * @param state
	 *            The state to convert to metadata.
	 * @return The metadata to represent the IBlockState.
	 */
	public static int getMetaForBlockState(IBlockState state)
	{
		return getMetaForBlockState(state, (IProperty[]) state.getProperties().keySet().toArray(new IProperty[0]));
	}

	/**
	 * Gets the IBlockState represented by the metadata passed to the function, filtered by an array of properties.
	 *
	 * @param state
	 *            The state to base the new state off of (will usually be Block.getDefaultState()).
	 * @param metadata
	 *            The metadata to restore to an IBlockState.
	 * @param properties
	 *            The properties to restore from the metadata, in the order they were passed to getMetaForBlockState.
	 * @return The restored IBlockState.
	 */
	public static IBlockState getBlockStateFromMeta(IBlockState state, int metadata, IProperty... properties)
	{
		int offset = 0;

		for (IProperty property : properties)
		{
			List<Comparable> values = getSortedValues(property);

			BitwiseMask mask = new BitwiseMask(values.size());
			int metaValue = (metadata & (mask.getMask() << offset)) >> offset;

			Comparable propValue = values.get(metaValue);

			state = state.withProperty(property, propValue);

			offset += mask.getBitCount();
		}

		if (offset > MAXMETAVALUE.getBitCount())
		{
			throw new RuntimeException("Attempted to retrieve a property from an IBlockState past " + MAXMETAVALUE.getBitCount() + " bits, the maximum metadata bit count.");
		}

		return state;
	}

	/**
	 * Gets the IBlockState represented by the metadata passed to the function.
	 *
	 * @param state
	 *            The state to base the new state off of (will usually be Block.getDefaultState()).
	 * @param metadata
	 *            The metadata to restore to an IBlockState.
	 * @return The restored IBlockState.
	 */
	public static IBlockState getBlockStateFromMeta(IBlockState state, int metadata)
	{
		return getBlockStateFromMeta(state, metadata, (IProperty[]) state.getProperties().keySet().toArray(new IProperty[0]));
	}

	/**
	 * Gets the number of possible values after the provided properties have been stored in metadata. Used to determine how many
	 * variants a block can store after storing other properties (like facing direction).
	 *
	 * @param properties
	 *            The properties that must be stored.
	 * @return Number of possible values.
	 */
	public static int getMetadataLeftAfter(IProperty... properties)
	{
		int bitsLeft = MAXMETAVALUE.getBitCount();

		for (IProperty property : properties)
		{
			BitwiseMask mask = new BitwiseMask(property.getAllowedValues().size());
			bitsLeft -= mask.getBitCount();
		}

		if (bitsLeft > 0)
		{
			int vals = (int) Math.pow(2, bitsLeft);
			return vals;
		}

		return 0;
	}
}
