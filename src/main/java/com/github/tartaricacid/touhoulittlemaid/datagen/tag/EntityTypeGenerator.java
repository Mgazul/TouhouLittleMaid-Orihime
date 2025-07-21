package com.github.tartaricacid.touhoulittlemaid.datagen.tag;

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
    public EntityTypeGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.ENTITY_TYPE, lookupProvider);
    }

    @Override
    public void addTags(HolderLookup.Provider lookupProvider) {
        getOrCreateTagBuilder(EntityTypeTags.IMPACT_PROJECTILES).add(InitEntities.DANMAKU);
        getOrCreateTagBuilder(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS).add(InitEntities.FAIRY);
        getOrCreateTagBuilder(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add(InitEntities.FAIRY);
        getOrCreateTagBuilder(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(InitEntities.FAIRY);
        getOrCreateTagBuilder(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("iceandfire", "immune_to_gorgon_stone")))
                .add(InitEntities.MAID);
    }
}
