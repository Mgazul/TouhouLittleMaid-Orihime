package com.github.tartaricacid.touhoulittlemaid.inventory.container.backpack;

import com.github.tartaricacid.touhoulittlemaid.inventory.container.MaidMainContainer;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

public class EmptyBackpackContainer extends MaidMainContainer {
    public static final MenuType<EmptyBackpackContainer> TYPE = new ExtendedScreenHandlerType<>(EmptyBackpackContainer::new, new StreamCodec<>() {
        @Override
        public Integer decode(RegistryFriendlyByteBuf buf) {
            return buf.readInt();
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, Integer data) {
            buf.writeInt(data);
        }
    });

    public EmptyBackpackContainer(int id, Inventory inventory, int entityId) {
        super(TYPE, id, inventory, entityId);
    }

    @Override
    protected void addBackpackInv(Inventory inventory) {
    }
}