package genesis.block;

import java.util.*;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import genesis.common.GenesisSounds;
import genesis.common.GenesisBlocks;
import genesis.common.GenesisCreativeTabs;
import genesis.item.ItemBlockMulti;
import genesis.metadata.*;
import genesis.metadata.VariantsOfTypesCombo.*;
import genesis.util.BlockStateToMetadata;
import genesis.util.Constants;
import genesis.util.FlexibleStateMap;
import genesis.util.WorldUtils;
import genesis.util.random.drops.blocks.VariantDrop;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap.Builder;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings({"rawtypes", "unchecked"})
public class BlockAquaticPlant extends Block implements IModifyStateMap
{
	/**
	 * Used in VariantsOfTypesCombo.
	 */
	@BlockProperties
	public static IProperty[] getProperties()
	{
		return new IProperty[]{};
	}
	
	public final VariantsCombo<EnumAquaticPlant, BlockAquaticPlant, ItemBlockMulti> owner;
	public final ObjectType<BlockAquaticPlant, ItemBlockMulti> type;
	
	public final List<EnumAquaticPlant> variants;
	public final PropertyIMetadata<EnumAquaticPlant> variantProp;
	
	protected Set<Block> validGround;
	protected final Set<EnumAquaticPlant> noDrops = ImmutableSet.of(EnumAquaticPlant.CHARNIA);
	
	public BlockAquaticPlant(List<EnumAquaticPlant> variants, VariantsCombo<EnumAquaticPlant, BlockAquaticPlant, ItemBlockMulti> owner, ObjectType<BlockAquaticPlant, ItemBlockMulti> type)
	{
		super(Material.water);
		
		//super(variants, owner, (ObjectType) type, Material.water);
		
		this.owner = owner;
		this.type = type;
		
		this.variants = variants;
		variantProp = new PropertyIMetadata<EnumAquaticPlant>("variant", variants);
		
		blockState = new BlockState(this, variantProp, BlockLiquid.LEVEL);
		setDefaultState(getBlockState().getBaseState());
		
		setCreativeTab(GenesisCreativeTabs.DECORATIONS);
		
		setHardness(0.0F);
		setStepSound(GenesisSounds.AQUATICPLANT);
		setTickRandomly(true);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return BlockStateToMetadata.getMetaForBlockState(state, variantProp);
	}
	
