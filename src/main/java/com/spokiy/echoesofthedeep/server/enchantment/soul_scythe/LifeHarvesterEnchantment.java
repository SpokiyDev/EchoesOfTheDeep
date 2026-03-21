package com.spokiy.echoesofthedeep.server.enchantment.soul_scythe;

import com.spokiy.echoesofthedeep.server.enchantment.EDEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

public class LifeHarvesterEnchantment extends Enchantment {
    public LifeHarvesterEnchantment(EquipmentSlot... pApplicableSlots) {
        super(Rarity.RARE, EDEnchantments.SOUL_SCYTHE_CATEGORY, pApplicableSlots);
    }
    @Override public int getMinCost(int pEnchantmentLevel) {
        return 15 + (pEnchantmentLevel - 1) * 9;
    }
    @Override public int getMaxCost(int pEnchantmentLevel) {
        return super.getMinCost(pEnchantmentLevel) + 50;
    }
    @Override public int getMaxLevel() {
        return 3;
    }

    @Override protected boolean checkCompatibility(@NotNull Enchantment other) {
        return super.checkCompatibility(other)
                && !(other == EDEnchantments.SOUL_HARVESTER.get());
    }

}
