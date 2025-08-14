package com.github.tartaricacid.touhoulittlemaid.compat.kaleidoscope;


import com.github.tartaricacid.touhoulittlemaid.compat.kaleidoscope.crop.RiceCropHandler;
import com.github.tartaricacid.touhoulittlemaid.entity.task.crop.SpecialCropManager;
import net.fabricmc.loader.api.FabricLoader;

public class KaleidoscopeCompat {
    public static final String COOKERY_ID = "kaleidoscope_cookery";

    public static void addCropHandlers(SpecialCropManager manager) {
        if (FabricLoader.getInstance().isModLoaded(COOKERY_ID)) {
            RiceCropHandler.addCropHandlers(manager);
        }
    }
}