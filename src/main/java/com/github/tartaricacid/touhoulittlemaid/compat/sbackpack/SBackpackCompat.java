package com.github.tartaricacid.touhoulittlemaid.compat.sbackpack;

import com.github.tartaricacid.touhoulittlemaid.api.event.InteractMaidEvent;

public class SBackpackCompat {
    public static void init() {
        InteractMaidEvent.CALLBACK.register(BackpackRightClickMaidEvent::onClickMaid);
    }
}