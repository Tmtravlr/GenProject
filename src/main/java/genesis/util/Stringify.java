package genesis.util;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;

public class Stringify
{
	public static String stringifyIterable(Iterable<?> list)
	{
		String output = "{";
		String sep = ", ";
		boolean remove = false;
		
		for (Object obj : list)
		{
			output += stringify(obj) + sep;
			remove = true;
		}
		
		if (remove)
		{
			output.substring(0, output.length() - sep.length());
		}
		
		output += "}";
		
		return output;
	}
	
	public static String stringifyArray(Object[] objArray)
	{
		return stringifyIterable(ImmutableList.copyOf(objArray));
	}
	
	public static String stringify(Object obj)
	{
		if (obj instanceof Object[])
		{
			return stringifyArray((Object[]) obj);
		}
		
		if (obj instanceof Iterable<?>)
		{
			return stringifyIterable((Iterable<?>) obj);
		}
		
		if (obj instanceof IStringSerializable)
		{
			return ((IStringSerializable) obj).getName();
		}
		
		String data = "";
		
		if (obj instanceof Item)
		{
			data += "name = " + ((Item) obj).getUnlocalizedName();
		}
		else if (obj instanceof Block)
		{
			data += "name = " + ((Block) obj).getUnlocalizedName();
		}
		else if (obj instanceof Class)
		{
			Class<?> clazz = (Class<?>) obj;
			String name = clazz.getSimpleName();
			
			if (clazz.isAnonymousClass())
			{
				name = clazz.getSuperclass().getSimpleName();
			}
			
			data += "name = " + name;
		}
		
		return obj.getClass().getSimpleName() + (data.length() > 0 ? "(" + data + ")" : "");
	}
}
