package com.spokiy.echoesofthedeep.client;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.client.render.blockentity.SculkGuardianBlockEntityRenderer;
import com.spokiy.echoesofthedeep.server.block.EDBlockEntities;
import com.spokiy.echoesofthedeep.server.particle.CalibratedSculkShriekerShriekParticle;
import com.spokiy.echoesofthedeep.server.particle.EDParticles;
import com.spokiy.echoesofthedeep.server.particle.SoulScytheSweepAttack;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.fml.common.Mod;

// EDClientEvents.java
@Mod.EventBusSubscriber(modid = EchoesOfTheDeep.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EDClientEvents {
    private static final ResourceLocation ANCIENT_CITY_ICON =
            ResourceLocation.fromNamespaceAndPath("echoesofthedeep", "textures/map/ancient_city.png");

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(EDBlockEntities.SCULK_GUARDIAN.get(), SculkGuardianBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticleProvider(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(EDParticles.SOUL_SCYTHE_SWEEP_ATTACK.get(), SoulScytheSweepAttack.Provider::new);
        event.registerSpriteSet(EDParticles.CALIBRATED_SCULK_SHRIEKER_SHRIEK_PARTICLE.get(), CalibratedSculkShriekerShriekParticle.Provider::new);

    }

}
