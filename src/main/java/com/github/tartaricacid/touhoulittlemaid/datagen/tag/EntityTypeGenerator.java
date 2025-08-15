package com.github.tartaricacid.touhoulittlemaid.datagen.tag;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitEntities;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public class EntityTypeGenerator extends FabricTagProvider<EntityType<?>> {
    public static TagKey<EntityType<?>> MAID_FAIRY_ATTACK_GOAL = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "maid_fairy_attack_goal"));
    public static TagKey<EntityType<?>> MAID_VEHICLE_ROTATE_BLOCKLIST = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "maid_vehicle_rotate_blocklist"));
    public static TagKey<EntityType<?>> CARRYON_ENTITY_BLACKLIST = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("carryon", "entity_blacklist"));

    public EntityTypeGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.ENTITY_TYPE, lookupProvider);
    }

    @Override
    public void addTags(HolderLookup.Provider lookupProvider) {
        getOrCreateTagBuilder(EntityTypeTags.IMPACT_PROJECTILES).add(InitEntities.DANMAKU);
        getOrCreateTagBuilder(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS).add(InitEntities.FAIRY);
        getOrCreateTagBuilder(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add(InitEntities.FAIRY);
        getOrCreateTagBuilder(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(InitEntities.FAIRY);
        getOrCreateTagBuilder(TagKey.create(Registries.ENTITY_TYPE, id("iceandfire:immune_to_gorgon_stone"))).add(InitEntities.MAID);

        getOrCreateTagBuilder(MAID_FAIRY_ATTACK_GOAL).add(EntityType.IRON_GOLEM)
                .addOptional(id("guardvillagers:guard"))
                .addOptional(id("earthtojavamobs:furnace_golem"))
                .addOptional(id("earthmobsmod:furnace_golem"))
                .addOptional(id("mutantmonsters:mutant_snow_golem"))
                .addOptional(id("alexscaves:gingerbread_man"))
                .addOptional(id("alexsmobs:bunfungus"));

        getOrCreateTagBuilder(MAID_VEHICLE_ROTATE_BLOCKLIST).addOptional(id("create:carriage_contraption"));
        getOrCreateTagBuilder(CARRYON_ENTITY_BLACKLIST).add(
                InitEntities.TOMBSTONE,
                InitEntities.SIT,
                InitEntities.BROOM);
    }

    private ResourceLocation id(String name) {
        return ResourceLocation.parse(name);
    }
}
