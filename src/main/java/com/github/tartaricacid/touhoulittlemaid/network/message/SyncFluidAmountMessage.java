package com.github.tartaricacid.touhoulittlemaid.network.message;

import com.github.tartaricacid.touhoulittlemaid.inventory.container.backpack.TankBackpackContainer;
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

    public static FriendlyByteBuf encode(int amount) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeVarInt(amount);
        return buf;
    }

    public static void handle(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int amount = buf.readVarInt();
        client.execute(() -> {
            LocalPlayer player = client.player;
            if (player != null && player.containerMenu instanceof TankBackpackContainer tankBackpackContainer) {
                tankBackpackContainer.setClientFluidCount(amount);
            }
        });
    }
}
