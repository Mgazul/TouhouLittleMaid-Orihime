package com.github.tartaricacid.touhoulittlemaid.api.event.client;

import cn.sh1rocu.touhoulittlemaid.api.event.CancellableEvent;
import com.github.tartaricacid.touhoulittlemaid.api.entity.IMaid;
import com.github.tartaricacid.touhoulittlemaid.client.resource.models.MaidModels;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@Environment(EnvType.CLIENT)
public class RenderMaidEvent extends CancellableEvent {
    private final IMaid maid;
    private final MaidModels.ModelData modelData;

    public RenderMaidEvent(IMaid maid, MaidModels.ModelData modelData) {
        this.maid = maid;
        this.modelData = modelData;
    }

    public IMaid getMaid() {
        return maid;
    }

    public MaidModels.ModelData getModelData() {
        return modelData;
    }

    public static final Event<Callback> CALLBACK = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.post(event);
        }
    });

    public interface Callback {
        void post(RenderMaidEvent e);
    }
}
