package com.spokiy.echoesofthedeep.datagen.loot;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.server.block.EDBlocks;
import com.spokiy.echoesofthedeep.server.item.EDItems;
import com.spokiy.echoesofthedeep.server.loot.AddItemModifier;
import com.spokiy.echoesofthedeep.server.loot.AddLootTableModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class GlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public GlobalLootModifiersProvider(PackOutput output) {
        super(output, EchoesOfTheDeep.MOD_ID);
    }

    @Override
    protected void start() {
        add("warden_head", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(ResourceLocation.parse("entities/warden")).build(),
                    LootItemRandomChanceCondition.randomChance(0.33f).build()
                },

                new ItemStack(EDItems.WARDEN_HEAD.get()).getItem()
        ));

//        add("add_loot_ancient_city", new AddLootTableModifier(new LootItemCondition[]{
//                new LootTableIdCondition.Builder(ResourceLocation.parse("chests/ancient_city")).build(),
////                LootItemRandomChanceCondition.randomChance(0.35f).build()
//                },
//
//                ResourceLocation.parse("echoesofthedeep:chests/add_ancient_city_loot")
//        ));
    }
}
