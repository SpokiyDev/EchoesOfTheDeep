package com.spokiy.echoesofthedeep.server.mob_effect;

import com.spokiy.echoesofthedeep.config.EDConfigs;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;
import java.util.UUID;

public class EchoCollapseEffect extends MobEffect {
    EchoCollapseEffect() {
        super(MobEffectCategory.HARMFUL, 0x009295);
    }

    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        if (!(entity.level() instanceof ServerLevel level)) return;

        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,
                entity.getBoundingBox().inflate(EDConfigs.ECHO_COLLAPSE_ENCHANTMENT_EFFECT_RADIUS.get()));
        entities.remove(getAttacker(level, entity));

        int entitiesNumber = Math.min(entities.size() - 1, 2 * (amplifier + 1));
        if (entitiesNumber > 0) {
            entity.hurt(level.damageSources().magic(), (float) (entitiesNumber * EDConfigs.ECHO_COLLAPSE_ENCHANTMENT_EFFECT_DAMAGE_PER_MOB.get()));
        }

    }

    @Unique
    public LivingEntity getAttacker(ServerLevel level, LivingEntity entity) {
        CompoundTag tag = entity.getPersistentData();

        String key = "echoesofthedeep.echo_collapse.effect.source";
        if (!tag.hasUUID(key)) return null;
        UUID id = tag.getUUID(key);

        Entity source = level.getEntity(id);
        if (source instanceof LivingEntity livingEntity) return livingEntity;
        return null;
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        int j = 40;
        return (duration - 1) % j == 0;
    }

}