	@Override
	public IBlockState getStateFromMeta(int metadata)
	{
		return BlockStateToMetadata.getBlockStateFromMeta(getDefaultState(), metadata, variantProp);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void customizeStateMap(FlexibleStateMap stateMap)
	{
		stateMap.addIgnoredProperties(BlockLiquid.LEVEL);
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
	{
		owner.fillSubItems(type, variants, list, noDrops);
	}
	
	@Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
		if (!noDrops.contains(state.getValue(variantProp)))
		{
	        return super.getDrops(world, pos, state, fortune);
		}
		
		return Collections.emptyList();
    }
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return owner.getItemMetadata(type, (EnumAquaticPlant) state.getValue(variantProp));
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
	{
		return null;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public boolean isReplaceable(World worldIn, BlockPos pos)
	{
		return false;
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		if (state.getValue(variantProp) == EnumAquaticPlant.CHANCELLORIA)
		{
			entityIn.attackEntityFrom(Constants.CHANCELLORIA_DMG, 0.5F);
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		this.checkAndDropBlock(worldIn, pos, state);
	}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
		this.checkAndDropBlock(worldIn, pos, state);
	}

	protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		if (!this.canBlockStay(worldIn, pos, state))
		{
			//this.breakPlant(worldIn, pos, state);
			worldIn.destroyBlock(pos, true);
		}
	}

	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
	{
		super.onBlockDestroyedByPlayer(worldIn, pos, state);
		this.breakPlant(worldIn, pos, state);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if (((EnumAquaticPlant) state.getValue(variantProp)) == EnumAquaticPlant.CHARNIA_TOP)
		{
			worldIn.setBlockState(pos, getDefaultState().withProperty(variantProp, EnumAquaticPlant.CHARNIA), 3);
			worldIn.setBlockState(pos.up(), getDefaultState().withProperty(variantProp, EnumAquaticPlant.CHARNIA_TOP), 3);
		}
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return this.canBlockStay(worldIn, pos, this.getDefaultState());
	}

	private void breakPlant(World world, BlockPos pos, IBlockState state)
	{
		world.setBlockState(pos, Blocks.water.getStateFromMeta(0), 3);
		EnumAquaticPlant variant = (EnumAquaticPlant) state.getValue(variantProp);
		
		if (variant == EnumAquaticPlant.CHARNIA_TOP)
		{
			IBlockState below = world.getBlockState(pos.down());
			
			if (below.getBlock() == this && below.getValue(variantProp) == EnumAquaticPlant.CHARNIA)
			{
				world.setBlockState(pos.down(), Blocks.water.getStateFromMeta(0), 3);
			}
		}
		else if (variant == EnumAquaticPlant.CHARNIA)
		{
			IBlockState above = world.getBlockState(pos.up());
			
			if (above.getBlock() == this && above.getValue(variantProp) == EnumAquaticPlant.CHARNIA_TOP)
			{
				world.setBlockState(pos.up(), Blocks.water.getStateFromMeta(0), 3);
			}
		}
	}

	public boolean canBlockStay(World world, BlockPos pos, IBlockState state)
	{
		if (validGround == null)
		{
			validGround = Sets.newIdentityHashSet();
			validGround.add(Blocks.dirt);
			validGround.add(Blocks.sand);
			validGround.add(Blocks.gravel);
			validGround.add(Blocks.clay);
			validGround.add(Blocks.hardened_clay);
			validGround.add(Blocks.stonebrick);
			validGround.add(Blocks.stone);
			validGround.add(Blocks.log);
			validGround.add(Blocks.log2);
			validGround.add(Blocks.planks);
			validGround.add(GenesisBlocks.red_clay);
			validGround.add(GenesisBlocks.ooze);
			validGround.add(GenesisBlocks.peat);
			
			for (BlockGenesisLogs block : GenesisBlocks.trees.getBlocks(TreeBlocksAndItems.LOG))
			{
				validGround.add(block);
			}
			
			for (BlockGenesisLogs block : GenesisBlocks.trees.getBlocks(TreeBlocksAndItems.ROTTEN_LOG))
			{
				validGround.add(block);
			}
			
			for (Block block : GenesisBlocks.silt.getBlocks(SiltBlocks.SILT))
			{
				validGround.add(block);
			}
			
			for (Block block : GenesisBlocks.silt.getBlocks(SiltBlocks.SILTSTONE))
			{
				validGround.add(block);
			}
			
			validGround.addAll(GenesisBlocks.corals.getBlocks(GenesisBlocks.corals.soleType));
		}
		
		IBlockState below = world.getBlockState(pos.down());
		Block blockBelow = below.getBlock();
		EnumAquaticPlant variant = (EnumAquaticPlant) state.getValue(variantProp);
		
		if (!validGround.contains(blockBelow)
				&& blockBelow instanceof BlockGenesisRock == false
				&& (variant != EnumAquaticPlant.CHARNIA_TOP || blockBelow != this || below.getValue(variantProp) != EnumAquaticPlant.CHARNIA))
		{
			return false;
		}
		
		IBlockState above = world.getBlockState(pos.up());
		
		if (above.getBlock().getMaterial() != Material.water)
		{
			return false;
		}
		if (variant == EnumAquaticPlant.CHARNIA && world.getBlockState(pos.up(2)).getBlock().getMaterial() != Material.water)
		{
			return false;
		}
		
		final List<IBlockState> blocks = WorldUtils.getBlocksAround(world, pos);
		
		for (int i = 0; i < blocks.size();)
		{
			final boolean corner0 = this.isWaterish(blocks.get(i++).getBlock());
			final boolean corner1 = this.isWaterish(blocks.get(i++).getBlock());
			boolean corner2;
			
			if (i == blocks.size())
			{
				corner2 = this.isWaterish(blocks.get(0).getBlock());
			}
			else
			{
				corner2 = this.isWaterish(blocks.get(i).getBlock());
			}
			
			if (corner0 && corner1 && corner2)
			{
				return true;
			}
		}
		
		return false;
	}

	private boolean isWaterish(Block block)
	{
		return (block == Blocks.water) || block == Blocks.flowing_water || (block == this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Block.EnumOffsetType getOffsetType()
	{
		return Block.EnumOffsetType.XYZ;
	}
}
