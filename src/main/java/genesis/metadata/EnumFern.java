package genesis.metadata;

public enum EnumFern implements IMetadata
{
	ZYGOPTERIS("zygopteris"), PHLEBOPTERIS("phlebopteris"), RUFFORDIA("ruffordia"), ASTRALOPTERIS("astralopteris"), MATONIDIUM("matonidium");

	final String name;
	final String unlocalizedName;

	EnumFern(String name)
	{
		this(name, name);
	}

	EnumFern(String name, String unlocalizedName)
	{
		this.name = name;
		this.unlocalizedName = unlocalizedName;
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
