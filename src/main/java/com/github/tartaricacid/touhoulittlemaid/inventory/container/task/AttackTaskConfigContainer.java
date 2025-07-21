package com.github.tartaricacid.touhoulittlemaid.inventory.container.task;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

public class AttackTaskConfigContainer extends TaskConfigContainer {
    public static final MenuType<AttackTaskConfigContainer> TYPE = new ExtendedScreenHandlerType<>(AttackTaskConfigContainer::new, new StreamCodec<>() {
        @Override
        public @NotNull Integer decode(@NotNull RegistryFriendlyByteBuf buf) {
            return buf.readInt();
        }

        @Override
        public void encode(@NotNull RegistryFriendlyByteBuf buf, @NotNull Integer data) {
            buf.writeInt(data);
        }
    });

    public AttackTaskConfigContainer(int id, Inventory inventory, int entityId) {
        super(TYPE, id, inventory, entityId);
    }
}
