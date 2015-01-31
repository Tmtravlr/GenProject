package genesis.metadata;

import genesis.common.GenesisBlocks;
import genesis.util.Metadata;
import net.minecraft.item.Item;

public enum EnumFern implements IMetaSingle
{
	ZYGOPTERIS("zygopteris"), RUFFORDIA("ruffordia"), ASTRALOPTERIS("astralopteris"), MATONIDIUM("matonidium");

	private final String name;
	private final String unlocalizedName;

	EnumFern(String name)
	{
		this(name, name);
	}

	EnumFern(String name, String unlocalizedName)
	{
		this.name = name;
		this.unlocalizedName = unlocalizedName;
		Metadata.add(this);
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

	@Override
	public Item getItem()
	{
		return Item.getItemFromBlock(GenesisBlocks.fern);
	}
}