package genesis.metadata;

public enum EnumSilt implements IMetadata
{
	SILT("", "", "default"), RED_SILT("red");
	
	final String name;
	final String unlocalizedName;
	final String toString;
	
	EnumSilt(String name, String unlocalizedName, String toString)
	{
		this.name = name;
		this.unlocalizedName = unlocalizedName;
		this.toString = toString;
	}
	
	private EnumSilt(String name, String unlocalizedName)
	{
		this(name, unlocalizedName, name);
	}
	
	private EnumSilt(String name)
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
	
	@Override
	public String toString()
	{
		return toString;
	}
}
