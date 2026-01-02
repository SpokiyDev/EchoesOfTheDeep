package com.spokiy.echoesofthedeep.particle;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleRegistry {
    public static final DeferredRegister<ParticleType<?>> DEF_REG =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, EchoesOfTheDeep.MOD_ID);

    public static final RegistryObject<SimpleParticleType> COLORED_FIREWORK_PARTICLES =
            DEF_REG.register("colored_firework_particles", () -> new SimpleParticleType(true));

}
