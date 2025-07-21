package cn.sh1rocu.touhoulittlemaid.client;

import cn.sh1rocu.touhoulittlemaid.api.event.*;
import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaidClient;
import com.github.tartaricacid.touhoulittlemaid.api.event.InteractMaidEvent;
import com.github.tartaricacid.touhoulittlemaid.api.event.client.RenderMaidEvent;
import com.github.tartaricacid.touhoulittlemaid.client.download.InfoGetManager;
import com.github.tartaricacid.touhoulittlemaid.client.event.*;
import com.github.tartaricacid.touhoulittlemaid.client.init.*;
import com.github.tartaricacid.touhoulittlemaid.client.input.STTChatKey;
import com.github.tartaricacid.touhoulittlemaid.event.ClientExtensionsEvent;
import com.github.tartaricacid.touhoulittlemaid.event.maid.UseNameTagEvent;
import com.github.tartaricacid.touhoulittlemaid.network.NetworkHandler;
import com.github.tartaricacid.touhoulittlemaid.util.EntityCacheUtil;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeModConfigEvents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.entity.GlowSquid;

public class TouhouLittleMaidFabricClient implements ClientModInitializer {
    private static final ResourceLocation HIGHEST = ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "event_highest_priority");
    // NORMAL用Fabric的DEFAULT
    //private static final ResourceLocation NORMAL = ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "event_normal_priority");
    private static final ResourceLocation LOW = ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "event_low_priority");
    private static final ResourceLocation LOW_EST = ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID, "event_lowest_priority");

    @Override
    public void onInitializeClient() {
        TouhouLittleMaidClient.setup();
        NetworkHandler.registerS2CPackets();
        ClientExtensionsEvent.RegisterClientExtensions();
        InfoGetManager.onClientSetup();
        ItemTooltipCallback.EVENT.register(AddInformationEvent::onRenderTooltips);
        RenderHandEvent.CALLBACK.register(CarryMaidHideArmEvent::onRenderHandEvent);
        NeoForgeModConfigEvents.loading(TouhouLittleMaid.MOD_ID).register(ClientPackDownloadEvent::onLoadingConfig);
        NeoForgeModConfigEvents.reloading(TouhouLittleMaid.MOD_ID).register(ClientPackDownloadEvent::onReloadingConfig);
        WorldRenderEvents.AFTER_ENTITIES.register(CompassRenderEvent::onRender);
        WorldRenderEvents.AFTER_ENTITIES.register(MaidAreaRenderEvent::onRender);
        InteractMaidEvent.CALLBACK.register(UseNameTagEvent::onInteractClient);
        PlayerLoggedInEvent.CALLBACK.register(PlayerLoggedInNotice::onEnterGame);
        PlaySoundEvent.CALLBACK.register(MaidSoundFreqEvent::onPlaySoundEvent);
        PlaySoundSourceEvent.CALLBACK.register(PlayMaidSoundEvent::onPlaySoundSource);
        KeyInputCallback.EVENT.register(PressAIChatKeyEvent::onOpenConfig);
        KeyInputCallback.EVENT.register(STTChatKey::onSttChatPress);
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new ReloadResourceEvent());
        WorldRenderEvents.AFTER_TRANSLUCENT.register(ScrollRenderEvent::onRenderWorldLastEvent);
        ScreenEvents.AFTER_INIT.register(ShowOptifineScreen::showOptifineWarning);
        RenderMaidEvent.CALLBACK.addPhaseOrdering(HIGHEST, Event.DEFAULT_PHASE);
        RenderMaidEvent.CALLBACK.addPhaseOrdering(Event.DEFAULT_PHASE, LOW);
        RenderMaidEvent.CALLBACK.addPhaseOrdering(LOW, LOW_EST);
        RenderMaidEvent.CALLBACK.register(HIGHEST, SpecialMaidRenderEvent::onRenderPlayerNamedMaid);
        RenderMaidEvent.CALLBACK.register(Event.DEFAULT_PHASE, SpecialMaidRenderEvent::onRenderEncryptNamedMaid);
        RenderMaidEvent.CALLBACK.register(LOW, SpecialMaidRenderEvent::onRenderNormalNamedMaid);
        RenderMaidEvent.CALLBACK.register(LOW_EST, SpecialMaidRenderEvent::onRenderEasterEggModel);
        WorldRenderEvents.AFTER_ENTITIES.register(WirelessIORenderEvent::onRender);
        ClientSetupEvent.onClientSetup();
        ClientSetupEvent.RegisterGuiLayers();
        TooltipComponentCallback.EVENT.register(InitClientTooltip::onRegisterClientTooltip);
        InitContainerGui.clientSetup();
        InitEntitiesRender.onEntityRenderers();
        InitEntitiesRender.onRegisterLayers();
        ModelLoadingPlugin.register(new InitSpecialItemRender());
        EntityJoinLevelEvent.CALLBACK.register(EntityCacheUtil::onChangeDim);
    }
}
