package com.github.tartaricacid.touhoulittlemaid;

import cn.sh1rocu.touhoulittlemaid.api.event.EntityJoinLevelEvent;
import com.github.tartaricacid.touhoulittlemaid.api.entity.IMaid;
import com.github.tartaricacid.touhoulittlemaid.client.entity.GeckoMaidEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;

@Environment(EnvType.CLIENT)
public class TouhouLittleMaidClient {
    public static void setup() {
        registerClientOnly();
    }

    private static void registerClientOnly() {
        // 这个仅用于客户端，所以不需要在服务端注册
        EntityJoinLevelEvent.CALLBACK.register(event -> {
            Entity clientEntity = event.getEntity();
            if (clientEntity instanceof Mob mob) {
                IMaid maid = IMaid.convert(mob);
                if (maid != null) {
                    clientEntity.setAttached(GeckoMaidEntity.TYPE, new GeckoMaidEntity(mob, maid));
                }
            }
        });
    }
}
