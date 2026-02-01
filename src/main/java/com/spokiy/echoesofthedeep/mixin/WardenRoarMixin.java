package com.spokiy.echoesofthedeep.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.warden.Roar;
import net.minecraft.world.entity.monster.warden.Warden;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Roar.class)
public class WardenRoarMixin {

    @Inject(method = "start(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/monster/warden/Warden;J)V",
            at = @At("TAIL"))
    public void onWardenRoar(ServerLevel level, Warden warden, long p_217582_, CallbackInfo ci) {

        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, warden.getBoundingBox().inflate(0.25));
        entities.forEach(entity -> {
            if (!(entity == warden)) {
                double d0 = entity.getX() - warden.getX();
                double d1 = entity.getZ() - warden.getZ();
                double d2 = Math.max(d0 * d0 + d1 * d1, 0.001D);
                entity.push(d0 / d2 * 4.0D, 0.2D, d1 / d2 * 4.0D);

            }
        });
    }
}
