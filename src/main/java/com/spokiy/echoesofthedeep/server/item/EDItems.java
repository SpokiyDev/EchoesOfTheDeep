package com.spokiy.echoesofthedeep.server.item;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.server.block.EDBlocks;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EDItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EchoesOfTheDeep.MOD_ID);

    public static final RegistryObject<Item> SHRIEKER_SMITHING_TEMPLATE = ITEMS.register("shrieker_armor_trim_smithing_template",
            () -> SmithingTemplateItem.createArmorTrimTemplate(ResourceLocation.fromNamespaceAndPath(EchoesOfTheDeep.MOD_ID, "shrieker")));

    public static final RegistryObject<Item> WARNING_CLOCK = ITEMS.register("warning_clock",
            () -> new WarningClockItem());

    public static final RegistryObject<Item> SOUL_SCYTHE = ITEMS.register("soul_scythe",
            () -> new SoulScytheItem(Tiers.NETHERITE, 7, -3.2F));

    public static final RegistryObject<Item> WARDEN_HEAD = ITEMS.register("warden_head",
            () -> new BlockItem(EDBlocks.WARDEN_HEAD.get(), new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
