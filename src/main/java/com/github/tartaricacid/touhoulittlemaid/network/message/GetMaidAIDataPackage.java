package com.github.tartaricacid.touhoulittlemaid.network.message;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import static com.github.tartaricacid.touhoulittlemaid.util.ResourceLocationUtil.getResourceLocation;

public record GetMaidAIDataPackage(int entityId) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<GetMaidAIDataPackage> TYPE = new CustomPacketPayload.Type<>(getResourceLocation("get_maid_ai_data"));
    public static final StreamCodec<RegistryFriendlyByteBuf, GetMaidAIDataPackage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            GetMaidAIDataPackage::entityId,
            GetMaidAIDataPackage::new
    );

    public static void handle(GetMaidAIDataPackage message, ServerPlayNetworking.Context context) {
        context.server().execute(() -> handleInner(message, context));
    }

    private static void handleInner(GetMaidAIDataPackage message, ServerPlayNetworking.Context context) {
        ServerPlayer sender = context.player();
        Entity entity = sender.level.getEntity(message.entityId);
        if (entity instanceof EntityMaid maid && maid.isOwnedBy(sender)) {
            ServerPlayNetworking.send(sender, new OpenMaidAIDataScreenPackage(message.entityId, maid.getAiChatManager()));
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
