package genesis.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public interface IMetadata extends IStringSerializable {
    public String getUnlocalizedName();

    public int getMetadata();

    public ItemStack createStack(int amount);
}
