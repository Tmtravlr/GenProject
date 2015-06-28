package genesis.block;

import com.google.common.collect.Sets;
import genesis.client.GenesisSounds;
import genesis.common.GenesisBlocks;
import genesis.common.GenesisCreativeTabs;
import genesis.metadata.EnumAquaticPlant;
import genesis.metadata.IModifyStateMap;
import genesis.metadata.VariantsCombo;
import genesis.metadata.VariantsOfTypesCombo.BlockProperties;
import genesis.metadata.VariantsOfTypesCombo.ObjectType;
import genesis.util.Constants;
import genesis.util.FlexibleStateMap;
import genesis.util.WorldUtils;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAquaticPlant extends BlockGenesisVariants<EnumAquaticPlant, VariantsCombo> implements IModifyStateMap
{
	/**
	 * Used in BlocksAndItemsWithVariantsOfTypes.
	 */
	@BlockProperties
	public static IProperty[] getProperties()
	{
		return new IProperty[] {};
	}

	private Set<Block> validGround;

	public BlockAquaticPlant(List<EnumAquaticPlant> variants, VariantsCombo owner, ObjectType type)
	{
		super(variants, owner, type, Material.water);

		blockState = new BlockState(this, variantProp, BlockLiquid.LEVEL);
		setDefaultState(getBlockState().getBaseState());

		noItemVariants.add(EnumAquaticPlant.CHARNIA);

		setCreativeTab(GenesisCreativeTabs.DECORATIONS);

		setHardness(0.0F);
		setStepSound(GenesisSounds.AQUATICPLANT);
		setTickRandomly(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void customizeStateMap(FlexibleStateMap stateMap)
	{
		stateMap.addIgnoredProperties(BlockLiquid.LEVEL);
	}

	@Override
	public BlockAquaticPlant setUnlocalizedName(String name)
	{
		super.setUnlocalizedName(name);

		return this;
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
	{
		owner.fillSubItems(type, variants, list, noItemVariants);
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
		checkAndDropBlock(worldIn, pos, state);
	}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
		checkAndDropBlock(worldIn, pos, state);
	}

	protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		if (!canBlockStay(worldIn, pos, state))
		{
			//this.breakPlant(worldIn, pos, state);
			worldIn.destroyBlock(pos, true);
		}
	}

	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
	{
		super.onBlockDestroyedByPlayer(worldIn, pos, state);
		breakPlant(worldIn, pos, state);
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
		return canBlockStay(worldIn, pos, getDefaultState());
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

	@Override
	public int damageDropped(IBlockState state)
	{
		Comparable variant = state.getValue(variantProp);

		return variant == EnumAquaticPlant.CHARNIA ? owner.getItemMetadata(EnumAquaticPlant.CHARNIA_TOP) : super.damageDropped(state);
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
			validGround.add(GenesisBlocks.red_clay);
			validGround.add(GenesisBlocks.ooze);
			validGround.add(GenesisBlocks.peat);
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
			final boolean corner0 = isWaterish(blocks.get(i++).getBlock());
			final boolean corner1 = isWaterish(blocks.get(i++).getBlock());
			boolean corner2;

			if (i == blocks.size())
			{
				corner2 = isWaterish(blocks.get(0).getBlock());
			}
			else
			{
				corner2 = isWaterish(blocks.get(i).getBlock());
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
