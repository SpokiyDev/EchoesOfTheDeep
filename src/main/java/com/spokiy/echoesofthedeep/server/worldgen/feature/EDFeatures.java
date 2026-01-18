package com.spokiy.echoesofthedeep.server.worldgen.feature;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.*;

import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;
import net.minecraft.world.level.levelgen.feature.configurations.SculkPatchConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class EDFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, EchoesOfTheDeep.MOD_ID);

    public static final RegistryObject<Feature<NetherForestVegetationConfig>> SCULK_VEGETATION =
            FEATURES.register("sculk_vegetation", () -> new SculkVegetationFeature(NetherForestVegetationConfig.CODEC));


    public static void register (IEventBus eventBus) {
        FEATURES.register(eventBus);
    }

}
