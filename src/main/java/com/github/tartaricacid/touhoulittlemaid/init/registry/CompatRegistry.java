package com.github.tartaricacid.touhoulittlemaid.init.registry;

import com.github.tartaricacid.touhoulittlemaid.compat.patchouli.PatchouliCompat;
import com.github.tartaricacid.touhoulittlemaid.compat.sbackpack.SBackpackCompat;
import net.fabricmc.loader.api.FabricLoader;

public final class CompatRegistry {
    public static final String TOP = "theoneprobe";
    public static final String PATCHOULI = "patchouli";
    //public static final String CLOTH_CONFIG = "cloth_config";
    // 为什么Fabric端的id要改（
    public static final String CLOTH_CONFIG = "cloth-config";
    public static final String CARRY_ON_ID = "carryon";
    public static final String SBACKPACK = "sophisticatedbackpacks";

    public static void onEnqueue() {
/*        event.enqueueWork(() -> checkModLoad(TOP, () -> InterModComms.sendTo(TOP, "getTheOneProbe", TheOneProbeInfo::new)));
        event.enqueueWork(() -> checkModLoad(PATCHOULI, PatchouliCompat::init));
        event.enqueueWork(() -> checkModLoad(CARRY_ON_ID, BlackList::addBlackList));*/
        checkModLoad(PATCHOULI, PatchouliCompat::init);
        checkModLoad(SBACKPACK, SBackpackCompat::init);
    }

    private static void checkModLoad(String modId, Runnable runnable) {
        if (FabricLoader.getInstance().isModLoaded(modId)) {
            runnable.run();
        }
    }
}
