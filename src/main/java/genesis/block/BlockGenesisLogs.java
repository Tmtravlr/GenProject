package genesis.block;

import genesis.common.GenesisCreativeTabs;
import genesis.metadata.EnumTree;
import genesis.metadata.IMetadata;
import genesis.metadata.PropertyIMetadata;
import genesis.metadata.VariantsOfTypesCombo;
import genesis.metadata.VariantsOfTypesCombo.BlockProperties;
import genesis.metadata.VariantsOfTypesCombo.ObjectType;
import genesis.util.BlockStateToMetadata;
import genesis.util.Constants.Unlocalized;
import java.util.List;
import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGenesisLogs extends BlockLog
{
	/**
	 * Used in BlocksAndItemsWithVariantsOfTypes.
	 */
	@BlockProperties
	public static IProperty[] getProperties()
	{
		return new IProperty[] { LOG_AXIS };
	}

	public final VariantsOfTypesCombo owner;
	public final ObjectType type;

	public final List<EnumTree> variants;
	public final PropertyIMetadata variantProp;

	public BlockGenesisLogs(List<EnumTree> variants, VariantsOfTypesCombo owner, ObjectType type)
	{
		super();

		this.owner = owner;
		this.type = type;

		this.variants = variants;
		variantProp = new PropertyIMetadata("variant", variants);

		blockState = new BlockState(this, variantProp, LOG_AXIS);
		setDefaultState(getBlockState().getBaseState().withProperty(LOG_AXIS, EnumAxis.NONE));

		setHarvestLevel("axe", 0);
		Blocks.fire.setFireInfo(this, 5, 5);

		setCreativeTab(GenesisCreativeTabs.BLOCK);

	}

	@Override
	public BlockGenesisLogs setUnlocalizedName(String name)
	{
		super.setUnlocalizedName(Unlocalized.PREFIX + name);

		return this;
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
	{
		owner.fillSubItems(type, variants, list);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return BlockStateToMetadata.getMetaForBlockState(state, variantProp, LOG_AXIS);
	}

	@Override
	public IBlockState getStateFromMeta(int metadata)
	{
		return BlockStateToMetadata.getBlockStateFromMeta(getDefaultState(), metadata, variantProp, LOG_AXIS);
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		IBlockState state = super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);

		if (placer.isSneaking())
		{
			state = state.withProperty(LOG_AXIS, EnumAxis.NONE);
		}

		return state;
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return owner.getStack(type, (IMetadata) state.getValue(variantProp)).getItemDamage();
	}

	@Override
	public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
	{	// Prevent logs from dropping if the player isn't using the appropriate tool type.
		IBlockState state = getActualState(world.getBlockState(pos), world, pos);
		ItemStack held = player.getHeldItem();

		if (held == null || held.getItem().getHarvestLevel(held, getHarvestTool(state)) < 0)
		{
			return false;
		}

		return super.canHarvestBlock(world, pos, player);
	}
}
