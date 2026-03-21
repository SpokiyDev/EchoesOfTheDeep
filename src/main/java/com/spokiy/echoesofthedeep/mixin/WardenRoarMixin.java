package com.spokiy.echoesofthedeep.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.warden.Roar;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(Roar.class)
public class WardenRoarMixin {
    @Unique
    private static final Predicate<Entity> NO_WARDEN_AND_ALIVE = (entity) -> entity.isAlive() && !(entity instanceof Warden);

    @Inject(method = "tick(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/monster/warden/Warden;J)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/warden/Warden;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V"))
    public void onWardenRoar(ServerLevel level, Warden warden, long p_217582_, CallbackInfo ci) {
        double maxDistance = 8.0;
        for(LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, warden.getBoundingBox().inflate(8.0D), NO_WARDEN_AND_ALIVE)) {
            double d0 = entity.getX() - warden.getX();
            double d1 = entity.getZ() - warden.getZ();
            double distance = Math.sqrt(d0 * d0 + d1 * d1);
            distance = Math.min(distance, maxDistance);
            if(distance < 0.001) distance = 0.001;

            // Knockback
            double maxPush = 3.25D;
            double minPush = 1.0D;
            double pushStrength = maxPush - (distance / maxDistance) * (maxPush - minPush);
            entity.push(d0 / distance * pushStrength, 0.2D, d1 / distance * pushStrength);

            // Damage
            double minDamage = 2.0;
            double maxDamage = 8.5;
            double damage = maxDamage - (distance / maxDistance) * (maxDamage - minDamage);
            entity.hurt(warden.damageSources().mobAttack(warden), (float) damage);
        }

//        Vec3 vec3 = warden.getBoundingBox().getCenter();
//        level.sendParticles(ParticleTypes.POOF,
//                vec3.x, vec3.y, vec3.z,
//                20,
//                0, 0, 0,
//                0.3D
//        );

    }
}
