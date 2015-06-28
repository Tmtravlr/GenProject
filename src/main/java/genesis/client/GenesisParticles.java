package genesis.client;

import genesis.entity.effect.EntitySplashInsideBlockFX;
import genesis.util.Constants;
import genesis.util.ParticleUtils;
import net.minecraft.util.EnumParticleTypes;

public class GenesisParticles
{
	public static EnumParticleTypes WATER_SPLASH;

	public static void createParticles()
	{
		WATER_SPLASH = ParticleUtils.registerParticleSystem(Constants.MOD_ID.toUpperCase() + "_WATER_SPLASH",
				Constants.ASSETS_PREFIX + "water_splash", 1257, false, 0, new EntitySplashInsideBlockFX.Factory());
	}
}
