package com.spokiy.echoesofthedeep;

import com.mojang.logging.LogUtils;
import com.spokiy.echoesofthedeep.enchantment.EDEnchantments;
import com.spokiy.echoesofthedeep.event.EDEvents;
import com.spokiy.echoesofthedeep.item.EDCreativeModeTabs;
import com.spokiy.echoesofthedeep.item.EDItems;
import com.spokiy.echoesofthedeep.item.alchemy.EDPotions;
import com.spokiy.echoesofthedeep.loot.EDLootModifiers;
import com.spokiy.echoesofthedeep.particle.ColoredFireworkParticle;
import com.spokiy.echoesofthedeep.particle.EDParticles;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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

        EDItems.register(modEventBus);                              // Items
        EDPotions.register(modEventBus);                            // Potions
        EDCreativeModeTabs.register(modEventBus);                   // Creative tabs
        EDEnchantments.register(modEventBus);                       // Enchantments
        EDParticles.register(modEventBus);                          // Particles
        EDLootModifiers.register(modEventBus);                      // Loot

        MinecraftForge.EVENT_BUS.register(new EDEvents());

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

    }


    private void setup(final FMLCommonSetupEvent event) {

        EDPotions.registerPotionsRecipes();

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
//            event.accept();
        }

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
            event.registerSpriteSet(EDParticles.COLORED_FIREWORK_PARTICLES.get(), ColoredFireworkParticle.Provider::new);
        }
    }
}
