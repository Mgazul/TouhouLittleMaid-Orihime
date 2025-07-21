package com.github.tartaricacid.touhoulittlemaid.init;

import com.github.tartaricacid.touhoulittlemaid.data.MaidNumAttachment;
import com.github.tartaricacid.touhoulittlemaid.data.PowerAttachment;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

@SuppressWarnings("UnstableApiUsage")
public class InitDataAttachment {
    public static void init() {

    }

    public static final AttachmentType<MaidNumAttachment> MAID_NUM = MaidNumAttachment.TYPE;
    public static final AttachmentType<PowerAttachment> POWER_NUM = PowerAttachment.TYPE;
}
