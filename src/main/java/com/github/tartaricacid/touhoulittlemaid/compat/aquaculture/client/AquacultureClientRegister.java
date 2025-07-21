package com.github.tartaricacid.touhoulittlemaid.compat.aquaculture.client;

import com.github.tartaricacid.touhoulittlemaid.compat.aquaculture.entity.AquacultureFishingHook;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class AquacultureClientRegister {
    public static void onEntityRenderers() {
        //EntityRendererRegistry.register(AquacultureFishingHook.TYPE, AquacultureFishingHookRenderer::new);
    }
}
