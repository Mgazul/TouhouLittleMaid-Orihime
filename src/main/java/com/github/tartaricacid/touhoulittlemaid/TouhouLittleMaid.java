package com.github.tartaricacid.touhoulittlemaid;

import com.github.tartaricacid.touhoulittlemaid.api.ILittleMaid;
import com.github.tartaricacid.touhoulittlemaid.compat.aquaculture.AquacultureCompat;
import com.github.tartaricacid.touhoulittlemaid.config.GeneralConfig;
import com.github.tartaricacid.touhoulittlemaid.config.ServerConfig;
import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.ChatBubbleManger;
import com.github.tartaricacid.touhoulittlemaid.init.*;
import com.github.tartaricacid.touhoulittlemaid.init.registry.CommandRegistry;
import com.github.tartaricacid.touhoulittlemaid.network.NetworkHandler;
import com.google.common.collect.Lists;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.neoforged.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public final class TouhouLittleMaid {
    public static final String MOD_ID = "touhou_little_maid";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static List<ILittleMaid> EXTENSIONS = Lists.newArrayList();

    public static void commonSetup() {
        initRegister();
        ChatBubbleManger.initDefaultChat();
        registerConfiguration();

        AquacultureCompat.init();
    }

    private static void initRegister() {
        InitEntities.init();
        InitBlocks.init();
        InitItems.init();
        InitCreativeTabs.init();
        InitContainer.init();
        InitSounds.init();
        InitRecipes.init();
        InitCommand.init();
        InitPoi.init();
        InitTrigger.init();
        InitDataAttachment.init();
        InitDataComponent.init();
        InitLootCondition.init();

        NetworkHandler.registerPackets();
        // CCA init by Entrypoint
        //InitCapabilities.registerGenericItemHandlers();

        CommandRegistrationCallback.EVENT.register(
                (dispatcher, context, selection) ->
                        CommandRegistry.onServerStaring(dispatcher));
    }

    private static void registerConfiguration() {
        NeoForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.COMMON, GeneralConfig.getConfigSpec());
        NeoForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.SERVER, ServerConfig.init());
    }
}
