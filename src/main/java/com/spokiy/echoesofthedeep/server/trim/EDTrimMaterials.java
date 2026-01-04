package com.spokiy.echoesofthedeep.server.trim;

import java.util.Map;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.armortrim.TrimMaterial;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class EDTrimMaterials {
    public static final ResourceKey<TrimMaterial> ECHO_SHARD = registryKey("echo_shard");

    public static void bootstrap(BootstapContext<TrimMaterial> context) {
        register(context, ECHO_SHARD, Items.ECHO_SHARD, Style.EMPTY.withColor(TextColor.parseColor("#0A5465")), 0.855F);
    }

    private static void register(BootstapContext<TrimMaterial> context, ResourceKey<TrimMaterial> trimKey, Item item, Style style, float itemModelIndex) {
        TrimMaterial trimmaterial = TrimMaterial.create(trimKey.location().getPath(), item, itemModelIndex, Component.translatable(Util.makeDescriptionId("trim_material", trimKey.location())).withStyle(style), Map.of());
        context.register(trimKey, trimmaterial);
    }

    private static ResourceKey<TrimMaterial> registryKey(String key) {
        return ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(EchoesOfTheDeep.MOD_ID, key));
    }
}