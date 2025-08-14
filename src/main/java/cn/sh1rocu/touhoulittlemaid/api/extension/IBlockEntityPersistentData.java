package cn.sh1rocu.touhoulittlemaid.api.extension;

import net.minecraft.nbt.CompoundTag;

public interface IBlockEntityPersistentData {
    String PERSISTENT_DATA = "ForgeData";

    default CompoundTag getPersistentData() {
        throw new RuntimeException();
    }
}