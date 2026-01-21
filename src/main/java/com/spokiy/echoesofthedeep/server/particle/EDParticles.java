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

    public static final RegistryObject<SimpleParticleType> SOUL_SCYTHE_SWEEP_ATTACK =
            PARTICLES.register("soul_scythe_sweep_attack", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> CALIBRATED_SCULK_SHRIEKER_SHRIEK_PARTICLE =
            PARTICLES.register("calibrated_sculk_shrieker_shriek_particle", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }

}
