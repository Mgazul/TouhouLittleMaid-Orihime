package com.github.tartaricacid.touhoulittlemaid.network;

import cn.sh1rocu.touhoulittlemaid.util.forge.network.AdvancedAddEntityPayload;
import com.github.tartaricacid.touhoulittlemaid.network.message.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class NetworkHandler {
    private static <T extends CustomPacketPayload> void registerC2SPacket(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec, ServerPlayNetworking.PlayPayloadHandler<T> handler) {
        PayloadTypeRegistry.playC2S().register(type, streamCodec);
        ServerPlayNetworking.registerGlobalReceiver(type, handler);
    }

    @Environment(EnvType.CLIENT)
    private static <T extends CustomPacketPayload> void registerS2CPacket(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec, ClientPlayNetworking.PlayPayloadHandler<T> handler) {
        PayloadTypeRegistry.playS2C().register(type, streamCodec);
        ClientPlayNetworking.registerGlobalReceiver(type, handler);
    }

    @Environment(EnvType.CLIENT)
    public static void registerS2CPackets() {
        registerS2CPacket(OpenChairGuiPackage.TYPE, OpenChairGuiPackage.STREAM_CODEC, OpenChairGuiPackage::handle);
        registerS2CPacket(ItemBreakPackage.TYPE, ItemBreakPackage.STREAM_CODEC, ItemBreakPackage::handle);
        registerS2CPacket(SpawnParticlePackage.TYPE, SpawnParticlePackage.STREAM_CODEC, SpawnParticlePackage::handle);
        registerS2CPacket(SyncDataPackage.TYPE, SyncDataPackage.STREAM_CODEC, SyncDataPackage::handle);
        registerS2CPacket(OpenBeaconGuiPackage.TYPE, OpenBeaconGuiPackage.STREAM_CODEC, OpenBeaconGuiPackage::handle);
        registerS2CPacket(BeaconAbsorbPackage.TYPE, BeaconAbsorbPackage.STREAM_CODEC, BeaconAbsorbPackage::handle);
        registerS2CPacket(OpenSwitcherGuiPackage.TYPE, OpenSwitcherGuiPackage.STREAM_CODEC, OpenSwitcherGuiPackage::handle);
        registerS2CPacket(SendEffectPackage.TYPE, SendEffectPackage.STREAM_CODEC, SendEffectPackage::handle);
        registerS2CPacket(PlayMaidSoundPackage.TYPE, PlayMaidSoundPackage.STREAM_CODEC, PlayMaidSoundPackage::handle);
        registerS2CPacket(GomokuClientPackage.TYPE, GomokuClientPackage.STREAM_CODEC, GomokuClientPackage::handle);
        registerS2CPacket(FoxScrollPackage.TYPE, FoxScrollPackage.STREAM_CODEC, FoxScrollPackage::handle);
        registerS2CPacket(CheckSchedulePosPacket.TYPE, CheckSchedulePosPacket.STREAM_CODEC, CheckSchedulePosPacket::handle);
        registerS2CPacket(SyncMaidAreaPackage.TYPE, SyncMaidAreaPackage.STREAM_CODEC, SyncMaidAreaPackage::handle);
        registerS2CPacket(CChessToClientPackage.TYPE, CChessToClientPackage.STREAM_CODEC, CChessToClientPackage::handle);
        registerS2CPacket(WChessToClientPackage.TYPE, WChessToClientPackage.STREAM_CODEC, WChessToClientPackage::handle);
        registerS2CPacket(TTSAudioToClientPackage.TYPE, TTSAudioToClientPackage.STREAM_CODEC, TTSAudioToClientPackage::handle);
        registerS2CPacket(SyncAiSettingPackage.TYPE, SyncAiSettingPackage.STREAM_CODEC, SyncAiSettingPackage::handle);
        registerS2CPacket(OpenMaidAIDataScreenPackage.TYPE, OpenMaidAIDataScreenPackage.STREAM_CODEC, OpenMaidAIDataScreenPackage::handle);
        // 仅安装 YSM 后才会发送此包
        registerS2CPacket(SyncYsmMaidDataPackage.TYPE, SyncYsmMaidDataPackage.STREAM_CODEC, SyncYsmMaidDataPackage::handle);
        registerS2CPacket(TTSSystemAudioToClientPackage.TYPE, TTSSystemAudioToClientPackage.STREAM_CODEC, TTSSystemAudioToClientPackage::handle);

        registerS2CPacket(AdvancedAddEntityPayload.TYPE, AdvancedAddEntityPayload.STREAM_CODEC, AdvancedAddEntityPayload::handle);

        registerS2CPacket(SyncFluidAmountPackage.TYPE, SyncFluidAmountPackage.STREAM_CODEC, SyncFluidAmountPackage::handle);
    }

    public static void registerC2SPackets() {
        registerC2SPacket(MaidModelPackage.TYPE, MaidModelPackage.STREAM_CODEC, MaidModelPackage::handle);
        registerC2SPacket(ChairModelPackage.TYPE, ChairModelPackage.STREAM_CODEC, ChairModelPackage::handle);
        registerC2SPacket(MaidConfigPackage.TYPE, MaidConfigPackage.STREAM_CODEC, MaidConfigPackage::handle);
        registerC2SPacket(MaidTaskPackage.TYPE, MaidTaskPackage.STREAM_CODEC, MaidTaskPackage::handle);
        registerC2SPacket(SendNameTagPackage.TYPE, SendNameTagPackage.STREAM_CODEC, SendNameTagPackage::handle);
        registerC2SPacket(WirelessIOGuiPackage.TYPE, WirelessIOGuiPackage.STREAM_CODEC, WirelessIOGuiPackage::handle);
        registerC2SPacket(WirelessIOSlotConfigPackage.TYPE, WirelessIOSlotConfigPackage.STREAM_CODEC, WirelessIOSlotConfigPackage::handle);
        registerC2SPacket(SetBeaconPotionPackage.TYPE, SetBeaconPotionPackage.STREAM_CODEC, SetBeaconPotionPackage::handle);
        registerC2SPacket(StorageAndTakePowerPackage.TYPE, StorageAndTakePowerPackage.STREAM_CODEC, StorageAndTakePowerPackage::handle);
        registerC2SPacket(SetBeaconOverflowPackage.TYPE, SetBeaconOverflowPackage.STREAM_CODEC, SetBeaconOverflowPackage::handle);
        registerC2SPacket(SaveSwitcherDataPackage.TYPE, SaveSwitcherDataPackage.STREAM_CODEC, SaveSwitcherDataPackage::handle);
        registerC2SPacket(ToggleTabPackage.TYPE, ToggleTabPackage.STREAM_CODEC, ToggleTabPackage::handle);
        registerC2SPacket(RequestEffectPackage.TYPE, RequestEffectPackage.STREAM_CODEC, RequestEffectPackage::handle);
        registerC2SPacket(SetMaidSoundIdPackage.TYPE, SetMaidSoundIdPackage.STREAM_CODEC, SetMaidSoundIdPackage::handle);
        registerC2SPacket(GomokuServerPackage.TYPE, GomokuServerPackage.STREAM_CODEC, GomokuServerPackage::handle);
        registerC2SPacket(SetScrollPackage.TYPE, SetScrollPackage.STREAM_CODEC, SetScrollPackage::handle);
        registerC2SPacket(ServantBellSetPackage.TYPE, ServantBellSetPackage.STREAM_CODEC, ServantBellSetPackage::handle);
        registerC2SPacket(SetAttackListPackage.TYPE, SetAttackListPackage.STREAM_CODEC, SetAttackListPackage::handle);
        registerC2SPacket(RefreshMaidBrainPackage.TYPE, RefreshMaidBrainPackage.STREAM_CODEC, RefreshMaidBrainPackage::handle);
        registerC2SPacket(MaidSubConfigPackage.TYPE, MaidSubConfigPackage.STREAM_CODEC, MaidSubConfigPackage::handle);
        registerC2SPacket(CChessToServerPackage.TYPE, CChessToServerPackage.STREAM_CODEC, CChessToServerPackage::handle);
        registerC2SPacket(WChessToServerPackage.TYPE, WChessToServerPackage.STREAM_CODEC, WChessToServerPackage::handle);
        registerC2SPacket(SendUserChatPackage.TYPE, SendUserChatPackage.STREAM_CODEC, SendUserChatPackage::handle);
        // 仅安装 YSM 后才会发送此包
        registerC2SPacket(YsmMaidModelPackage.TYPE, YsmMaidModelPackage.STREAM_CODEC, YsmMaidModelPackage::handle);
        registerC2SPacket(SaveMaidAIDataPackage.TYPE, SaveMaidAIDataPackage.STREAM_CODEC, SaveMaidAIDataPackage::handle);
        registerC2SPacket(GetMaidAIDataPackage.TYPE, GetMaidAIDataPackage.STREAM_CODEC, GetMaidAIDataPackage::handle);

    }

    public static void sendToNearby(Entity entity, CustomPacketPayload toSend) {
        if (entity.level instanceof ServerLevel) {
            for (ServerPlayer target : PlayerLookup.tracking(entity)) {
                ServerPlayNetworking.send(target, toSend);
            }
        }
    }

    public static void sendToNearby(Entity entity, CustomPacketPayload toSend, int distance) {
        if (entity.level instanceof ServerLevel serverLevel) {
            BlockPos pos = entity.blockPosition();
            for (ServerPlayer target : PlayerLookup.around(serverLevel, new Vec3i(pos.getX(), pos.getY(), pos.getZ()), distance)) {
                ServerPlayNetworking.send(target, toSend);
            }
        }
    }
}
