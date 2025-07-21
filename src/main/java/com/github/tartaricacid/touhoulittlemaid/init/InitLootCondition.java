package com.github.tartaricacid.touhoulittlemaid.init;

import cn.sh1rocu.touhoulittlemaid.api.extension.ILootTable;
import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.loot.LootTableTypeCondition;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class InitLootCondition {
    public static final ResourceLocation UNKNOWN_LOOT_TABLE = ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "unknown_loot_table");
    private static final ResourceLocation LAST = ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "last");

    public static void init() {
        //Global Modifier
        LootTableEvents.MODIFY.register((key, builder, source, provider) -> {
                    if (key.location().toString().startsWith("minecraft:chests"))
                        builder.withPool(LootPool.lootPool()
                                        //.name("power_point")
                                        .setRolls(ConstantValue.exactly(1))
                                        .when(LootItemRandomChanceCondition.randomChance(0.05F))
                                        .add(LootItem.lootTableItem(InitItems.POWER_POINT)))
                                .withPool(LootPool.lootPool()
                                        //.name("shrine")
                                        .setRolls(ConstantValue.exactly(1))
                                        .when(LootItemRandomChanceCondition.randomChance(0.05F))
                                        .add(LootItem.lootTableItem(InitItems.SHRINE)))
                                .build();
                }
        );

        LootTableEvents.MODIFY.addPhaseOrdering(Event.DEFAULT_PHASE, LAST);
        LootTableEvents.MODIFY.register(LAST,
                (key, builder, source, provider) -> ((ILootTable) builder).tlm$setQueriedLootTableId(key.location())
        );
    }

    public static final LootItemConditionType LOOT_TABLE_TYPE = register("loot_table_type", new LootItemConditionType(LootTableTypeCondition.CODEC));

    private static LootItemConditionType register(String id, LootItemConditionType condition) {
        return Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id), condition);
    }
}