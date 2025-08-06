package com.github.tartaricacid.touhoulittlemaid.init;

import cn.sh1rocu.touhoulittlemaid.api.extension.ILootContext;
import cn.sh1rocu.touhoulittlemaid.api.extension.ILootTable;
import cn.sh1rocu.touhoulittlemaid.api.extension.ILootTableBuilder;
import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.datagen.LootTableGenerator;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;

public class InitLootModifiers {
    private static final ResourceLocation LAST = ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "last");

    public static void init() {
        // Global Modifier
        LootTableEvents.MODIFY.register((key, builder, source, provider) -> {
                    if (key.location().toString().startsWith("minecraft:chests"))
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.CHEST_POWER_POINT)));

                    if (key == BuiltInLootTables.SPAWN_BONUS_CHEST)
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.SPAWN_BONUS)));
                    if (key == BuiltInLootTables.VILLAGE_TEMPLE)
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.NORMAL_BAUBLE)));
                    if (key == BuiltInLootTables.DESERT_PYRAMID)
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.RARE_BAUBLE)));
                    if (key == BuiltInLootTables.JUNGLE_TEMPLE)
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.RARE_BAUBLE)));
                    if (key == BuiltInLootTables.WOODLAND_MANSION)
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.VERY_RARE_BAUBLE)));
                    if (key == BuiltInLootTables.SIMPLE_DUNGEON)
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.FURNACE_OR_CRAFTING_TABLE_BACKPACK)));
                    if (key == BuiltInLootTables.ABANDONED_MINESHAFT)
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.NORMAL_BACKPACK)));
                    if (key == BuiltInLootTables.NETHER_BRIDGE)
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.TANK_BACKPACK)));
                    if (key == BuiltInLootTables.STRONGHOLD_CORRIDOR)
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.ENDER_CHEST_BACKPACK)));
                    if (key == BuiltInLootTables.STRONGHOLD_LIBRARY)
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.SHRINE_LESS)));
                    if (key == BuiltInLootTables.ANCIENT_CITY)
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.SHRINE_LESS)));
                    if (key == BuiltInLootTables.BASTION_TREASURE)
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.SHRINE_LESS)));
                    if (key == BuiltInLootTables.END_CITY_TREASURE)
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.SHRINE_MORE)));

                    if (key == BuiltInLootTables.BURIED_TREASURE)
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.MAID_BURIED_TREASURE)));

                    if (key == BuiltInLootTables.PILLAGER_OUTPOST)
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.STRUCTURE_SPAWN_MAID_GIFT)));
                    if (key == BuiltInLootTables.WOODLAND_MANSION)
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.STRUCTURE_SPAWN_MAID_GIFT)));

                    if (key == BuiltInLootTables.FISHING_JUNK)
                        builder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableGenerator.FISHING_POWER_POINT)));
                }
        );

        LootTableEvents.MODIFY.addPhaseOrdering(Event.DEFAULT_PHASE, LAST);
        LootTableEvents.MODIFY.register(LAST,
                (key, builder, source, provider) ->  ((ILootTableBuilder) builder).tlm$setId(key.location())
        );
    }
}
