package com.spokiy.echoesofthedeep.server.worldgen.placement;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.server.worldgen.configured.EDConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class EDPlacedFeatures {
    public static final ResourceKey<PlacedFeature> SCULK_SPROUTS = registerKey("sculk_sprouts");


    public static void bootstrap (BootstapContext<PlacedFeature> context) {
        var configuredFeature = context.lookup(Registries.CONFIGURED_FEATURE);

        Holder.Reference<ConfiguredFeature<?, ?>> holder = configuredFeature.getOrThrow(EDConfiguredFeatures.SCULK_SPROUTS);
        register(context, SCULK_SPROUTS, holder, List.of(CountPlacement.of(UniformInt.of(204, 250)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));

    }


    public static ResourceKey<PlacedFeature> registerKey (String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(EchoesOfTheDeep.MOD_ID, name));
    }

    private static void register (BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                  List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }


}
