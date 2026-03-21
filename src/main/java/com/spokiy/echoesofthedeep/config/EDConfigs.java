package com.spokiy.echoesofthedeep.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class EDConfigs {

    public static final ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.DoubleValue SOUL_SCYTHE_SWEEP_RADIUS_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue SOUL_SCYTHE_SWEEP_DAMAGE_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue SOUL_SCYTHE_SWEEP_ATTACK_PARTICLE_SIZE_MULTIPLIER;

    public static ForgeConfigSpec.DoubleValue REAPER_ENCHANTMENT_MULTIPLIER_PER_LEVEL;

    public static ForgeConfigSpec.IntValue ECHO_STRIKE_EFFECT_DURATION_SECONDS;
    public static ForgeConfigSpec.DoubleValue SOUL_STRIKE_BASE_DAMAGE_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue SOUL_STRIKE_DAMAGE_MULTIPLIER_PER_LEVEL;

    public static ForgeConfigSpec.DoubleValue SOUL_HARVESTER_EXPERIENCE_REWARD_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue LIFE_HARVESTER_LIFE_STEAL_BASE_PERCENTAGE;
    public static ForgeConfigSpec.DoubleValue LIFE_HARVESTER_LIFE_STEAL_PERCENTAGE;

    public static int CLOCK_MAX_STATE = 32;

    // Sculk Shrieker Redesigned
    public static int SCULK_SHRIEKER_SUCK_COOLDOWN = 20;
    public static int SCULK_SHRIEKER_DROP_ITEMS_COOLDOWN = 160; // 160
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

        ECHO_STRIKE_EFFECT_DURATION_SECONDS = buildInt(builder, "soulStrikeEffectDurationSeconds", 15, 0, 100000,
                "");
        SOUL_STRIKE_BASE_DAMAGE_MULTIPLIER = buildDouble(builder, "soulStrikeBaseDamageMultiplier", 1.2, 0, 100,
                "");
        SOUL_STRIKE_DAMAGE_MULTIPLIER_PER_LEVEL = buildDouble(builder, "soulStrikeDamageMultiplierPerLevel", 0.2, 0, 10,
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
