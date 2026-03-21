package com.spokiy.echoesofthedeep.mixin;

import com.spokiy.echoesofthedeep.config.EDConfigs;
import com.spokiy.echoesofthedeep.server.item.SoulScytheItem;
import com.spokiy.echoesofthedeep.server.enchantment.EDEnchantments;
import com.spokiy.echoesofthedeep.server.particle.EDParticles;
import com.spokiy.echoesofthedeep.util.Utils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    protected PlayerMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Unique private float echoesOfTheDeep$f = 0;
    @Unique private boolean echoesOfTheDeep$flag3 = false;
    @Inject(
            method = "attack(Lnet/minecraft/world/entity/Entity;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void beforeHurt(Entity target, CallbackInfo ci, float f) {
        echoesOfTheDeep$f = f;
    }

    @Inject(method = "attack(Lnet/minecraft/world/entity/Entity;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;canPerformAction(Lnet/minecraftforge/common/ToolAction;)Z"))
    private void flag3(Entity target, CallbackInfo ci) {
        echoesOfTheDeep$flag3 = true;
    }

    @Inject(method = "attack(Lnet/minecraft/world/entity/Entity;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;setLastHurtMob(Lnet/minecraft/world/entity/Entity;)V")
    )
    private void customSweepAttack(Entity target, CallbackInfo ci) {
        if (!echoesOfTheDeep$flag3) return;
        echoesOfTheDeep$flag3 = false;

        Player player = (Player)(Object)this;
        ItemStack stack = player.getMainHandItem();

        // If Echo Scythe
        // Get vanilla damage output
        if (stack.getItem() instanceof SoulScytheItem) {
            // Echo Scythe sweep damage multiplier and the reaper enchantment
            int i = EnchantmentHelper.getTagEnchantmentLevel(EDEnchantments.REAPER.get(), stack);
            double multiplier = EDConfigs.SOUL_SCYTHE_SWEEP_DAMAGE_MULTIPLIER.get() +
                    i * EDConfigs.REAPER_ENCHANTMENT_MULTIPLIER_PER_LEVEL.get();
            float f3 = (float) (echoesOfTheDeep$f * multiplier);

            for (LivingEntity livingentity : this.level().getEntitiesOfClass(LivingEntity.class, Utils.newSweepHitBox(this.getItemInHand(InteractionHand.MAIN_HAND), player, target))) {
                var reach = this.getAttributeValue(net.minecraftforge.common.ForgeMod.ENTITY_REACH.get());
                if (livingentity != this && livingentity != target && !this.isAlliedTo(livingentity) && (!(livingentity instanceof ArmorStand) || !((ArmorStand) livingentity).isMarker()) && this.distanceToSqr(livingentity) < reach * reach) {
                    livingentity.knockback(0.4F, Mth.sin(this.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(this.getYRot() * ((float) Math.PI / 180F)));
                    livingentity.hurt(this.damageSources().playerAttack(player), f3);
                }
            }

            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, this.getSoundSource(), 1.0F, 1.0F);
            echoesOfTheDeep$soulScytheSweepAttack();

        }
    }

    @Unique
    private void echoesOfTheDeep$soulScytheSweepAttack() {
        double d0 = -Mth.sin(this.getYRot() * ((float)Math.PI / 180F));
        double d1 = Mth.cos(this.getYRot() * ((float)Math.PI / 180F));
        if (this.level() instanceof ServerLevel) {
            ((ServerLevel)this.level()).sendParticles(EDParticles.SOUL_SCYTHE_SWEEP_ATTACK.get(), this.getX() + d0, this.getY(0.5D), this.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
        }

    }

}

