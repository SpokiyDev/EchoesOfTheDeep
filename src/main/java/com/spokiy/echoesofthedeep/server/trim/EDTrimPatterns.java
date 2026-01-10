package com.spokiy.echoesofthedeep.server.trim;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.server.item.EDItems;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraftforge.registries.ForgeRegistries;

public class EDTrimPatterns {
    public static final ResourceKey<TrimPattern> SHRIEKER = registryKey("shrieker");

    public static void bootstrap(BootstapContext<TrimPattern> context) {
        register(context, EDItems.SHRIEKER_SMITHING_TEMPLATE.get(), SHRIEKER);
    }

    private static void register(BootstapContext<TrimPattern> context, Item item, ResourceKey<TrimPattern> key) {
        TrimPattern trimPattern = new TrimPattern(key.location(), ForgeRegistries.ITEMS.getHolder(item).get(),
                Component.translatable(Util.makeDescriptionId("trim_pattern", key.location())));
        context.register(key, trimPattern);
    }

    private static ResourceKey<TrimPattern> registryKey(String key) {
        return ResourceKey.create(Registries.TRIM_PATTERN, ResourceLocation.fromNamespaceAndPath(EchoesOfTheDeep.MOD_ID, key));
    }
}