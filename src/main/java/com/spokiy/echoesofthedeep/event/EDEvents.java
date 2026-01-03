package com.spokiy.echoesofthedeep.event;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = EchoesOfTheDeep.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EDEvents {

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        EDVillagerTrades.addCustomTrades(event);
    }

}