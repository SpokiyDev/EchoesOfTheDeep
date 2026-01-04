package com.spokiy.echoesofthedeep.worldgen.feature;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class EDPlacedFeatures {
    public static final ResourceKey<PlacedFeature> NEW_SCULK_PATCH =
            ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(EchoesOfTheDeep.MOD_ID, "new_sculk_patch")
            );

    public static void bootstrap(BootstapContext<PlacedFeature> ctx) {
        HolderGetter<ConfiguredFeature<?, ?>> configured =
                ctx.lookup(Registries.CONFIGURED_FEATURE);

        ctx.register(
                NEW_SCULK_PATCH,
                new PlacedFeature(
                        configured.getOrThrow(EDConfiguredFeatures.NEW_SCULK_PATCH),
                        List.of(
                                CountPlacement.of(UniformInt.of(1, 3)),
                                InSquarePlacement.spread(),
                                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                                BiomeFilter.biome()
                        )
                )
        );
    }
}
