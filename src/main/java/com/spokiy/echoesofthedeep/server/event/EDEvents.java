package com.spokiy.echoesofthedeep.server.event;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.config.EDConfigs;
import com.spokiy.echoesofthedeep.server.item.EDItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.warden.WardenSpawnTracker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = EchoesOfTheDeep.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EDEvents {
    private static int tickCounter = 0;

    @SubscribeEvent void onHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ItemStack stack = player.getMainHandItem();
            if (player.isCrouching() && stack.isEmpty()) return;

            stack.getTags().forEach(tag -> {
                player.sendSystemMessage(Component.literal(tag.location().toString()));
            });

        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        tickCounter++;
        if (tickCounter % 20 != 0) return; // оновлюємо кожні 5 тіків

        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
            int warning = player.getWardenSpawnTracker()
                    .map(WardenSpawnTracker::getWarningLevel)
                    .orElse(0);

            for (ItemStack stack : player.getInventory().items) {
                if (stack.getItem() == EDItems.WARNING_CLOCK.get()) {
                    stack.getOrCreateTag().putInt("WardenWarning", warning);
                }
            }
        }

    }


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