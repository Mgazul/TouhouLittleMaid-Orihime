package com.github.tartaricacid.touhoulittlemaid.inventory.container.backpack;

import cn.sh1rocu.touhoulittlemaid.util.itemhandler.SlotItemHandler;
import com.github.tartaricacid.touhoulittlemaid.inventory.container.MaidMainContainer;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

public class SmallBackpackContainer extends MaidMainContainer {
    public static final MenuType<SmallBackpackContainer> TYPE = new ExtendedScreenHandlerType<>(SmallBackpackContainer::new, new StreamCodec<>() {
        @Override
        public Integer decode(RegistryFriendlyByteBuf buf) {
            return buf.readInt();
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, Integer data) {
            buf.writeInt(data);
        }
    });

    public SmallBackpackContainer(int id, Inventory inventory, int entityId) {
        super(TYPE, id, inventory, entityId);
    }

    @Override
    protected void addBackpackInv(Inventory inventory) {
        for (int i = 0; i < 6; i++) {
            addSlot(new SlotItemHandler(maid.getMaidInv(), 6 + i, 143 + 18 * i, 59));
        }
    }
}
