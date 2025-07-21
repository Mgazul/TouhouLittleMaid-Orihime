package cn.sh1rocu.touhoulittlemaid.mixin.common;

import cn.sh1rocu.touhoulittlemaid.api.extension.ILootTable;
import com.github.tartaricacid.touhoulittlemaid.init.InitLootCondition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LootTable.Builder.class)
public class LootTableBuilderMixin implements ILootTable {
    @Unique
    private ResourceLocation queriedLootTableId;

    @Unique
    @Override
    public void tlm$setQueriedLootTableId(ResourceLocation queriedLootTableId) {
        if (this.queriedLootTableId == null && queriedLootTableId != null)
            this.queriedLootTableId = queriedLootTableId;
    }

    @Unique
    @Override
    public ResourceLocation tlm$getQueriedLootTableId() {
        return this.queriedLootTableId == null ? InitLootCondition.UNKNOWN_LOOT_TABLE : this.queriedLootTableId;
    }
}
