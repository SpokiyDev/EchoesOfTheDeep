package com.spokiy.echoesofthedeep.server.util;

import com.spokiy.echoesofthedeep.config.EDConfigs;
import com.spokiy.echoesofthedeep.server.item.EchoScytheItem;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Utils {
    public static AABB newSweepHitBox(ItemStack stack, Player player, Entity target) {
        AABB original = stack.getSweepHitBox(player, target);
        if (!(stack.getItem() instanceof EchoScytheItem)) return original;

        double extraX = original.getXsize() * (EDConfigs.ECHO_SCYTHE_SWEEP_RADIUS_MULTIPLIER.get() - 1.0) / 2.0;
        double extraZ = original.getZsize() * (EDConfigs.ECHO_SCYTHE_SWEEP_RADIUS_MULTIPLIER.get() - 1.0) / 2.0;

        return original.inflate(extraX, 0.0, extraZ);

    }

    public static void emitSonicBoom(ServerLevel level, Vec3 attackerPos, Vec3 targetPos, LivingEntity target, float damage) {
        emitSonicBoom(level, attackerPos, targetPos, target, damage, ParticleTypes.SONIC_BOOM, SoundEvents.WARDEN_SONIC_BOOM);
    }
    public static void emitSonicBoom(ServerLevel level, Vec3 attackerPos, Vec3 targetPos, LivingEntity target, float damage, SoundEvent soundEvent) {
        emitSonicBoom(level, attackerPos, targetPos, target, damage, ParticleTypes.SONIC_BOOM, soundEvent);
    }
    public static void emitSonicBoom(ServerLevel level, Vec3 attackerPos, Vec3 targetPos, LivingEntity target, float damage, SimpleParticleType particleType, SoundEvent soundEvent) {
        Vec3 direction = targetPos.subtract(attackerPos).normalize();
        double distance = attackerPos.distanceTo(targetPos);

        // Visuals
        for (int i = 0; i <= distance; i++) {
            Vec3 pos = attackerPos.add(direction.scale(i));
            level.sendParticles(
                    particleType,
                    pos.x, pos.y, pos.z,
                    1, 0.0D, 0.0D, 0.0D, 0.0D
            );
        }
        if (soundEvent != null) level.playSound(null, BlockPos.containing(attackerPos), soundEvent, SoundSource.BLOCKS, 3.0F, 1.0F);

        // Damage
        target.hurt(level.damageSources().sonicBoom(target), damage);

        double d1 = 0.5D;
        double d0 = 2.5D;
        if (target instanceof Player player) {
            d1 *= 1.0D - player.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            d0 *= 1.0D - player.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
        }
        target.push(direction.x() * d0, direction.y() * d1, direction.z() * d0);
    }
}
