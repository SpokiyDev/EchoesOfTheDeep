package com.spokiy.echoesofthedeep.server.enchantment;

import com.spokiy.echoesofthedeep.server.item.EchoScytheItem;
import com.spokiy.echoesofthedeep.server.mob_effect.EDMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.jetbrains.annotations.NotNull;

public class EchoResonanceEnchantment extends Enchantment {
    public EchoResonanceEnchantment(EquipmentSlot... pApplicableSlots) {
        super(Rarity.RARE, EDEnchantments.ECHO_SCYTHE_CATEGORY, pApplicableSlots);
    }
    public int getMinCost(int enchantmentLevel) {
        return 15 + (enchantmentLevel * 8);
    }
    public int getMaxCost(int enchantmentLevel) {
        return 30 + getMinCost(enchantmentLevel);
    }
    public int getMaxLevel() {
        return 4;
    }

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment other) {
        return other != EDEnchantments.REAPER.get()
                && !(other instanceof DamageEnchantment)
                && super.checkCompatibility(other);
    }

    @Override
    public void doPostAttack(@NotNull LivingEntity attacker, @NotNull Entity entity, int pLevel) {
        if (attacker instanceof Player || !(entity instanceof LivingEntity target)) return;

        ItemStack stack = attacker.getMainHandItem();

        if (stack.getItem() instanceof EchoScytheItem) {
            int i = EnchantmentHelper.getTagEnchantmentLevel(EDEnchantments.ECHO_RESONANCE.get(), stack);
            if (i > 0) {
                target.addEffect(new MobEffectInstance(
                        EDMobEffects.ECHO_COLLAPSE.get(), 90, i - 1));

                target.getPersistentData().putUUID(
                        "echoesofthedeep.echo_collapse.effect.source",
                        attacker.getUUID()
                );
            }

        }

        super.doPostAttack(attacker, target, pLevel);

    }
}
