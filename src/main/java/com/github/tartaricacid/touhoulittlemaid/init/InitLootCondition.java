package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.loot.LootTableTypeCondition;
import com.github.tartaricacid.touhoulittlemaid.loot.SetTankCountFunction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class InitLootCondition {
    public static final ResourceLocation UNKNOWN_LOOT_TABLE = ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "unknown_loot_table");

    public static void init() {
    }

    public static final LootItemConditionType LOOT_TABLE_TYPE = register("loot_table_type", new LootItemConditionType(LootTableTypeCondition.CODEC));

    public static final LootItemFunctionType<? extends LootItemConditionalFunction> SET_TANK_COUNT_FUNCTION = register("set_tank_count", new LootItemFunctionType<>(SetTankCountFunction.CODEC));

    private static LootItemConditionType register(String id, LootItemConditionType condition) {
        return Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id), condition);
    }

    private static <T extends LootItemConditionalFunction> LootItemFunctionType<T> register(String id, LootItemFunctionType<T> function) {
        return Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, id), function);
    }
}