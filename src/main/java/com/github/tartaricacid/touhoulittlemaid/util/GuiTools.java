package com.github.tartaricacid.touhoulittlemaid.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.Button;

@Environment(EnvType.CLIENT)
public final class GuiTools {
    public static final Button.OnPress NO_ACTION = (button) -> {
    };
}
