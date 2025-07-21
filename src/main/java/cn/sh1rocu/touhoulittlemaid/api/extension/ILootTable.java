package cn.sh1rocu.touhoulittlemaid.api.extension;

import net.minecraft.resources.ResourceLocation;

public interface ILootTable {
    ResourceLocation tlm$getQueriedLootTableId();

    void tlm$setQueriedLootTableId(ResourceLocation queriedLootTableId);
}
