package com.spokiy.echoesofthedeep.util;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

public class EDTags {
    public static class Blocks {
        //public static final TagKey<Block>  = createTag("");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(EchoesOfTheDeep.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> SOUL_SCYTHE = createTag("souly_scythe");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(EchoesOfTheDeep.MOD_ID, name));
        }
    }

    public static class StructureTags {
        public static final TagKey<Structure> ON_ANCIENT_CITY_EXPLORER_MAPS = createConfiguredStructureFeatureTag("on_ancient_city_explorer_maps");

        private static TagKey<Structure> createConfiguredStructureFeatureTag(String name) {
            return TagKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(EchoesOfTheDeep.MOD_ID, name));
        }
    }


}
