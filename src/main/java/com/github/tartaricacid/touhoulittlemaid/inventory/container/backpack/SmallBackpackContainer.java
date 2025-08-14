package com.github.tartaricacid.touhoulittlemaid.inventory.container.backpack;

import cn.sh1rocu.touhoulittlemaid.util.itemhandler.SlotItemHandler;
import com.github.tartaricacid.touhoulittlemaid.inventory.container.MaidMainContainer;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

public class SmallBackpackContainer extends MaidMainContainer {
    public static final MenuType<SmallBackpackContainer> TYPE = new ExtendedScreenHandlerType<>(
            (id, inv, data) -> new SmallBackpackContainer(id, inv, data.readInt())
    );

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
