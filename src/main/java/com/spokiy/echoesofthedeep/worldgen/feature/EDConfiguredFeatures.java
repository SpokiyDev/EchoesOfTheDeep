package com.spokiy.echoesofthedeep.worldgen.feature;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.SculkPatchConfiguration;

public class EDConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> NEW_SCULK_PATCH =
            ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(EchoesOfTheDeep.MOD_ID, "new_sculk_patch")
            );

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> ctx) {
        ctx.register(
                NEW_SCULK_PATCH,
                new ConfiguredFeature<>(
                        EDFeatures.NEW_SCULK_PATCH.get(),
                        new SculkPatchConfiguration(
                            10,
                            32,
                            64,
                            0,
                            1,
                            ConstantInt.of(0),
                            0.5F
                        )
                )
        );
    }
}
