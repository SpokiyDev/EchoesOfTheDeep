package com.spokiy.echoesofthedeep.datagen;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.server.item.EDItems;
import com.spokiy.echoesofthedeep.server.util.EDTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class EDItemTagProvider extends ItemTagsProvider {
    public EDItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture,
                             CompletableFuture<TagLookup<Block>> lookupCompletableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, completableFuture, lookupCompletableFuture, EchoesOfTheDeep.MOD_ID, existingFileHelper);

    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(ItemTags.TRIM_MATERIALS)
                .add(Items.ECHO_SHARD);

        tag(ItemTags.TRIM_TEMPLATES)
                .add(EDItems.SHRIEKER_SMITHING_TEMPLATE.get());


        tag(ItemTags.HOES)
                .add(EDItems.ECHO_SCYTHE.get());

        tag(ItemTags.TOOLS)
                .add(EDItems.ECHO_SCYTHE.get());

        // Custom tags
        tag(EDTags.Items.ECHO_SCYTHE)
                .add(EDItems.ECHO_SCYTHE.get());

    }
}