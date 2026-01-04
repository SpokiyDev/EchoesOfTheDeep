package com.spokiy.echoesofthedeep.event;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.config.EDConfigs;
import com.spokiy.echoesofthedeep.worldgen.feature.EDFeatures;
import com.spokiy.echoesofthedeep.worldgen.feature.EDPlacedFeatures;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.level.ChunkDataEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = EchoesOfTheDeep.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EDEvents {


    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        LivingEntity entity = event.getEntity();

        if (EDConfigs.WARDEN_SONIC_ARMOR_PENETRATION_MULTIPLIER_ENABLED.get()) {
            if (source.getMsgId().equals("sonic_boom") && source.getDirectEntity() instanceof Warden) {
                float baseDamage = event.getAmount();

                AttributeInstance armorAttribute = entity.getAttribute(Attributes.ARMOR);
                if (armorAttribute != null) {
                    double multiplier = 1 - EDConfigs.WARDEN_SONIC_ARMOR_PENETRATION_MULTIPLIER.get();

                    float modifiedArmor = (float) (armorAttribute.getValue() * multiplier);
                    float damageAfterArmor = baseDamage * (1 - modifiedArmor / 25f);

                    event.setAmount(damageAfterArmor);
                }
            }
        }
    }

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        EDVillagerTrades.addCustomTrades(event);
    }

}