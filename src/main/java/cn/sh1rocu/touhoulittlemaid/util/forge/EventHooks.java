package cn.sh1rocu.touhoulittlemaid.util.forge;

import cn.sh1rocu.touhoulittlemaid.api.event.PotentialSpawnsEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class EventHooks {
    private static final WeightedRandomList<MobSpawnSettings.SpawnerData> NO_SPAWNS = WeightedRandomList.create();

    public static WeightedRandomList<MobSpawnSettings.SpawnerData> getPotentialSpawns(LevelAccessor level, MobCategory category, BlockPos pos, WeightedRandomList<MobSpawnSettings.SpawnerData> oldList) {
        PotentialSpawnsEvent event = new PotentialSpawnsEvent(level, category, pos, oldList);
        PotentialSpawnsEvent.CALLBACK.invoker().post(event);
        if (event.isCanceled())
            return NO_SPAWNS;
        else if (event.getSpawnerDataList() == oldList.unwrap())
            return oldList;
        return WeightedRandomList.create(event.getSpawnerDataList());
    }
}
