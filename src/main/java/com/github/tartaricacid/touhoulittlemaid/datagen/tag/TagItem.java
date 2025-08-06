package com.github.tartaricacid.touhoulittlemaid.datagen.tag;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.github.tartaricacid.touhoulittlemaid.util.ResourceLocationUtil.getResourceLocation;

public class TagItem extends FabricTagProvider<Item> {
    public static final TagKey<Item> GOHEI_ENCHANTABLE = TagKey.create(Registries.ITEM, getResourceLocation("gohei_enchantable"));
    public static final TagKey<Item> MAID_PLANTABLE_SEEDS = TagKey.create(Registries.ITEM, getResourceLocation("maid_plantable_seeds"));
    public static final TagKey<Item> MAID_TAMED_ITEM = TagKey.create(Registries.ITEM, getResourceLocation("maid_tamed_item"));
    public static final TagKey<Item> MAID_MENDING_BLOCKLIST_ITEM = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "maid_mending_blocklist_item"));
    public static final TagKey<Item> MAID_VANISHING_BLOCKLIST_ITEM = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "maid_vanishing_blocklist_item"));

    public TagItem(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, Registries.ITEM, completableFuture);
    }


    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        getOrCreateTagBuilder(GOHEI_ENCHANTABLE).add(InitItems.HAKUREI_GOHEI);
        getOrCreateTagBuilder(GOHEI_ENCHANTABLE).add(InitItems.SANAE_GOHEI);

        getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE).add(InitItems.HAKUREI_GOHEI)
                .add(InitItems.SANAE_GOHEI)
                .add(InitItems.ULTRAMARINE_ORB_ELIXIR)
                .add(InitItems.EXPLOSION_PROTECT_BAUBLE)
                .add(InitItems.FIRE_PROTECT_BAUBLE)
                .add(InitItems.PROJECTILE_PROTECT_BAUBLE)
                .add(InitItems.MAGIC_PROTECT_BAUBLE)
                .add(InitItems.FALL_PROTECT_BAUBLE)
                .add(InitItems.DROWN_PROTECT_BAUBLE)
                .add(InitItems.NIMBLE_FABRIC);

        getOrCreateTagBuilder(MAID_PLANTABLE_SEEDS).forceAddTag(ItemTags.VILLAGER_PLANTABLE_SEEDS);
        getOrCreateTagBuilder(MAID_PLANTABLE_SEEDS).add(Items.NETHER_WART);

        getOrCreateTagBuilder(MAID_TAMED_ITEM)
                .add(Items.CAKE)
                .addOptionalTag(ResourceLocation.parse("forge:cakes"))
                .addOptionalTag(ResourceLocation.parse("c:cakes"))
                .addOptionalTag(ResourceLocation.parse("jmc:cakes"))
                .addOptional(ResourceLocation.parse("kawaiidishes:cheese_cake"))
                .addOptional(ResourceLocation.parse("kawaiidishes:honey_cheese_cake"))
                .addOptional(ResourceLocation.parse("kawaiidishes:chocolate_cheese_cake"))
                .addOptional(ResourceLocation.parse("kawaiidishes:piece_of_cake"))
                .addOptional(ResourceLocation.parse("kawaiidishes:piece_of_cheesecake"))
                .addOptional(ResourceLocation.parse("kawaiidishes:piece_of_chocolate_cheesecake"))
                .addOptional(ResourceLocation.parse("kawaiidishes:piece_of_honey_cheesecake"));

        getOrCreateTagBuilder(MAID_MENDING_BLOCKLIST_ITEM).add(InitItems.ULTRAMARINE_ORB_ELIXIR);
        getOrCreateTagBuilder(MAID_VANISHING_BLOCKLIST_ITEM).add(InitItems.ULTRAMARINE_ORB_ELIXIR);
    }
}
