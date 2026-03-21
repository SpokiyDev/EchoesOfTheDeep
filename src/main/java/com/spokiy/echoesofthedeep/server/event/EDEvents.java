package com.spokiy.echoesofthedeep.server.event;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.config.EDConfigs;
import com.spokiy.echoesofthedeep.server.enchantment.EDEnchantments;
import com.spokiy.echoesofthedeep.server.item.EDItems;
import com.spokiy.echoesofthedeep.server.item.SoulScytheItem;
import com.spokiy.echoesofthedeep.server.mob_effect.EDMobEffects;
import com.spokiy.echoesofthedeep.util.Utils;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.WardenSpawnTracker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.GrindstoneEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;

@Mod.EventBusSubscriber(modid = EchoesOfTheDeep.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EDEvents {
    private static int tickCounter = 0;
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

    @SubscribeEvent void onHurt(LivingHurtEvent event) {
        if (event.getEntity().level() instanceof ServerLevel serverLevel) {
            LivingEntity target = event.getEntity();
            Entity attacker = event.getSource().getEntity();

            double damageMultiplier = 1.0D;

            if (event.getSource().getEntity() instanceof Player player) {

                if (target.getHealth() <= 0 && player.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {

                    ItemStack stack = player.getMainHandItem();
                    if (!player.isCrouching() && stack.isEmpty()) return;

                    stack.getTags().forEach(tag -> {
                        player.sendSystemMessage(Component.literal(tag.location().toString()));
                    });

                }
            }

            // Soul Strike enchantment
            if (attacker instanceof LivingEntity livingAttacker) {
                ItemStack weapon = livingAttacker.getMainHandItem();
                CompoundTag tag = weapon.getTag();

                if (tag != null && tag.contains("echoesofthedeep:echo_strike_charge")) {
                    int level = tag.getInt("echoesofthedeep:echo_strike_charge");
                    damageMultiplier *= EDConfigs.SOUL_STRIKE_BASE_DAMAGE_MULTIPLIER.get() +
                            level * EDConfigs.SOUL_STRIKE_DAMAGE_MULTIPLIER_PER_LEVEL.get();

                    tag.remove("echoesofthedeep:echo_strike_charge");

                    serverLevel.sendParticles(
                            ParticleTypes.SCULK_SOUL,
                            target.getX(),
                            target.getY() + target.getBbHeight() * 0.5,
                            target.getZ(),
                            Utils.randomRange(4, 5),
                            0.25, 0.25, 0.25,
                            0
                    );
                }
            }

            event.setAmount((float) (event.getAmount() * damageMultiplier));
            attacker.sendSystemMessage(Component.literal("" + event.getAmount()));

        }

    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        DamageSource source = event.getSource();
        LivingEntity target = event.getEntity();

        // Echo Scythe additional enchantments
        if (source.getEntity() instanceof LivingEntity attacker && attacker.level() instanceof ServerLevel serverLevel) {
            Vec3 pos = target.position();
            ItemStack weapon = attacker.getMainHandItem();
            int i;                              // Enchantment level

            // Soulstrike enchantment mechanics
            int level = EnchantmentHelper.getTagEnchantmentLevel(EDEnchantments.ECHO_STRIKE.get(), weapon);
            if(level > 0) {
                CompoundTag tag = weapon.getOrCreateTag();
                tag.putInt("echoesofthedeep:echo_strike_charge", level);
            }

            // Soul Harvester enchantment mechanics
            if (attacker instanceof Player) {
                i = EnchantmentHelper.getTagEnchantmentLevel(EDEnchantments.SOUL_HARVESTER.get(), weapon);
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
            }

            // Life Harvester enchantment mechanics
            i = EnchantmentHelper.getTagEnchantmentLevel(EDEnchantments.LIFE_HARVESTER.get(), weapon);
            if (i > 0) {
                float maxHealth = target.getMaxHealth();
                double heal = maxHealth * (EDConfigs.LIFE_HARVESTER_LIFE_STEAL_BASE_PERCENTAGE.get() + EDConfigs.LIFE_HARVESTER_LIFE_STEAL_PERCENTAGE.get() * i);

                // Particle effects
                if (attacker.getHealth() < attacker.getMaxHealth() && heal > 0) {
                    Vec3 damagerPos = attacker.getBoundingBox().getCenter();
                    if (attacker.getDeltaMovement().y < -0.1) damagerPos = attacker.position();

                    Utils.spawnParticleBeam(serverLevel,
                            new DustParticleOptions(new Vector3f(1.0F, 0.0F, 0.0F), 1.0F),
                            target.getBoundingBox().getCenter(),
                            damagerPos,
                            0.35
                    );
                }

                attacker.heal(Math.round(heal));

            }

        }
    }

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        EDVillagerTrades.addCustomTrades(event);
    }

}