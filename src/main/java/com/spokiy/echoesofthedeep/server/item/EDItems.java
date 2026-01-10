package com.spokiy.echoesofthedeep.server.item;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EDItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EchoesOfTheDeep.MOD_ID);

    public static final RegistryObject<Item> SHRIEKER_SMITHING_TEMPLATE = ITEMS.register("shrieker_armor_trim_smithing_template",
            () -> SmithingTemplateItem.createArmorTrimTemplate(ResourceLocation.fromNamespaceAndPath(EchoesOfTheDeep.MOD_ID, "shrieker")));

    public static final RegistryObject<Item> ECHO_SCYTHE = ITEMS.register("echo_scythe",
            () -> new EchoScytheItem(Tiers.NETHERITE, 7, -3.2F, (new Item.Properties()).rarity(Rarity.EPIC).durability(750)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
