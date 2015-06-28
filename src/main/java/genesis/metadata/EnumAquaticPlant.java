package genesis.metadata;


public enum EnumAquaticPlant implements IMetadata
{
	BANGIOMORPHA("bangiomorpha"), MARPOLIA("marpolia"), MARGERETIA("margaretia"), CHANCELLORIA("chancelloria"), HAZELLA("hazelia"),
	DIAONIELLA("diagoniella"), PIRANIA("pirania"), VAUXIA("vauxia"), WAPKIA("wapkia"), PTERIDINIUM("pteridinium"), DINOMISCHUS("dinomischus"),
	ECHMATOCRINUS("echmatocrinus"), THAUMAPTILON("thaumaptilon"), PRIMOCANDELABRUM("primocandelabrum"), CHARNIA("charnia"), CHARNIA_TOP("charnia_top");

	private final String name;
	private final String unlocalizedName;

	private EnumAquaticPlant(String name)
	{
		this(name, name);
	}

	private EnumAquaticPlant(String name, String unlocalizedName)
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
