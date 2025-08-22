package cn.sh1rocu.touhoulittlemaid.mixin.compat.tacz;

import cn.sh1rocu.touhoulittlemaid.util.itemhandler.IItemHandler;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.item.IAmmo;
import com.tacz.guns.api.item.IAmmoBox;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.entity.shooter.LivingEntityReload;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityReload.class)
public class LivingEntityReloadMixin {
    @Inject(
            method = "inventoryHasAmmo",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;tacz$getItemHandler(Lnet/minecraft/core/Direction;)Lcom/tacz/guns/util/LazyOptional;"
            ),
            cancellable = true
    )
    private static void tlm$onInventoryHasAmmo(LivingEntity shooter, int currentAmmoCount, int maxAmmoCount, ItemStack currentGunItem, IGun iGun, CallbackInfoReturnable<Boolean> cir) {
        if (shooter instanceof EntityMaid maid) {
            var cap = maid.getAllInv();
            for (int i = 0; i < cap.getSlots(); i++) {
                ItemStack checkAmmoStack = cap.getStackInSlot(i);
                if (checkAmmoStack.getItem() instanceof IAmmo iAmmo && iAmmo.isAmmoOfGun(currentGunItem, checkAmmoStack)) {
                    cir.setReturnValue(true);
                    return;
                }
                if (checkAmmoStack.getItem() instanceof IAmmoBox iAmmoBox && iAmmoBox.isAmmoBoxOfGun(currentGunItem, checkAmmoStack)) {
                    cir.setReturnValue(true);
                    return;
                }
            }
        }
    }

    @Inject(
            method = "getAndExtractNeedAmmoCount",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;tacz$getItemHandler(Lnet/minecraft/core/Direction;)Lcom/tacz/guns/util/LazyOptional;"
            ),
            cancellable = true
    )
    private static void tlm$getAndExtractNeedAmmoCount(LivingEntity shooter, ItemStack currentGunItem, IGun iGun, int maxAmmoCount, CallbackInfoReturnable<Integer> cir) {
        if (shooter instanceof EntityMaid maid) {
            var cap = maid.getAllInv();
            int currentAmmoCount = iGun.getCurrentAmmoCount(currentGunItem);
            cir.setReturnValue(tlm$getAndExtractInventoryAmmoCount(cap, maxAmmoCount, currentAmmoCount, currentGunItem));
        }
    }

    @Unique
    private static int tlm$getAndExtractInventoryAmmoCount(IItemHandler itemHandler, int maxAmmoCount, int currentAmmoCount, ItemStack currentGunItem) {
        // 子弹数量检查
        int needAmmoCount = maxAmmoCount - currentAmmoCount;
        // 背包检查
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack checkAmmoStack = itemHandler.getStackInSlot(i);
            if (checkAmmoStack.getItem() instanceof IAmmo iAmmo && iAmmo.isAmmoOfGun(currentGunItem, checkAmmoStack)) {
                ItemStack extractItem = itemHandler.extractItem(i, needAmmoCount, false);
                needAmmoCount = needAmmoCount - extractItem.getCount();
                if (needAmmoCount <= 0) {
                    break;
                }
            }
            if (checkAmmoStack.getItem() instanceof IAmmoBox iAmmoBox && iAmmoBox.isAmmoBoxOfGun(currentGunItem, checkAmmoStack)) {
                int boxAmmoCount = iAmmoBox.getAmmoCount(checkAmmoStack);
                int extractCount = Math.min(boxAmmoCount, needAmmoCount);
                int remainCount = boxAmmoCount - extractCount;
                iAmmoBox.setAmmoCount(checkAmmoStack, remainCount);
                if (remainCount <= 0) {
                    iAmmoBox.setAmmoId(checkAmmoStack, DefaultAssets.EMPTY_AMMO_ID);
                }
                needAmmoCount = needAmmoCount - extractCount;
                if (needAmmoCount <= 0) {
                    break;
                }
            }
        }
        return maxAmmoCount - needAmmoCount;
    }
}
