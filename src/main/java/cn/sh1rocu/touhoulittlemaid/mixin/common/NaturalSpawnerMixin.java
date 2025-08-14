package cn.sh1rocu.touhoulittlemaid.mixin.common;

import cn.sh1rocu.touhoulittlemaid.util.forge.EventHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {
    @Inject(method = "mobsAt", at = @At("HEAD"), cancellable = true)
    private static void tlm$mobsAt(
            ServerLevel level,
            StructureManager structureManager,
            ChunkGenerator generator,
            MobCategory category,
            BlockPos pos,
            Holder<Biome> biome,
            CallbackInfoReturnable<WeightedRandomList<MobSpawnSettings.SpawnerData>> cir
    ) {
        if (NaturalSpawner.isInNetherFortressBounds(pos, level, category, structureManager)) {
            var monsterSpawns = structureManager.registryAccess().registryOrThrow(Registries.STRUCTURE).getOrThrow(BuiltinStructures.FORTRESS).spawnOverrides().get(MobCategory.MONSTER);
            if (monsterSpawns != null) {
                cir.setReturnValue(EventHooks.getPotentialSpawns(level, category, pos, monsterSpawns.spawns()));
            }
        }
        cir.setReturnValue(EventHooks.getPotentialSpawns(level, category, pos, generator.getMobsAt(biome != null ? biome : level.getBiome(pos), structureManager, category, pos)));
    }
}
