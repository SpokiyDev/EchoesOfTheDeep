package com.spokiy.echoesofthedeep.server.enchantment.soul_scythe;

import com.spokiy.echoesofthedeep.server.enchantment.EDEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

public class EchoStrikeEnchantment extends Enchantment {
    public EchoStrikeEnchantment(EquipmentSlot... pApplicableSlots) {
        super(Rarity.RARE, EDEnchantments.SOUL_SCYTHE_CATEGORY, pApplicableSlots);
    }
    public int getMinCost(int enchantmentLevel) {
        return 10 + (enchantmentLevel * 8);
    }
    public int getMaxCost(int enchantmentLevel) {
        return 25 + getMinCost(enchantmentLevel);
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

}
