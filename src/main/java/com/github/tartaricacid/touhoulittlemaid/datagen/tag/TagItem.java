package com.github.tartaricacid.touhoulittlemaid.datagen.tag;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class TagItem extends FabricTagProvider<Item> {
    public static final TagKey<Item> MAID_TAMED_ITEM = createTagKey("maid_tamed_item");
    public static final TagKey<Item> MAID_MENDING_BLOCKLIST_ITEM = createTagKey("maid_mending_blocklist_item");
    public static final TagKey<Item> MAID_VANISHING_BLOCKLIST_ITEM = createTagKey("maid_vanishing_blocklist_item");
    /**
     * 女仆进食黑名单，与配置文件协同作用，方便拓展兼容
     * <p>
     * 全局的，适用于工作餐、回血餐和家庭餐
     */
    public static final TagKey<Item> MAID_EAT_BLOCKLIST_ITEM = createTagKey("maid_eat_blocklist_item");

    public TagItem(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, Registries.ITEM, completableFuture);
    }

    private static TagKey<Item> createTagKey(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(TouhouLittleMaid.MOD_ID, name));
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        getOrCreateTagBuilder(MAID_TAMED_ITEM)
                .add(Items.CAKE)
                .addOptionalTag(new ResourceLocation("forge:cakes"))
                .addOptionalTag(new ResourceLocation("c:cakes"))
                .addOptionalTag(new ResourceLocation("jmc:cakes"))
                .addOptional(new ResourceLocation("kawaiidishes:cheese_cake"))
                .addOptional(new ResourceLocation("kawaiidishes:honey_cheese_cake"))
                .addOptional(new ResourceLocation("kawaiidishes:chocolate_cheese_cake"))
                .addOptional(new ResourceLocation("kawaiidishes:piece_of_cake"))
                .addOptional(new ResourceLocation("kawaiidishes:piece_of_cheesecake"))
                .addOptional(new ResourceLocation("kawaiidishes:piece_of_chocolate_cheesecake"))
                .addOptional(new ResourceLocation("kawaiidishes:piece_of_honey_cheesecake"));

        getOrCreateTagBuilder(MAID_MENDING_BLOCKLIST_ITEM).add(InitItems.ULTRAMARINE_ORB_ELIXIR);
        getOrCreateTagBuilder(MAID_VANISHING_BLOCKLIST_ITEM).add(InitItems.ULTRAMARINE_ORB_ELIXIR);

        // 森罗物语辣椒
        getOrCreateTagBuilder(MAID_EAT_BLOCKLIST_ITEM)
                .addOptional(new ResourceLocation("kaleidoscope_cookery:red_chili"))
                .addOptional(new ResourceLocation("kaleidoscope_cookery:green_chili"));
    }
}
