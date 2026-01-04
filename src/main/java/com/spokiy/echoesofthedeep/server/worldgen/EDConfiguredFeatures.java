package com.spokiy.echoesofthedeep.server.worldgen;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.server.registry.EDFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SculkPatchConfiguration;

public class EDConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> NEW_SCULK_PATCH_DEEP_DARK = registerKey("sculk_patch_deep_dark");


    public static void bootstrap(BootstapContext<ConfiguredFeature<?,?>> context) {
        register(context, NEW_SCULK_PATCH_DEEP_DARK, EDFeatures.NEW_SCULK_PATCH.get(),
                new SculkPatchConfiguration(
                        10,
                        32,
                        64,
                        0,
                        1,
                        ConstantInt.of(0), 0.5F
                )
        );

    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey (String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(EchoesOfTheDeep.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register (BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                           ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}
