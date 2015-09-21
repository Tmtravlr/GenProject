package genesis.metadata;

import java.util.*;

public enum EnumPlant implements IMetadata
{
	COOKSONIA("cooksonia"), BARAGWANATHIA("baragwanathia"), SCIADOPHYTON("sciadophyton"), PSILOPHYTON("psilophyton"), NOTHIA("nothia"),
	RHYNIA("rhynia"), ARCHAEAMPHORA("archaeamphora"), MABELIA("mabelia"), PALAEOASTER("palaeoaster"), ASTEROXYLON("asteroxylon");

	public static final Set<EnumPlant> NO_SINGLES = Collections.emptySet();
	public static final Set<EnumPlant> DOUBLES = EnumSet.of(ASTEROXYLON);
	
	final String name;
	final String unlocalizedName;
	
	EnumPlant(String name)
	{
		this(name, name);
	}
	
	EnumPlant(String name, String unlocalizedName)
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
