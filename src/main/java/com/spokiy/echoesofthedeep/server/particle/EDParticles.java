package com.spokiy.echoesofthedeep.server.particle;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EDParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, EchoesOfTheDeep.MOD_ID);

    public static final RegistryObject<SimpleParticleType> ECHO_SCYTHE_SWEEP_ATTACK =
            PARTICLES.register("echo_scythe_sweep_attack", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }

}
