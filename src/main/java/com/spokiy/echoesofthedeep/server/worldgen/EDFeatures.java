package com.spokiy.echoesofthedeep.server.worldgen;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.server.worldgen.features.NewSculkPatchFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.*;

import net.minecraft.world.level.levelgen.feature.configurations.SculkPatchConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class EDFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, EchoesOfTheDeep.MOD_ID);

    public static final RegistryObject<Feature<SculkPatchConfiguration>> NEW_SCULK_PATCH=
            FEATURES.register("new_sculk_patch", () -> new NewSculkPatchFeature(SculkPatchConfiguration.CODEC));


    public static void register (IEventBus eventBus) {
        FEATURES.register(eventBus);
    }

}
