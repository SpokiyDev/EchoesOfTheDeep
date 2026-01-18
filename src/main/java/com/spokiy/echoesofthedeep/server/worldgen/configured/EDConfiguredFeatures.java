package com.spokiy.echoesofthedeep.server.worldgen.configured;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.server.block.EDBlocks;
import com.spokiy.echoesofthedeep.server.worldgen.feature.EDFeatures;
import com.spokiy.echoesofthedeep.server.worldgen.feature.SculkVegetationFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class EDConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> SCULK_SPROUTS = registerKey("sculk_sprouts");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SCULK_SPROUTS_BONEMEAL = registerKey("sculk_sprouts_bonemeal");


    public static void bootstrap(BootstapContext<ConfiguredFeature<?,?>> context) {
        register(context, SCULK_SPROUTS, EDFeatures.SCULK_VEGETATION.get(), new NetherForestVegetationConfig(BlockStateProvider.simple(EDBlocks.SCULK_SPROUTS.get()), 8, 4));
        register(context, SCULK_SPROUTS_BONEMEAL, EDFeatures.SCULK_VEGETATION.get(), new NetherForestVegetationConfig(BlockStateProvider.simple(EDBlocks.SCULK_SPROUTS.get()), 3, 1));

    }

    private static RandomPatchConfiguration grassPatch(BlockStateProvider provider, int tries) {
        return FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(provider)));
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey (String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(EchoesOfTheDeep.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register (BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                           ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}
