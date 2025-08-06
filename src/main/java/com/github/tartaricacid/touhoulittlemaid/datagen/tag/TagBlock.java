package com.github.tartaricacid.touhoulittlemaid.datagen.tag;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class TagBlock extends FabricTagProvider<Block> {
    public static final TagKey<Block> MAID_JUMP_FORBIDDEN_BLOCK = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "maid_jump_forbidden_block"));

    public static final TagKey<Block> ALTAR_TORII = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "altar_torii"));
    public static final TagKey<Block> ALTAR_PILLAR = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "altar_pillar"));

    public TagBlock(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.BLOCK, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(MAID_JUMP_FORBIDDEN_BLOCK)
                .forceAddTag(BlockTags.DOORS)
                .forceAddTag(BlockTags.FENCES)
                .forceAddTag(BlockTags.CLIMBABLE);

        getOrCreateTagBuilder(ALTAR_TORII).add(Blocks.RED_WOOL,Blocks.RED_CONCRETE).addOptional(ResourceLocation.parse("biomesoplenty:redwood_planks"));
        getOrCreateTagBuilder(ALTAR_PILLAR).forceAddTag(BlockTags.LOGS);
    }
}
