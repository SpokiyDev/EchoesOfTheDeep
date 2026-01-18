package com.spokiy.echoesofthedeep.mixin;

import com.spokiy.echoesofthedeep.config.EDConfigs;
import com.spokiy.echoesofthedeep.server.item.EchoScytheItem;
import com.spokiy.echoesofthedeep.server.enchantment.EDEnchantments;
import com.spokiy.echoesofthedeep.server.mob_effect.EDMobEffects;
import com.spokiy.echoesofthedeep.server.particle.EDParticles;
import com.spokiy.echoesofthedeep.server.util.Utils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    protected PlayerMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(
            method = "attack(Lnet/minecraft/world/entity/Entity;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getSweepingDamageRatio(Lnet/minecraft/world/entity/LivingEntity;)F"),
            cancellable = true
    )
    private void onSweepAttack(Entity target, CallbackInfo ci) {
        Player player = (Player)(Object)this;
        ItemStack stack = player.getMainHandItem();

        if (!(stack.getItem() instanceof EchoScytheItem)) return;

        // Echo Resonance Effect
        if (target instanceof LivingEntity livingEntity) {
            int i = EnchantmentHelper.getTagEnchantmentLevel(EDEnchantments.RESONANCE.get(), stack);
            if (i > 0) {
                livingEntity.addEffect(new MobEffectInstance(EDMobEffects.ECHO_COLLAPSE.get(), 90, i - 1));
                livingEntity.getPersistentData().putUUID("echoesofthedeep.echo_collapse.effect.source", player.getUUID());
            }
        }

        // If Echo Scythe
        // Get vanilla damage output
        float f = (float)player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float strength = player.getAttackStrengthScale(0.5F);
        f *= 0.2F + strength * strength * 0.8F;

        // Echo Scythe sweep damage multiplier and the reaper enchantment
        int i = EnchantmentHelper.getTagEnchantmentLevel(EDEnchantments.REAPER.get(), stack);
        double multiplier = EDConfigs.ECHO_SCYTHE_SWEEP_DAMAGE_MULTIPLIER.get() +
                i * EDConfigs.REAPER_ENCHANTMENT_MULTIPLIER_PER_LEVEL.get();
        float f3 = (float)(f * multiplier);

        for(LivingEntity livingentity : this.level().getEntitiesOfClass(LivingEntity.class, Utils.newSweepHitBox(this.getItemInHand(InteractionHand.MAIN_HAND), player, target))) {
            var reach = this.getAttributeValue(net.minecraftforge.common.ForgeMod.ENTITY_REACH.get());
            if (livingentity != this && livingentity != target && !this.isAlliedTo(livingentity) && (!(livingentity instanceof ArmorStand) || !((ArmorStand)livingentity).isMarker()) && this.distanceToSqr(livingentity) < reach * reach) {
                livingentity.knockback(0.4F, Mth.sin(this.getYRot() * ((float)Math.PI / 180F)), -Mth.cos(this.getYRot() * ((float)Math.PI / 180F)));
                livingentity.hurt(this.damageSources().playerAttack(player), f3);
            }
        }

        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, this.getSoundSource(), 1.0F, 1.0F);
        echoesOfTheDeep$echoScytheSweepAttack();
        ci.cancel();
    }

    @Unique
    public void echoesOfTheDeep$echoScytheSweepAttack() {
        double d0 = -Mth.sin(this.getYRot() * ((float)Math.PI / 180F));
        double d1 = Mth.cos(this.getYRot() * ((float)Math.PI / 180F));
        if (this.level() instanceof ServerLevel) {
            ((ServerLevel)this.level()).sendParticles(EDParticles.ECHO_SCYTHE_SWEEP_ATTACK.get(), this.getX() + d0, this.getY(0.5D), this.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
        }

    }

}

