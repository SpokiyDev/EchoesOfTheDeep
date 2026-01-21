package com.spokiy.echoesofthedeep.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class EDConfigs {

    public static final ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.DoubleValue SOUL_SCYTHE_SWEEP_RADIUS_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue SOUL_SCYTHE_SWEEP_DAMAGE_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue SOUL_SCYTHE_SWEEP_ATTACK_PARTICLE_SIZE_MULTIPLIER;

    public static ForgeConfigSpec.DoubleValue REAPER_ENCHANTMENT_MULTIPLIER_PER_LEVEL;
    public static ForgeConfigSpec.DoubleValue ECHO_COLLAPSE_ENCHANTMENT_EFFECT_RADIUS;
    public static ForgeConfigSpec.DoubleValue ECHO_COLLAPSE_ENCHANTMENT_EFFECT_DAMAGE_PER_MOB;
    public static ForgeConfigSpec.DoubleValue SOUL_HARVESTER_EXPERIENCE_REWARD_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue LIFE_HARVESTER_LIFE_STEAL_BASE_PERCENTAGE;
    public static ForgeConfigSpec.DoubleValue LIFE_HARVESTER_LIFE_STEAL_PERCENTAGE;

    // Sculk Shrieker Redesigned
    public static int SCULK_SHRIEKER_SUCK_COOLDOWN = 20;
    public static int SCULK_SHRIEKER_DROP_ITEMS_COOLDOWN = 100; // 160
    public static float SCULK_SHRIEKER_EAT_SOUND_PITCH = 0.75F;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("General Mod Settings").push("general");

        // Soul Scythe
        SOUL_SCYTHE_SWEEP_RADIUS_MULTIPLIER = buildDouble(builder, "soulScytheSweepRadiusMultiplier", 2, 0, 100,
                "");
        SOUL_SCYTHE_SWEEP_DAMAGE_MULTIPLIER = buildDouble(builder, "soulScytheSweepDamageMultiplier", 0.5, 0, 100,
                "");
        SOUL_SCYTHE_SWEEP_ATTACK_PARTICLE_SIZE_MULTIPLIER = buildDouble(builder, "soulScytheSweepAttackParticleSizeMultiplier",
                1.2, 0, 100, "");

        // Soul Scythe Enchantments
        REAPER_ENCHANTMENT_MULTIPLIER_PER_LEVEL = buildDouble(builder, "reaperEnchantmentMultiplierPerLevel", 0.1, 0, 100,
                "");

        ECHO_COLLAPSE_ENCHANTMENT_EFFECT_RADIUS = buildDouble(builder, "echoCollapseEnchantmentEffectRadius", 2.5, 0, 100,
                "");
        ECHO_COLLAPSE_ENCHANTMENT_EFFECT_DAMAGE_PER_MOB = buildDouble(builder, "echoCollapseEnchantmentEffectDamagePerMob", 0.5, 0, 100,
                "");

        SOUL_HARVESTER_EXPERIENCE_REWARD_MULTIPLIER = buildDouble(builder, "soulHarvesterExperienceRewardMultiplier", 0.15, 0, 100,
                "");
        LIFE_HARVESTER_LIFE_STEAL_BASE_PERCENTAGE = buildDouble(builder, "lifeHarvesterLifeStealPercentage", 0.025, 0, 100,
                "");
        LIFE_HARVESTER_LIFE_STEAL_PERCENTAGE = buildDouble(builder, "lifeHarvesterLifeStealPercentage", 0.025, 0, 100,
                "");


        builder.pop();
        COMMON_CONFIG = builder.build();

    }


    private static ForgeConfigSpec.BooleanValue buildBoolean(ForgeConfigSpec.Builder builder, String name, boolean defaultValue, String comment) {
        return builder.comment(comment).translation(name).define(name, defaultValue);
    }

    private static ForgeConfigSpec.IntValue buildInt(ForgeConfigSpec.Builder builder, String name, int defaultValue, int min, int max, String comment) {
        return builder.comment(comment).translation(name).defineInRange(name, defaultValue, min, max);
    }

    private static ForgeConfigSpec.DoubleValue buildDouble(ForgeConfigSpec.Builder builder, String name, double defaultValue, double min, double max, String comment) {
        return builder.comment(comment).translation(name).defineInRange(name, defaultValue, min, max);
    }

}
