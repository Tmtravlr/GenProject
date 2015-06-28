package genesis.block.tileentity;

import genesis.block.tileentity.crafting.CookingPotRecipeRegistry;
import genesis.block.tileentity.crafting.CookingPotRecipeRegistry.IInventoryCookingPot;
import genesis.block.tileentity.gui.ContainerCampfire;
import genesis.block.tileentity.render.TileEntityCampfireRenderer;
import genesis.util.Constants;
import genesis.util.Constants.Unlocalized;
import genesis.util.ItemStackKey;
import genesis.util.RandomFloatRange;
import genesis.util.WorldUtils;
import genesis.util.gui.RestrictedDisabledSlot.IInventoryDisabledSlots;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TileEntityCampfire extends TileEntityLockable implements ISidedInventory, IInventoryDisabledSlots, IInventoryCookingPot, IUpdatePlayerListBox
{
	public static int getItemBurnTime(ItemStack stack)
	{
		int time = TileEntityFurnace.getItemBurnTime(stack);

		// Exclude lava.
		FluidStack lava = FluidContainerRegistry.getFluidForFilledItem(new ItemStack(Items.lava_bucket));

		if (FluidContainerRegistry.containsFluid(stack, lava))
		{
			time = 0;
		}

		return (int) (time * 1.125F);
	}

	public static boolean isItemFuel(ItemStack stack)
	{
		return getItemBurnTime(stack) > 0;
	}

	public static boolean isCookingPot(ItemStack stack)
	{
		return CookingPotRecipeRegistry.isCookingPotItem(stack);
	}

	protected static final Set<ItemStackKey> allowedOutputs = new HashSet();

	public static ItemStack registerAllowedOutput(ItemStack output)
	{
		allowedOutputs.add(new ItemStackKey(output));
		return output;
	}

	public static boolean isAllowedOutput(ItemStack output)
	{
		if (output == null)
		{
			return false;
		}

		return allowedOutputs.contains(new ItemStackKey(output));
	}

	public static final int SLOT_COUNT = 5;
	public static final int SLOT_INPUT = 0;
	public static final int SLOT_FUEL = 1;
	public static final int SLOT_OUTPUT = 2;
	public static final int SLOT_INGREDIENT_1 = 3;
	public static final int SLOT_INGREDIENT_2 = 4;

	protected static final int WET_TIME = 200;

	public BlockCampfire blockCampfire;

	protected ItemStack[] inventory = new ItemStack[SLOT_COUNT];

	protected boolean wasBurning = false;
	protected boolean waterAround = false;

	public float prevRot = 0;
	public float rot = 0;
	public float rotVel = 0;

	public String customName;

	public int burnTime;
	public int totalBurnTime;
	public int cookTime;

	protected int fireSoundTime = 30;
	protected int fireSoundCounter;

	public TileEntityCampfire()
	{
		super();
	}

	@Override
	public BlockCampfire getBlockType()
	{
		super.getBlockType();

		if (blockCampfire == null)
		{
			blockCampfire = (BlockCampfire) blockType;
		}

		return blockCampfire;
	}

	public boolean hasCookingPot()
	{
		return isCookingPot(getInput());
	}

	public boolean canSmeltItemType(ItemStack stack)
	{
		if (stack != null)
		{
			if (stack.getItemUseAction().equals(EnumAction.EAT))
			{
				return true;
			}

			if (Block.getBlockFromItem(stack.getItem()) == Blocks.cactus)
			{
				return true;
			}

			ItemStack smeltResult = FurnaceRecipes.instance().getSmeltingResult(stack);

			if (smeltResult != null)
			{
				if (isAllowedOutput(smeltResult))
				{
					return true;
				}

				if (smeltResult.getItemUseAction().equals(EnumAction.EAT))
				{
					return true;
				}

				if (smeltResult.getItem() == Items.coal)
				{
					return true;
				}
			}
		}

		return false;
	}

	public boolean canSmelt()
	{
		ItemStack smeltingItem = getInput();

		if (!canSmeltItemType(smeltingItem) && !hasCookingPot())
		{
			return false;
		}

		ItemStack outputSlotStack = getOutput();
		ItemStack smeltResult = CookingPotRecipeRegistry.getResult(this);

		if (smeltResult == null)
		{
			smeltResult = FurnaceRecipes.instance().getSmeltingResult(smeltingItem);

			if (smeltResult == null)
			{
				return false;
			}
		}

		if (outputSlotStack == null)
		{
			return true;
		}

		if (!outputSlotStack.isItemEqual(smeltResult))
		{
			return false;
		}

		int newOutputSize = outputSlotStack.stackSize + smeltResult.stackSize;

		return (newOutputSize <= getInventoryStackLimit() && newOutputSize <= outputSlotStack.getMaxStackSize());
	}

	public void smeltItem()
	{
		if (canSmelt())
		{
			ItemStack smeltingItem = getInput();
			ItemStack outputItem = getOutput();

			if (CookingPotRecipeRegistry.hasRecipe(this))
			{
				CookingPotRecipeRegistry.getRecipe(this).craft(this);
			}
			else
			{
				ItemStack result = FurnaceRecipes.instance().getSmeltingResult(smeltingItem);

				if (outputItem == null)
				{
					outputItem = result.copy();
				}
				else if (outputItem.isItemEqual(result))
				{
					outputItem.stackSize += result.stackSize;
				}

				smeltingItem.stackSize--;

				if (smeltingItem.stackSize <= 0)
				{
					smeltingItem = null;
				}

				setInventorySlotContents(SLOT_INPUT, smeltingItem);
				setInventorySlotContents(SLOT_OUTPUT, outputItem);
			}
		}
	}

	public boolean isBurning()
	{
		return burnTime > 0;
	}

	protected boolean isRainingOn()
	{
		return worldObj.getRainStrength(1) >= 0.9F && worldObj.canLightningStrike(pos.up());
	}

	protected void sendDescriptionPacket()
	{
		worldObj.markBlockForUpdate(pos);
	}

	public void setWaterAround(boolean water)
	{
		waterAround = water;

		sendDescriptionPacket();
	}

	public boolean isWaterAround()
	{
		return waterAround;
	}

	public boolean isWet()
	{
		return burnTime < 0;
	}

	public void setWet()
	{
		blockCampfire.randomDisplayTick(worldObj, pos, worldObj.getBlockState(pos), worldObj.rand);

		if (isBurning())
		{
			worldObj.playAuxSFX(1004, pos, 0);
		}

		burnTime = Math.min(burnTime, -WET_TIME);
		updateBurningValue();
	}

	public boolean updateBurningValue()
	{
		boolean burning = isBurning();

		if (wasBurning != burning)
		{
			blockCampfire.setBurning(worldObj, pos, burning);
			wasBurning = burning;
			return true;
		}

		return false;
	}

	public boolean burnFuelIfNotBurning()
	{
		if (!isBurning() && !isWet())
		{
			ItemStack burningItem = getFuel();
			totalBurnTime = burnTime = getItemBurnTime(burningItem);

			if (burnTime > 0)
			{
				if (burningItem != null)
				{
					--burningItem.stackSize;
					ItemStack container = burningItem.getItem().getContainerItem(burningItem);

					if (burningItem.stackSize == 0)
					{
						setInventorySlotContents(1, container);
					}
					else
					{
						WorldUtils.spawnItemsAt(worldObj, pos.getX() + 0.5, pos.getY() + 0.25, pos.getZ() + 0.5, container);
					}

					updateBurningValue();
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public void update()
	{
		// Get the block type for use in the update method.
		getBlockType();

		if (worldObj.isRemote && !TileEntityCampfireRenderer.hasCookingItemModel(getInput()))
		{
			final int fullAngle = 360;
			final int increments = 360;
			final float increment = fullAngle / (float) increments;
			prevRot = rot;

			boolean cookingPot = hasCookingPot();
			float newVel = rotVel;

			if (cookingPot)
			{
				float swungPos = MathHelper.sin(rot * (float) Math.PI / 180);
				newVel -= swungPos * 4;

				Random rand = worldObj.rand;

				if (wasBurning && rand.nextInt(20) == 0)
				{
					float swing = rand.nextFloat() * increment;

					if (newVel == 0)
					{
						newVel += rand.nextInt() == 0 ? swing : -swing;
					}
					else if (newVel > 0)
					{
						newVel += swing;
					}
					else
					{
						newVel -= swing;
					}
				}
			}
			else if (canSmelt())
			{
				float target = 0;

				if (wasBurning)
				{
					target = increment;
				}

				newVel += (target - newVel) * 0.5F;
			}

			newVel *= 0.9F;
			rot += newVel;
			rotVel = newVel;

			float sub = rot - (rot % fullAngle);

			if (sub > 0)
			{
				prevRot -= sub;
				rot -= sub;
			}
		}

		boolean updateBlock = false;

		if (isRainingOn() || isWaterAround())
		{
			// Stop burning if the fire is rained on.
			setWet();
		}
		else if (wasBurning)	// wasBurning (which is the value of isBurning() at the end of last tick) tells us whether burnTime > 0
		{
			burnTime--;
			// If the campfire is not burning, start an item burning.
			burnFuelIfNotBurning();

			if (worldObj.isRemote)
			{	// Play fire sound effect every 1.5 secs on client.
				if (fireSoundCounter > 0)
				{
					fireSoundCounter--;
				}
				else
				{
					RandomFloatRange volumeRange = new RandomFloatRange(1, 2);
					RandomFloatRange pitchRange = new RandomFloatRange(0.3F, 0.7F);
					worldObj.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, "fire.fire",
							volumeRange.getRandom(worldObj.rand), pitchRange.getRandom(worldObj.rand), false);

					if (hasCookingPot() && canSmelt())
					{
						worldObj.playSound(pos.getX() + 0.5, pos.getY() + 0.625, pos.getZ() + 0.5, Constants.ASSETS_PREFIX + "ambient.cookingpot",
								volumeRange.getRandom(worldObj.rand), pitchRange.getRandom(worldObj.rand), false);
					}

					fireSoundCounter = fireSoundTime;
				}
			}
		}
		else if (isWet())
		{
			burnTime++;
		}

		updateBlock = updateBlock || updateBurningValue();

		if (wasBurning && canSmelt())
		{
			cookTime++;

			if (cookTime >= 200)
			{
				cookTime = 0;
				smeltItem();
				updateBlock = true;
			}
		}
		else
		{
			cookTime = 0;
		}

		if (updateBlock)
		{
			markDirty();
		}
	}

	/**
	 * Have to use this method to update the light because otherwise it won't update when the block is broken, as of Forge 1390.
	 */
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		if (oldState.getBlock() != newState.getBlock() ||
				(oldState.getValue(BlockCampfire.FIRE) != newState.getValue(BlockCampfire.FIRE)))
		{
			worldObj.checkLight(pos);
		}

		return oldState.getBlock() != newState.getBlock();
	}

	public int getBurnTimeLeftScaled(int pixels)
	{
		float total = totalBurnTime;

		if (isWet())
		{
			total = -WET_TIME;
		}

		return (int) Math.ceil((burnTime / total) * pixels);
	}

	public int getCookProgressScaled(int pixels)
	{
		return Math.round((cookTime / 200F) * pixels);
	}

	/*@Override
	public void onInventoryChanged()
	{
		super.onInventoryChanged();

		if (worldObj != null)
		{
			worldObj.markBlockForUpdate(pos);
		}
	}*/

	@Override
	public int getSizeInventory()
	{
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return inventory[slot];
	}

	@Override
	public boolean isSlotDisabled(int index)
	{
		if (index == SLOT_INGREDIENT_1 || index == SLOT_INGREDIENT_2)
		{
			return hasCookingPot();
		}

		return false;
	}

	@Override
	public void clear()
	{
		for (int i = 0; i < inventory.length; ++i)
		{
			inventory[i] = null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		inventory[slot] = stack;
		worldObj.markBlockForUpdate(pos);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		switch (slot)
		{
		case SLOT_INPUT:
			if (canSmeltItemType(stack))
			{
				return true;
			}

			if (isCookingPot(stack))
			{
				return true;
			}

			return false;
		case SLOT_FUEL:
			return TileEntityFurnace.isItemFuel(stack);
		case SLOT_INGREDIENT_1:
		case SLOT_INGREDIENT_2:
			return hasCookingPot() && CookingPotRecipeRegistry.isRecipeIngredient(stack, this);
		}

		return false;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		ItemStack stack = getStackInSlot(slot);

		if (stack != null)
		{
			setInventorySlotContents(slot, null);
		}

		return stack;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		ItemStack stack = getStackInSlot(slot);

		if (stack != null)
		{
			if (stack.stackSize <= amount)
			{
				setInventorySlotContents(slot, null);
			}
			else
			{
				stack = stack.splitStack(amount);

				if (stack.stackSize <= 0)
				{
					setInventorySlotContents(slot, null);
				}
			}
		}

		return stack;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	public void setCustomInventoryName(String name)
	{
		customName = name;
	}

	@Override
	public String getCommandSenderName()
	{
		return hasCustomName() ? customName : Unlocalized.CONTAINER + "campfire";
	}

	@Override
	public boolean hasCustomName()
	{
		return customName != null && customName.length() > 0;
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound compound = new NBTTagCompound();
		compound.setInteger("burnTime", burnTime);
		compound.setBoolean("waterAround", waterAround);

		ItemStack input = getInput();

		if (input != null)
		{
			compound.setTag("input", input.writeToNBT(new NBTTagCompound()));
		}

		ItemStack fuel = getFuel();

		if (fuel != null)
		{
			compound.setTag("fuel", fuel.writeToNBT(new NBTTagCompound()));
		}

		return new S35PacketUpdateTileEntity(pos, 0, compound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		NBTTagCompound compound = packet.getNbtCompound();
		burnTime = compound.getInteger("burnTime");
		waterAround = compound.getBoolean("waterAround");

		if (compound.hasKey("input"))
		{
			setInput(ItemStack.loadItemStackFromNBT(compound.getCompoundTag("input")));
		}
		else
		{
			setInput(null);
		}

		if (compound.hasKey("fuel"))
		{
			setFuel(ItemStack.loadItemStackFromNBT(compound.getCompoundTag("fuel")));
		}
		else
		{
			setFuel(null);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		NBTTagList tagList = compound.getTagList("items", 10);
		inventory = new ItemStack[getSizeInventory()];

		for (int i = 0; i < tagList.tagCount(); ++i)
		{
			NBTTagCompound itemCompound = (NBTTagCompound) tagList.get(i);
			byte slot = itemCompound.getByte("slot");

			if (slot >= 0 && slot < inventory.length)
			{
				inventory[slot] = ItemStack.loadItemStackFromNBT(itemCompound);
			}
		}

		burnTime = compound.getInteger("burnTime");
		cookTime = compound.getInteger("cookTime");
		totalBurnTime = compound.getInteger("totalBurnTime");

		waterAround = compound.getBoolean("waterAround");

		if (compound.hasKey("customName"))
		{
			customName = compound.getString("customName");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		NBTTagList itemList = new NBTTagList();
		int i = 0;

		for (ItemStack stack : inventory)
		{
			if (stack != null)
			{
				NBTTagCompound itemComp = new NBTTagCompound();
				itemComp.setByte("slot", (byte) i);
				stack.writeToNBT(itemComp);

				itemList.appendTag(itemComp);
			}

			i++;
		}

		compound.setTag("items", itemList);

		compound.setInteger("burnTime", burnTime);
		compound.setInteger("totalBurnTime", totalBurnTime);
		compound.setInteger("cookTime", cookTime);

		compound.setBoolean("waterAround", waterAround);

		if (hasCustomName())
		{
			compound.setString("customName", customName);
		}
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return new AxisAlignedBB(pos.getX() - 0.25, pos.getY(), pos.getZ() - 0.25,
				pos.getX() + 1.25, pos.getY() + 1.25, pos.getZ() + 1.25);
	}

	@Override
	public ItemStack getInput()
	{
		return getStackInSlot(SLOT_INPUT);
	}

	@Override
	public void setInput(ItemStack stack)
	{
		setInventorySlotContents(SLOT_INPUT, stack);
	}

	@Override
	public ItemStack[] getIngredients()
	{
		return new ItemStack[] { getStackInSlot(SLOT_INGREDIENT_1), getStackInSlot(SLOT_INGREDIENT_2) };
	}

	@Override
	public void setIngredients(ItemStack[] stacks)
	{
		setInventorySlotContents(SLOT_INGREDIENT_1, stacks[0]);
		setInventorySlotContents(SLOT_INGREDIENT_2, stacks[1]);
	}

	@Override
	public ItemStack getFuel()
	{
		return getStackInSlot(SLOT_FUEL);
	}

	@Override
	public void setFuel(ItemStack stack)
	{
		setInventorySlotContents(SLOT_FUEL, stack);
	}

	@Override
	public ItemStack getOutput()
	{
		return getStackInSlot(SLOT_OUTPUT);
	}

	@Override
	public void setOutput(ItemStack stack)
	{
		setInventorySlotContents(SLOT_OUTPUT, stack);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return true;
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public ContainerCampfire createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return null;
	}

	@Override
	public String getGuiID()
	{
		return null;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return null;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		return false;
	}

	@Override
	public boolean isLocked()
	{
		return false;
	}

	@Override
	public void setLockCode(LockCode code)
	{

	}

	@Override
	public LockCode getLockCode()
	{
		return null;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{}

	@Override
	public void closeInventory(EntityPlayer player)
	{}
}
