package com.github.tartaricacid.touhoulittlemaid.datagen.tag;

import com.github.tartaricacid.touhoulittlemaid.init.InitDamage;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;

import java.util.concurrent.CompletableFuture;

public class DamageTypeGenerator extends DamageTypeTagsProvider {
    public DamageTypeGenerator(FabricDataOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider) {
        super(pOutput, pLookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(DamageTypeTags.IS_PROJECTILE).add(InitDamage.DANMAKU);
        tag(DamageTypeTags.ALWAYS_HURTS_ENDER_DRAGONS)
                .add(InitDamage.DANMAKU_ENDER_KILLER)
                .addOptional(new ResourceLocation("tacz", "bullet"));
    }
}
