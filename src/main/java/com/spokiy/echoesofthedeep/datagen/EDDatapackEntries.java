package com.spokiy.echoesofthedeep.datagen;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.server.trim.EDTrimMaterials;
import com.spokiy.echoesofthedeep.server.trim.EDTrimPatterns;
import com.spokiy.echoesofthedeep.server.worldgen.EDBiomeModifiers;
import com.spokiy.echoesofthedeep.server.worldgen.EDConfiguredFeatures;
import com.spokiy.echoesofthedeep.server.worldgen.EDPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class EDDatapackEntries extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.TRIM_MATERIAL, EDTrimMaterials::bootstrap)
            .add(Registries.TRIM_PATTERN, EDTrimPatterns::bootstrap)

            .add(Registries.CONFIGURED_FEATURE, EDConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, EDPlacedFeatures::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, EDBiomeModifiers::bootstrap);

    public EDDatapackEntries(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(EchoesOfTheDeep.MOD_ID));
    }
}