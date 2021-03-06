package genesis.item;

import java.util.*;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import genesis.client.GenesisClient;
import genesis.common.GenesisItems;
import genesis.metadata.*;
import genesis.metadata.ItemsCeramicBowls.EnumCeramicBowls;
import genesis.metadata.VariantsOfTypesCombo.ObjectType;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ItemCeramicBowl extends ItemGenesis
{
	public final ItemsCeramicBowls owner;
	
	protected final List<IMetadata> variants;
	protected final ObjectType<Block, ItemCeramicBowl> type;
	
	public ItemCeramicBowl(List<IMetadata> variants, ItemsCeramicBowls owner, ObjectType<Block, ItemCeramicBowl> type)
	{
		super();
		
		this.owner = owner;
		this.type = type;
		this.variants = variants;
		
		setHasSubtypes(true);
		
		if (variants.contains(EnumCeramicBowls.WATER_BOWL))
		{
			MinecraftForge.EVENT_BUS.register(this);
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return owner.getUnlocalizedName(stack, super.getUnlocalizedName(stack));
	}
	
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List subItems)
	{
		owner.fillSubItems(type, variants, subItems);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (owner.getVariant(stack) == EnumCeramicBowls.BOWL)
		{
			MovingObjectPosition hit = getMovingObjectPositionFromPlayer(world, player, true);
	
			if (hit != null && hit.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
			{
				BlockPos hitPos = hit.getBlockPos();
				
				if (world.isBlockModifiable(player, hitPos) && player.canPlayerEdit(hitPos.offset(hit.sideHit), hit.sideHit, stack))
				{
					if (world.getBlockState(hitPos).getBlock().getMaterial() == Material.water)
					{
						player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
						
						player.swingItem();
						ItemStack newStack = GenesisItems.bowls.getStack(ItemsCeramicBowls.MAIN, EnumCeramicBowls.WATER_BOWL);
						stack.stackSize--;
						
						if (stack.stackSize <= 0)
						{
							return newStack;
						}
						
						if (!player.inventory.addItemStackToInventory(newStack))
						{
							player.dropPlayerItemWithRandomChoice(newStack, false);
						}
					}
				}
			}
		}
		
		return stack;
	}
	
	@SubscribeEvent
	public void onBlockInteracted(PlayerInteractEvent event)
	{
		EntityPlayer player = event.entityPlayer;
		
		World world = event.world;
		BlockPos pos = event.pos;
		
		IBlockState state = Blocks.air.getDefaultState();
		if (pos != null)
			state = world.getBlockState(pos);
		Block block = state.getBlock();
		
		ItemStack stack = player.getHeldItem();
		VariantsOfTypesCombo<ObjectType, IMetadata>.VariantData variantData = owner.getVariantData(stack);
		
		switch (event.action)
		{
		case RIGHT_CLICK_BLOCK:
			if (variantData != null && variantData.variant == EnumCeramicBowls.BOWL && block == Blocks.cauldron)
			{
				event.useBlock = Result.DENY;
				
				int cauldronLevel = (Integer) state.getValue(BlockCauldron.LEVEL);
				
				if (cauldronLevel > 0)
				{
					world.setBlockState(pos, state.withProperty(BlockCauldron.LEVEL, Math.max(cauldronLevel - 1, 0)));
					
					if (!player.capabilities.isCreativeMode)
					{
						stack.stackSize--;
						
						if (stack.stackSize <= 0)
						{
							player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
						}
						
						ItemStack newStack = owner.getStack(EnumCeramicBowls.WATER_BOWL);
						
						if (!player.inventory.addItemStackToInventory(newStack))
						{
							player.dropPlayerItemWithRandomChoice(newStack, false);
						}
					}
					
					event.useItem = Result.DENY;
					
					if (world.isRemote)	// We must send a packet to the server telling it that the player right clicked or else it won't fill the cauldron server side.
					{
						Minecraft mc = GenesisClient.getMC();
						EntityPlayerSP spPlayer = mc.thePlayer;
						
						if (spPlayer == player)
						{
							Vec3 hitVec = mc.objectMouseOver.hitVec;
							hitVec = hitVec.subtract(pos.getX(), pos.getY(), pos.getZ());
							Packet packet = new C08PacketPlayerBlockPlacement(pos, event.face.getIndex(), stack, (float) hitVec.xCoord, (float) hitVec.yCoord, (float) hitVec.zCoord);
							spPlayer.sendQueue.addToSendQueue(packet);
							
							spPlayer.swingItem();
							event.setCanceled(true);
						}
					}
				}
			}
			
			break;
		case RIGHT_CLICK_AIR:
			break;
		case LEFT_CLICK_BLOCK:
			break;
		}
	}
}
