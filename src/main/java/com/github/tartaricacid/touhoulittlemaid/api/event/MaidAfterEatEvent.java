package com.github.tartaricacid.touhoulittlemaid.api.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.item.ItemStack;

public class MaidAfterEatEvent {
    private final EntityMaid maid;
    private final ItemStack foodAfterEat;

    public MaidAfterEatEvent(EntityMaid maid, ItemStack foodAfterEat) {
        this.maid = maid;
        this.foodAfterEat = foodAfterEat;
    }

    public EntityMaid getMaid() {
        return maid;
    }

    public ItemStack getFoodAfterEat() {
        return foodAfterEat;
    }

    public static final Event<Callback> CALLBACK = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    });

    public interface Callback {
        void post(MaidAfterEatEvent event);
    }
}
