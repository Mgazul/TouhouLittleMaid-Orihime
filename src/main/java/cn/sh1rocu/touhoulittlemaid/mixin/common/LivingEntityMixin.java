package cn.sh1rocu.touhoulittlemaid.mixin.common;

import cn.sh1rocu.touhoulittlemaid.api.extension.IBedBlock;
import cn.sh1rocu.touhoulittlemaid.util.forge.EventHooks;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    public abstract Optional<BlockPos> getSleepingPos();

    @Shadow
    public abstract ItemStack getUseItem();

    @Shadow public abstract int getUseItemRemainingTicks();

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = "startSleeping",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;setPose(Lnet/minecraft/world/entity/Pose;)V"
            )
    )
    private void tlm$startSleeping(BlockPos pos, CallbackInfo ci, @Local BlockState state) {
        if (!(state.getBlock() instanceof BedBlock) && state.getBlock() instanceof IBedBlock bedBlock) {
            if (bedBlock.isBed(state, this.level(), pos, (LivingEntity) (Object) this))
                this.level().setBlock(pos, state.setValue(BedBlock.OCCUPIED, true), 3);
        }
    }

    @Inject(
            method = "checkBedExists",
            at = @At("HEAD"),
            cancellable = true
    )
    private void tlm$checkBedExists(CallbackInfoReturnable<Boolean> cir) {
        Optional<BlockPos> blockPos = this.getSleepingPos();
        if (blockPos.isPresent()) {
            BlockState state = this.level().getBlockState(blockPos.get());
            if (state.getBlock() instanceof IBedBlock bedBlock)
                cir.setReturnValue(bedBlock.isBed(state, this.level(), blockPos.get(), (LivingEntity) (Object) this));
            else cir.setReturnValue(state.getBlock() instanceof BedBlock);
        }
    }

    @Inject(
            method = "stopSleeping",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;position()Lnet/minecraft/world/phys/Vec3;"
            )
    )
    private void tlm$stopSleeping(CallbackInfo ci) {
        Optional<BlockPos> sleepingPos = this.getSleepingPos();
        sleepingPos.filter(this.level()::hasChunkAt).ifPresent((blockPos) -> {
            BlockState blockState = this.level().getBlockState(blockPos);
            if (!(blockState.getBlock() instanceof BedBlock) && blockState.getBlock() instanceof IBedBlock bedBlock) {
                if (bedBlock.isBed(blockState, this.level(), blockPos, (LivingEntity) (Object) this)) {
                    Direction direction = blockState.getValue(BedBlock.FACING);
                    this.level().setBlock(blockPos, blockState.setValue(BedBlock.OCCUPIED, false), 3);
                    Vec3 vec3 = BedBlock.findStandUpPosition(this.getType(), this.level(), blockPos, direction, this.getYRot()).orElseGet(() -> {
                        BlockPos blockPos2 = blockPos.above();
                        return new Vec3((double) blockPos2.getX() + (double) 0.5F, (double) blockPos2.getY() + 0.1, (double) blockPos2.getZ() + (double) 0.5F);
                    });
                    Vec3 vec32 = Vec3.atBottomCenterOf(blockPos).subtract(vec3).normalize();
                    float f = (float) Mth.wrapDegrees(Mth.atan2(vec32.z, vec32.x) * (double) (180F / (float) Math.PI) - (double) 90.0F);
                    this.setPos(vec3.x, vec3.y, vec3.z);
                    this.setYRot(f);
                    this.setXRot(0.0F);
                }
            }
        });
    }

    @Inject(
            method = "getBedOrientation",
            at = @At("HEAD"),
            cancellable = true
    )
    private void tlm$getBedOrientation(CallbackInfoReturnable<Direction> cir) {
        BlockPos blockPos = this.getSleepingPos().orElse(null);
        if (blockPos == null) return;
        BlockState state = this.level().getBlockState(blockPos);
        if (state.getBlock() instanceof IBedBlock bedBlock)
            cir.setReturnValue(!bedBlock.isBed(state, this.level(), blockPos, (LivingEntity) (Object) this) ? Direction.UP : BedBlock.getBedOrientation(this.level(), blockPos));
    }

    @WrapOperation(method = "completeUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;finishUsingItem(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;"))
    public ItemStack tlm$onItemUseFinish(ItemStack instance, Level level, LivingEntity livingEntity, Operation<ItemStack> original) {
        return EventHooks.onItemUseFinish((LivingEntity) (Object) this, this.getUseItem().copy(), this.getUseItemRemainingTicks(), original.call(instance, level, livingEntity));
    }
}
