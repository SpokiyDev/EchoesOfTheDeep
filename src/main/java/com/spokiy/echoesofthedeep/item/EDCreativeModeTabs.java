package com.spokiy.echoesofthedeep.item;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
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

    public static final RegistryObject<CreativeModeTab> ECHOES_OF_THE_DEEP_ITEMS_TAB = CREATIVE_MODE_TABS.register("echoes_of_the_deep_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.SCULK_SHRIEKER))
                    .title(Component.translatable("creativetab.echoesofthedeep.echoes_of_the_deep"))
                    .displayItems((itemDisplayParameters, output) -> {
                        // Item list
                        output.accept(EDItems.SHRIEKER_SMITHING_TEMPLATE.get());

                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
