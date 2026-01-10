package com.spokiy.echoesofthedeep.server.mob_effect;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EDMobEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, EchoesOfTheDeep.MOD_ID);

    public static final RegistryObject<MobEffect> ECHO_COLLAPSE = EFFECTS.register("echo_collapse", EchoCollapseEffect::new);


    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }

}