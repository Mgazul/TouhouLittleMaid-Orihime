package com.github.tartaricacid.touhoulittlemaid.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.network.message.SyncYsmMaidDataMessage;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class MaidTrackEvent {
    public static void onTrackingPlayer(Entity target, Player player) {
        if (target instanceof EntityMaid maid && maid.isYsmModel()) {
            if (player instanceof ServerPlayer serverPlayer) {
                ServerPlayNetworking.send(serverPlayer, SyncYsmMaidDataMessage.ID,
                        SyncYsmMaidDataMessage.encode(maid.getId(), maid.rouletteAnim, maid.rouletteAnimPlaying, maid.roamingVars));
            }
        }
    }
}
