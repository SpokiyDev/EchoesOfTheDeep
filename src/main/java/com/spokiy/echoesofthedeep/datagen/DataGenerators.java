package com.spokiy.echoesofthedeep.datagen;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.datagen.block.EDBlockStateProvider;
import com.spokiy.echoesofthedeep.datagen.block.EDBlockTagProvider;
import com.spokiy.echoesofthedeep.datagen.item.EDItemModelProvider;
import com.spokiy.echoesofthedeep.datagen.item.EDItemTagProvider;
import com.spokiy.echoesofthedeep.datagen.loot.EDBlockLootProvider;
import com.spokiy.echoesofthedeep.datagen.loot.EDEntityLootProvider;
import com.spokiy.echoesofthedeep.datagen.loot.GlobalLootModifiersProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = EchoesOfTheDeep.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new EDDatapackEntries(output, lookupProvider));

        EDBlockTagProvider blockTagsProvider =
                new EDBlockTagProvider(output, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(),
                new EDItemTagProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(),
                new EDRecipeProvider(output));
        // Loot
        generator.addProvider(event.includeServer(),
                new GlobalLootModifiersProvider(output));
        generator.addProvider(event.includeServer(), new LootTableProvider(output, Collections.emptySet(), List.of(
                new LootTableProvider.SubProviderEntry(EDBlockLootProvider::new, LootContextParamSets.BLOCK))));

        EDBlockStateProvider blockStates = new EDBlockStateProvider(output, existingFileHelper);
        generator.addProvider(event.includeClient(), blockStates);
        generator.addProvider(event.includeClient(), new EDItemModelProvider(output, blockStates.models().existingFileHelper));

    }

}
