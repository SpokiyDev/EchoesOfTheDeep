package com.spokiy.echoesofthedeep.server.enchantment;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.server.enchantment.soul_scythe.LifeHarvesterEnchantment;
import com.spokiy.echoesofthedeep.server.enchantment.soul_scythe.ReaperEnchantment;
import com.spokiy.echoesofthedeep.server.enchantment.soul_scythe.EchoStrikeEnchantment;
import com.spokiy.echoesofthedeep.server.enchantment.soul_scythe.SoulHarvesterEnchantment;
import com.spokiy.echoesofthedeep.server.item.EDItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.RegistryObject;

public class EDEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, EchoesOfTheDeep.MOD_ID);

    public static final EnchantmentCategory SOUL_SCYTHE_CATEGORY = EnchantmentCategory.create("soul_scythe", (item -> item == EDItems.SOUL_SCYTHE.get()));

    // Soul Scythe
    public static final RegistryObject<Enchantment> REAPER =
            ENCHANTMENTS.register("reaper", () -> new ReaperEnchantment(EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> ECHO_STRIKE =
            ENCHANTMENTS.register("echo_strike", () -> new EchoStrikeEnchantment(EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> SOUL_HARVESTER =
            ENCHANTMENTS.register("soul_harvester", () -> new SoulHarvesterEnchantment(EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> LIFE_HARVESTER =
            ENCHANTMENTS.register("life_harvester", () -> new LifeHarvesterEnchantment(EquipmentSlot.MAINHAND));

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }

}
