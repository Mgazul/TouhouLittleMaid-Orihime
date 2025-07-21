package com.github.tartaricacid.touhoulittlemaid.item.bauble;

import com.github.tartaricacid.touhoulittlemaid.advancements.maid.TriggerType;
import com.github.tartaricacid.touhoulittlemaid.api.bauble.IMaidBauble;
import com.github.tartaricacid.touhoulittlemaid.api.event.MaidDeathEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitTrigger;
import com.github.tartaricacid.touhoulittlemaid.util.ItemsUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityEvent;

public class UndyingTotemBauble implements IMaidBauble {
    public UndyingTotemBauble() {
        MaidDeathEvent.CALLBACK.register(this::onLivingDamage);
    }

    public void onLivingDamage(MaidDeathEvent event) {
        EntityMaid maid = event.getMaid();
        DamageSource source = event.getSource();
        if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            int slot = ItemsUtil.getBaubleSlotInMaid(maid, this);
            if (slot >= 0) {
                maid.getMaidBauble().getStackInSlot(slot).shrink(1);
                event.setCanceled(true);
                maid.setHealth(1.0F);
                maid.removeAllEffects();
                maid.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                maid.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                maid.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
                maid.level.broadcastEntityEvent(maid, EntityEvent.TALISMAN_ACTIVATE);
                if (maid.getOwner() instanceof ServerPlayer serverPlayer) {
                    InitTrigger.MAID_EVENT.trigger(serverPlayer, TriggerType.USE_UNDEAD_BAUBLE);
                }
            }
        }
    }
}
