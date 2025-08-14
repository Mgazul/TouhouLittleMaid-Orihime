package cn.sh1rocu.touhoulittlemaid.api.extension;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public interface IItemEntity {
    default boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        return false;
    }
}
