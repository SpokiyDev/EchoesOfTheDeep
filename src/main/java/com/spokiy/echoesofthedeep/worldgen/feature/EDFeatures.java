package com.spokiy.echoesofthedeep.worldgen.feature;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SculkPatchConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EDFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(ForgeRegistries.FEATURES, EchoesOfTheDeep.MOD_ID);

    public static final RegistryObject<Feature<SculkPatchConfiguration>> NEW_SCULK_PATCH =
            FEATURES.register("new_sculk_patch",
                    () -> new NewSculkPatchFeature(SculkPatchConfiguration.CODEC));

    public static void register(IEventBus bus) {
        FEATURES.register(bus);
    }
}
