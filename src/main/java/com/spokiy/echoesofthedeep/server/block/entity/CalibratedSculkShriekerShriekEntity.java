package com.spokiy.echoesofthedeep.server.block.entity;

import com.spokiy.echoesofthedeep.server.entity.EDEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

public class CalibratedSculkShriekerShriekEntity extends Entity {
    private final double maxRadius = 5.0;
    private final double speed = 0.25;
    public double currentRadius = 0.0;
    private Vec3 origin;

    public CalibratedSculkShriekerShriekEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public CalibratedSculkShriekerShriekEntity(Level level, Vec3 origin) {
        this(EDEntities.CALIBRATED_SCULK_SHRIEKER_SHRIEK.get(), level);
        this.origin = origin;
        this.setPos(origin.x, origin.y, origin.z);
    }

    @Override
    public void tick() {
        super.tick();

        currentRadius += speed;
        if (!this.level().isClientSide) {
            double previousRadius = currentRadius - speed;

            AABB area = new AABB(
                    origin.x - currentRadius, origin.y - currentRadius, origin.z - currentRadius,
                    origin.x + currentRadius, origin.y + currentRadius, origin.z + currentRadius
            );
            List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, area,
                    e -> !e.isSpectator() && e.distanceToSqr(origin) >= previousRadius * previousRadius);

            for (LivingEntity entity : entities) {
                entity.hurt(this.level().damageSources().sonicBoom(this), 4.0F);
            }

            if (currentRadius >= maxRadius) {
                this.remove(Entity.RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag tag) {}

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag tag) {}

}
