package com.spokiy.echoesofthedeep;

import com.mojang.logging.LogUtils;
import com.spokiy.echoesofthedeep.client.EDClientEvents;
import com.spokiy.echoesofthedeep.client.item.EDItemProperties;
import com.spokiy.echoesofthedeep.client.render.EDEntityRenders;
import com.spokiy.echoesofthedeep.config.EDConfigs;
import com.spokiy.echoesofthedeep.server.EDCreativeModeTabs;
import com.spokiy.echoesofthedeep.server.block.EDBlockEntities;
import com.spokiy.echoesofthedeep.server.block.EDBlocks;
import com.spokiy.echoesofthedeep.server.block.EDWallScullBlock;
import com.spokiy.echoesofthedeep.server.entity.EDEntities;
import com.spokiy.echoesofthedeep.server.item.EDItems;
import com.spokiy.echoesofthedeep.server.item.EDPotions;
import com.spokiy.echoesofthedeep.server.mob_effect.EDMobEffects;
import com.spokiy.echoesofthedeep.server.particle.EDParticles;
import com.spokiy.echoesofthedeep.server.enchantment.EDEnchantments;
import com.spokiy.echoesofthedeep.server.event.EDEvents;
import com.spokiy.echoesofthedeep.server.loot.EDLootModifiers;
import com.spokiy.echoesofthedeep.server.recipe.EDRecipes;
import com.spokiy.echoesofthedeep.server.worldgen.feature.EDFeatures;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.api.distmarker.Dist;
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
        EDRecipes.register(modEventBus);                                                        // Recipes
        EDEntities.register(modEventBus);                                                       // Entities
        EDMobEffects.register(modEventBus);                                                     // Mob Effects
        EDPotions.register(modEventBus);                                                        // Potions
        EDEnchantments.register(modEventBus);                                                   // Enchantments
        EDParticles.register(modEventBus);                                                      // Particles
        EDFeatures.register(modEventBus);                                                       // Features
        EDLootModifiers.register(modEventBus);                                                  // Loot

        MinecraftForge.EVENT_BUS.register(new EDEvents());

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Configuration
        context.registerConfig(ModConfig.Type.COMMON, EDConfigs.COMMON_CONFIG);

    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            EDPotions.registerPotionsRecipes();

            ComposterBlock.COMPOSTABLES.put(EDBlocks.SCULK_SPROUTS.get().asItem(), 0.5F);
        });

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
            event.enqueueWork(() -> {
                MinecraftForge.EVENT_BUS.register(new EDClientEvents());
                EDItemProperties.register();
                EDEntityRenders.register();
                // Skulls
                SkullBlockRenderer.SKIN_BY_TYPE.put(EDWallScullBlock.EDTypes.WARDEN, ResourceLocation.parse("textures/entity/warden/warden.png"));
            });
        }

    }
}
