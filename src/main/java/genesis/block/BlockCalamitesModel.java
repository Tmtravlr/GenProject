package genesis.block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.renderer.vertex.VertexFormatElement.EnumType;
import net.minecraft.client.renderer.vertex.VertexFormatElement.EnumUsage;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IModelState;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import genesis.common.Genesis;
import genesis.util.Constants;

public class BlockCalamitesModel implements IModel
{
	protected static ResourceLocation BASE_MODEL = new ResourceLocation(Constants.MOD_ID, "block/calamites_base");
	protected static ResourceLocation TOP_MODEL = new ResourceLocation(Constants.MOD_ID, "block/calamites_top");
	
	public static BlockCalamitesModel instance;
	
	public static void register()
	{
		Genesis.proxy.registerCustomModel("models/block/calamites", new BlockCalamitesModel());
	}

	@Override
	public Collection<ResourceLocation> getDependencies()
	{
		return new ArrayList<ResourceLocation>(){{
			add(BASE_MODEL);
			add(TOP_MODEL);
		}};
	}

	@Override
	public Collection<ResourceLocation> getTextures()
	{
		return new ArrayList<ResourceLocation>(){{
			add(new ResourceLocation(Constants.MOD_ID, "blocks/calamites_base"));
			add(new ResourceLocation(Constants.MOD_ID, "blocks/calamites_top"));
		}};
	}

	@Override
	public IFlexibleBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
	{
		IFlexibleBakedModel bakedModel = ModelLoaderRegistry.getModel(BASE_MODEL).bake(state, format, bakedTextureGetter);
		
		return bakedModel;
	}

	@Override
	public IModelState getDefaultState()
	{
        return ModelRotation.X0_Y0;
	}
}
