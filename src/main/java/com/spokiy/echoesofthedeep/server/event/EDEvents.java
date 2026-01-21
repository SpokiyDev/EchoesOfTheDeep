package com.spokiy.echoesofthedeep.server.event;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.config.EDConfigs;
import com.spokiy.echoesofthedeep.server.enchantment.EDEnchantments;
import com.spokiy.echoesofthedeep.server.item.EDItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.warden.WardenSpawnTracker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EchoesOfTheDeep.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EDEvents {
    private static int tickCounter = 0;

    @SubscribeEvent void onHurt(LivingHurtEvent event) {
        LivingEntity target = event.getEntity();
        if (event.getSource().getEntity() instanceof Player player) {
            if (target.getHealth() <= 0 && player.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {

                ItemStack stack = player.getMainHandItem();
                if (!player.isCrouching() && stack.isEmpty()) return;

                stack.getTags().forEach(tag -> {
                    player.sendSystemMessage(Component.literal(tag.location().toString()));
                });

            }
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        tickCounter++;
        if (tickCounter % 20 != 0) return;

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
    public static void onLivingHurt(LivingDeathEvent event) {
        DamageSource source = event.getSource();
        LivingEntity target = event.getEntity();

        // Echo Scythe additional enchantments
        if (source.getEntity() instanceof Player player && player.level() instanceof ServerLevel serverLevel) {
            Vec3 pos = target.position();

            // Soul Harvester enchantment mechanics
            int i = EnchantmentHelper.getEnchantmentLevel(EDEnchantments.SOUL_HARVESTER.get(), player);
            if (i > 0 && serverLevel.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                int reward = target.getExperienceReward();
                int bonusExperienceReward = (int) Math.ceil(reward
                        * (EDConfigs.SOUL_HARVESTER_EXPERIENCE_REWARD_MULTIPLIER.get() * i));

                serverLevel.addFreshEntity(new ExperienceOrb(serverLevel,
                        target.getX(), target.getY(), target.getZ(),
                        bonusExperienceReward));

                serverLevel.sendParticles(ParticleTypes.SCULK_SOUL,
                        pos.x, pos.y + 0.26D, pos.z,
                        2, 0.2D, 0.0D, 0.2D, 0.0D);

            }
            // Life Harvester enchantment mechanics
            i = EnchantmentHelper.getEnchantmentLevel(EDEnchantments.LIFE_HARVESTER.get(), player);
            if (i > 0) {
                float maxHealth = target.getMaxHealth();
                double heal = maxHealth * (EDConfigs.LIFE_HARVESTER_LIFE_STEAL_BASE_PERCENTAGE.get() + EDConfigs.LIFE_HARVESTER_LIFE_STEAL_PERCENTAGE.get() * i);
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("" + (EDConfigs.LIFE_HARVESTER_LIFE_STEAL_BASE_PERCENTAGE.get() + EDConfigs.LIFE_HARVESTER_LIFE_STEAL_PERCENTAGE.get() * i)));
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("" + heal));
                player.heal(Math.round(heal));

            }

        }
    }

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        EDVillagerTrades.addCustomTrades(event);
    }

}