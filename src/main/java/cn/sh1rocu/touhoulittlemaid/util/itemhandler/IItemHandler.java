package cn.sh1rocu.touhoulittlemaid.util.itemhandler;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.world.item.ItemStack;

public interface IItemHandler extends AutoSyncedComponent {
    String TAG_INVENTORY = "Inventory";

    int getSlots();

    ItemStack getStackInSlot(int slot);

    ItemStack insertItem(int slot, ItemStack stack, boolean simulate);

    ItemStack extractItem(int slot, int amount, boolean simulate);

    int getSlotLimit(int slot);

    boolean isItemValid(int slot, ItemStack stack);
}