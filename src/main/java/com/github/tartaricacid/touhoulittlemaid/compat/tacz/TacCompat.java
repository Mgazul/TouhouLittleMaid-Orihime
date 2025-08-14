package com.github.tartaricacid.touhoulittlemaid.compat.tacz;

import com.github.tartaricacid.touhoulittlemaid.api.entity.IMaid;
import com.github.tartaricacid.touhoulittlemaid.client.animation.script.ModelRendererWrapper;
import com.github.tartaricacid.touhoulittlemaid.client.entity.GeckoMaidEntity;
import com.github.tartaricacid.touhoulittlemaid.entity.task.TaskManager;
import com.github.tartaricacid.touhoulittlemaid.geckolib3.core.PlayState;
import com.github.tartaricacid.touhoulittlemaid.geckolib3.core.builder.ILoopType;
import com.github.tartaricacid.touhoulittlemaid.geckolib3.core.event.predicate.AnimationEvent;
import com.github.tartaricacid.touhoulittlemaid.geckolib3.geo.animated.ILocationModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class TacCompat {
    public static final ResourceLocation MINIGUN_ID = new ResourceLocation("tacz", "minigun");
    private static final String TACZ_ID = "tacz";
    private static boolean INSTALLED = false;

    public static void initAndAddGunTask(TaskManager manager) {
        if (FabricLoader.getInstance().isModLoaded(TACZ_ID)) {
//            MinecraftForge.EVENT_BUS.register(new GunHurtMaidEvent());
//            MinecraftForge.EVENT_BUS.register(new MaidGunEquipEvent());
//            manager.add(new TaskGunAttack());
            INSTALLED = true;
        }
    }

    public static boolean isInstalled() {
        return INSTALLED;
    }

    public static boolean isGun(ItemStack stack) {
        if (INSTALLED) {
            // return TacInnerCompat.isGun(stack);
        }
        return false;
    }

    public static boolean isGrenade(ItemStack itemStack) {
        // TODO 手雷还没有
        return false;
    }

    @Nullable
    public static ResourceLocation getGunId(ItemStack stack) {
        if (INSTALLED) {
            // return TacInnerCompat.getGunId(stack);
        }
        return null;
    }

    @Environment(EnvType.CLIENT)
    public static boolean onHoldGun(IMaid maid, @Nullable ModelRendererWrapper armLeft, @Nullable ModelRendererWrapper armRight) {
        if (INSTALLED) {
            // return GunBaseAnimation.onHoldGun(maid, armLeft, armRight);
        }
        return false;
    }

    @Environment(EnvType.CLIENT)
    public static void addItemTranslate(PoseStack matrixStack, ItemStack itemStack, boolean isLeft) {
        if (INSTALLED) {
            // GunMaidRender.addItemTranslate(matrixStack, itemStack, isLeft);
        }
    }

    @Environment(EnvType.CLIENT)
    public static void renderBackGun(PoseStack matrixStack, MultiBufferSource bufferIn, int packedLightIn, ItemStack stack, IMaid maid) {
        if (INSTALLED) {
            // GunMaidRender.renderBackGun(matrixStack, bufferIn, packedLightIn, stack, maid);
        }
    }

    @Environment(EnvType.CLIENT)
    public static void renderBackGun(ItemStack offhandItem, ILocationModel geoModel, IMaid maid, PoseStack poseStack, MultiBufferSource bufferIn, int packedLight) {
        if (INSTALLED && isGun(offhandItem)) {
            poseStack.pushPose();
            // GunMaidRender.renderBackGun(offhandItem, geoModel, maid, poseStack, bufferIn, packedLight);
            poseStack.popPose();
        }
    }

    @Environment(EnvType.CLIENT)
    @Nullable
    public static PlayState playGunMainAnimation(IMaid maid, AnimationEvent<GeckoMaidEntity<?>> event, String animationName, ILoopType loopType) {
        if (INSTALLED && isGun(maid.asEntity().getMainHandItem())) {
            // return GunGeckoAnimation.playGunMainAnimation(event, animationName, loopType);
        }
        return null;
    }

    @Environment(EnvType.CLIENT)
    @Nullable
    public static PlayState playGunHoldAnimation(ItemStack mainHandItem, AnimationEvent<GeckoMaidEntity<?>> event) {
        if (INSTALLED && isGun(mainHandItem)) {
            // return GunGeckoAnimation.playGunHoldAnimation(event, mainHandItem);
        }
        return null;
    }
}