package com.spokiy.echoesofthedeep.server.worldgen;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
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
    public static final ResourceKey<BiomeModifier> NEW_SCULK_PATCH_DEEP_DARK = registerKey("new_sculk_patch_deep_dark");

    public static void bootstrap (BootstapContext<BiomeModifier> context) {
        var placedFeature = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

//        removeFeature(context, "sculk_patch_deep_dark", Biomes.DEEP_DARK, GenerationStep.Decoration.UNDERGROUND_DECORATION);

        context.register(NEW_SCULK_PATCH_DEEP_DARK, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.MINESHAFT_BLOCKING),
                HolderSet.direct(placedFeature.getOrThrow(EDPlacedFeatures.NEW_SCULK_PATCH_DEEP_DARK)),
                GenerationStep.Decoration.UNDERGROUND_DECORATION));

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
