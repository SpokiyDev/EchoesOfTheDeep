package com.spokiy.echoesofthedeep.server.event;

import com.spokiy.echoesofthedeep.server.util.EDTags;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraftforge.event.village.VillagerTradesEvent;

import javax.annotation.Nullable;
import java.util.List;

public class EDVillagerTrades {
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.CARTOGRAPHER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Ancient city map
            trades.get(1).add((pTrader, pRandom) ->
                treasureMapOffer(pTrader, 14, EDTags.StructureTags.ON_ANCIENT_CITY_EXPLORER_MAPS, "filled_map.ancient_city", MapDecoration.Type.BANNER_CYAN, 12, 10)
            );
        }

    }

    @Nullable
    public static MerchantOffer treasureMapOffer(Entity trader, int emeraldCost, TagKey<Structure> destination, String displayName, MapDecoration.Type destinationType, int maxUses, int villagerXp) {
        if (!(trader.level() instanceof ServerLevel serverlevel)) {
            return null;
        } else {
            BlockPos pos = serverlevel.findNearestMapStructure(destination, trader.blockPosition(), 100, true);
            if (pos != null) {
                ItemStack mapStack = MapItem.create(serverlevel, pos.getX(), pos.getZ(), (byte)2, true, true);
                MapItem.renderBiomePreviewMap(serverlevel, mapStack);
                MapItemSavedData.addTargetDecoration(mapStack, pos, "+", destinationType);
                mapStack.setHoverName(Component.translatable(displayName));

                CompoundTag tag = mapStack.getTagElement("display");
                if (tag != null) tag.putInt("MapColor", 215900);

                return new MerchantOffer(new ItemStack(Items.EMERALD, emeraldCost), new ItemStack(Items.COMPASS), mapStack, maxUses, villagerXp, 0.2F);
            } else {
                return null;
            }
        }
    }

}

