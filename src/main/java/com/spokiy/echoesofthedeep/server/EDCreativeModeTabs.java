package com.spokiy.echoesofthedeep.server;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.server.block.EDBlocks;
import com.spokiy.echoesofthedeep.server.item.EDItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class EDCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EchoesOfTheDeep.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ECHOES_OF_THE_DEEP_ITEMS_TAB = CREATIVE_MODE_TABS.register("echoesofthedeep_creative_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.SCULK_SHRIEKER))
                    .title(Component.translatable("creativetab.echoesofthedeep.echoes_of_the_deep"))
                    .displayItems((itemDisplayParameters, output) -> {
                        // Materials
                        output.accept(Items.ECHO_SHARD);
                        // Blocks
                        output.accept(Items.SCULK);
                        output.accept(Items.SCULK_VEIN);
                        output.accept(EDBlocks.SCULK_SPROUTS.get());
                        output.accept(Items.SCULK_CATALYST);
                        output.accept(Items.SCULK_SHRIEKER);
                        output.accept(Items.SCULK_SENSOR);
                        output.accept(EDBlocks.SCULK_GUARDIAN.get());
                        output.accept(Items.CALIBRATED_SCULK_SENSOR);
                        output.accept(EDBlocks.CALIBRATED_SCULK_SHRIEKER.get());
                        // Item list
                        output.accept(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE);
                        output.accept(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE);
                        output.accept(EDItems.SHRIEKER_SMITHING_TEMPLATE.get());
                        // Music disc
                        output.accept(EDItems.SOUL_SCYTHE.get());
                        output.accept(Items.RECOVERY_COMPASS);
                        output.accept(EDItems.WARNING_CLOCK.get());
                        output.accept(Items.MUSIC_DISC_5);
                        output.accept(Items.DISC_FRAGMENT_5);
                        output.accept(EDItems.WARDEN_HEAD.get());

                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
