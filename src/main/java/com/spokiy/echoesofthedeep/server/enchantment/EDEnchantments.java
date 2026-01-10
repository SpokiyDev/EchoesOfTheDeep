package com.spokiy.echoesofthedeep.server.enchantment;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
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

    public static final EnchantmentCategory ECHO_SCYTHE_CATEGORY = EnchantmentCategory.create("echo_scythe", (item -> item == EDItems.ECHO_SCYTHE.get()));

    public static final RegistryObject<Enchantment> REAPER =
            ENCHANTMENTS.register("reaper", () -> new ReaperEnchantment(EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> ECHO_RESONANCE =
            ENCHANTMENTS.register("echo_resonance", () -> new EchoResonanceEnchantment(EquipmentSlot.MAINHAND));

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }

}
