package com.spokiy.echoesofthedeep.server.worldgen;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class EDPlacedFeatures {
    public static final ResourceKey<PlacedFeature> NEW_SCULK_PATCH_DEEP_DARK = registerKey("new_sculk_patch_ancient_city");


    public static void bootstrap (BootstapContext<PlacedFeature> context) {
        var configuredFeature = context.lookup(Registries.CONFIGURED_FEATURE);

        Holder.Reference<ConfiguredFeature<?, ?>> holder = configuredFeature.getOrThrow(EDConfiguredFeatures.NEW_SCULK_PATCH_DEEP_DARK);
        register(context, NEW_SCULK_PATCH_DEEP_DARK, holder, List.of(CountPlacement.of(ConstantInt.of(256)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));

    }


    public static ResourceKey<PlacedFeature> registerKey (String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(EchoesOfTheDeep.MOD_ID, name));
    }

    private static void register (BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                  List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }


}
