package com.spokiy.echoesofthedeep;

import com.mojang.logging.LogUtils;
import com.spokiy.echoesofthedeep.config.EDConfigs;
import com.spokiy.echoesofthedeep.server.EDCreativeModeTabs;
import com.spokiy.echoesofthedeep.server.block.EDBlockEntities;
import com.spokiy.echoesofthedeep.server.block.EDBlocks;
import com.spokiy.echoesofthedeep.server.item.EDItems;
import com.spokiy.echoesofthedeep.server.item.EDPotions;
import com.spokiy.echoesofthedeep.server.mob_effect.EDMobEffects;
import com.spokiy.echoesofthedeep.server.particle.EDParticles;
import com.spokiy.echoesofthedeep.server.enchantment.EDEnchantments;
import com.spokiy.echoesofthedeep.server.event.EDEvents;
import com.spokiy.echoesofthedeep.server.loot.EDLootModifiers;
import com.spokiy.echoesofthedeep.server.particle.EchoScytheSweepAttack;
import com.spokiy.echoesofthedeep.server.worldgen.EDFeatures;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EchoesOfTheDeep.MOD_ID)
public class EchoesOfTheDeep
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "echoesofthedeep";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public EchoesOfTheDeep(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::setup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        EDCreativeModeTabs.register(modEventBus);                                               // Creative Tabs
        EDItems.register(modEventBus);                                                          // Items
        EDBlocks.register(modEventBus);                                                         // Blocks
        EDBlockEntities.register(modEventBus);                                                  // Block Entities
        EDMobEffects.register(modEventBus);                                                     // Mob Effects
        EDPotions.register(modEventBus);                                                        // Potions
        EDEnchantments.register(modEventBus);                                                   // Enchantments
        EDParticles.register(modEventBus);                                               // Particles
        EDFeatures.register(modEventBus);                                                       // Features
        EDLootModifiers.register(modEventBus);                                                  // Loot

        MinecraftForge.EVENT_BUS.register(new EDEvents());

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Configuration
        context.registerConfig(ModConfig.Type.COMMON, EDConfigs.COMMON_CONFIG);

    }


    private void setup(final FMLCommonSetupEvent event) {

        EDPotions.registerPotionsRecipes();

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }

        @SubscribeEvent
        public static void registerParticleProvider(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(EDParticles.ECHO_SCYTHE_SWEEP_ATTACK.get(), EchoScytheSweepAttack.Provider::new);
        }
    }
}
