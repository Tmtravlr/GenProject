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
import genesis.common.Genesis;
import genesis.util.Constants;

public class BlockCalamitesModel implements IModel
{
	public static class CalamitesBakedModel implements IFlexibleBakedModel
	{
		VertexFormat format = new VertexFormat();
		
		public CalamitesBakedModel()
		{
			format.setElement(new VertexFormatElement(0, EnumType.FLOAT, EnumUsage.POSITION, 3));
		}
		
		@Override
		public boolean isAmbientOcclusion()
		{
			return true;
		}

		@Override
		public boolean isGui3d()
		{
			return false;
		}

		@Override
		public boolean isBuiltInRenderer()
		{
			return false;
		}

		@Override
		public TextureAtlasSprite getTexture()
		{
			return null;
		}

		@Override
		public ItemCameraTransforms getItemCameraTransforms()
		{
			return ItemCameraTransforms.DEFAULT;
		}

		@Override
		public List<BakedQuad> getFaceQuads(EnumFacing side)
		{
			ArrayList<BakedQuad> quads = new ArrayList<BakedQuad>();
			
			return quads;
		}

		@Override
		public List<BakedQuad> getGeneralQuads()
		{
			ArrayList<BakedQuad> quads = new ArrayList<BakedQuad>();
			
			return quads;
		}

		@Override
		public VertexFormat getFormat()
		{
            return new VertexFormat(format);
		}
	}
	
	public static BlockCalamitesModel instance;
	
	public static void register()
	{
		Genesis.proxy.registerCustomModel("blocks/calamites", new BlockCalamitesModel());
	}

	@Override
	public Collection<ResourceLocation> getDependencies()
	{
		return new ArrayList<ResourceLocation>(){{
			add(new ResourceLocation(Constants.MOD_ID, "blocks/calamites"));
		}};
	}

	@Override
	public Collection<ResourceLocation> getTextures()
	{
		return new ArrayList<ResourceLocation>(){{
			add(new ResourceLocation(Constants.MOD_ID, "blocks/calamites"));
		}};
	}

	@Override
	public IFlexibleBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
	{
		return new CalamitesBakedModel();
	}

	@Override
	public IModelState getDefaultState()
	{
        return ModelRotation.X0_Y0;
	}
}
