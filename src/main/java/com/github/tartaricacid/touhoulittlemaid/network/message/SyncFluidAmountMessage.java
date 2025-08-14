package com.github.tartaricacid.touhoulittlemaid.network.message;

import com.github.tartaricacid.touhoulittlemaid.entity.backpack.data.TankBackpackData;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import static cn.sh1rocu.touhoulittlemaid.TouhouLittleMaidFabric.getResourceLocation;

public class SyncFluidAmountMessage {
    public static final ResourceLocation ID = getResourceLocation("client_sync_fluid_amount");

    public static FriendlyByteBuf encode(int entityId, int amount) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeInt(entityId);
        buf.writeVarInt(amount);
        return buf;
    }

    public static void handle(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int entityId = buf.readInt();
        int amount = buf.readVarInt();
        client.execute(() -> {
            LocalPlayer player = client.player;
            if (player != null && player.level.getEntity(entityId) instanceof EntityMaid maid) {
                if (maid.getBackpackData() instanceof TankBackpackData tankBackpackData) {
                    tankBackpackData.getDataAccess().set(0, amount);
                }
            }
        });
    }
}
