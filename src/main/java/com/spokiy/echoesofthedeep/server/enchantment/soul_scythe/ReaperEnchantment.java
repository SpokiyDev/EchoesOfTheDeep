package com.spokiy.echoesofthedeep.server.enchantment.soul_scythe;

import com.spokiy.echoesofthedeep.server.enchantment.EDEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

public class ReaperEnchantment extends Enchantment {
    public ReaperEnchantment(EquipmentSlot... pApplicableSlots) {
        super(Rarity.UNCOMMON, EDEnchantments.SOUL_SCYTHE_CATEGORY, pApplicableSlots);
    }
    public int getMinCost(int enchantmentLevel) {
        return 5 + (enchantmentLevel * 8);
    }
    public int getMaxCost(int enchantmentLevel) {
        return 20 + getMinCost(enchantmentLevel);
    }
    public int getMaxLevel() {
        return 5;
    }

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment other) {
        return other != EDEnchantments.ECHO_STRIKE.get()
                && !(other instanceof DamageEnchantment)
                && super.checkCompatibility(other);
    }

}
