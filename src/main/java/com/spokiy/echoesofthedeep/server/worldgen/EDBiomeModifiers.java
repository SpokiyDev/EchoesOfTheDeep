package com.spokiy.echoesofthedeep.server.worldgen;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.server.worldgen.placement.EDPlacedFeatures;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EDBiomeModifiers {
    public static final String SCULK_SPROUTS = "sculk_sprouts";

    public static void bootstrap (BootstapContext<BiomeModifier> context) {
        addFeature(context, SCULK_SPROUTS, Biomes.DEEP_DARK, GenerationStep.Decoration.VEGETAL_DECORATION, EDPlacedFeatures.SCULK_SPROUTS);

    }

    @SafeVarargs
    private static void addFeature(BootstapContext<BiomeModifier> context, String name, ResourceKey<Biome> biome, GenerationStep.Decoration step, ResourceKey<PlacedFeature>... features) {
        register(context, "add_feature/" + name, () -> new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(biome)), featureSet(context, features), step));
    }


    @SafeVarargs
    private static void removeFeature(BootstapContext<BiomeModifier> context, String name, ResourceKey<Biome> biome, GenerationStep.Decoration step, ResourceKey<PlacedFeature>... features) {
        register(context, "remove_feature/" + name, () -> new ForgeBiomeModifiers.RemoveFeaturesBiomeModifier(HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(biome)), featureSet(context, features), Set.of(step)));
    }

    private static void register(BootstapContext<BiomeModifier> context, String name, Supplier<? extends BiomeModifier> modifier) {
        context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(EchoesOfTheDeep.MOD_ID, name)), modifier.get());
    }

    private static ResourceKey<BiomeModifier> registerKey (String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(EchoesOfTheDeep.MOD_ID, name));
    }

    @SafeVarargs
    private static HolderSet<PlacedFeature> featureSet(BootstapContext<?> context, ResourceKey<PlacedFeature>... features) {
        return HolderSet.direct(Stream.of(features).map(key -> context.lookup(Registries.PLACED_FEATURE).getOrThrow(key)).collect(Collectors.toList()));
    }

}
