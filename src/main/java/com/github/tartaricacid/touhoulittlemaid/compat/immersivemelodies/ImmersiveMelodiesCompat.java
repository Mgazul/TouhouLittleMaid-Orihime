package com.github.tartaricacid.touhoulittlemaid.compat.immersivemelodies;

import com.github.tartaricacid.touhoulittlemaid.client.animation.HardcodedAnimationManger;
import net.fabricmc.loader.api.FabricLoader;

public class ImmersiveMelodiesCompat {
    private static final String IMMERSIVE_MELODIES = "immersive_melodies";
    private static boolean IS_LOADED = false;

    public static void init() {
        IS_LOADED = FabricLoader.getInstance().isModLoaded(IMMERSIVE_MELODIES);
    }

    public static void addAnimation(HardcodedAnimationManger manger) {
        if (IS_LOADED) {
            manger.addMaidAnimation(new CompatAnimation());
        }
    }
}