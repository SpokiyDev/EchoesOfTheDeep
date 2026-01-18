package com.spokiy.echoesofthedeep.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class EDConfigs {

    public static final ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.BooleanValue WARDEN_SONIC_ARMOR_PENETRATION_MULTIPLIER_ENABLED;
    public static ForgeConfigSpec.DoubleValue WARDEN_SONIC_ARMOR_PENETRATION_MULTIPLIER;

    public static ForgeConfigSpec.DoubleValue ECHO_SCYTHE_SWEEP_RADIUS_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue ECHO_SCYTHE_BONUS_EXPERIENCE_REWARD_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue ECHO_SCYTHE_SWEEP_DAMAGE_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue ECHO_SCYTHE_SWEEP_ATTACK_PARTICLE_SIZE_MULTIPLIER;

    public static ForgeConfigSpec.DoubleValue REAPER_ENCHANTMENT_MULTIPLIER_PER_LEVEL;
    public static ForgeConfigSpec.DoubleValue ECHO_COLLAPSE_ENCHANTMENT_EFFECT_RADIUS;
    public static ForgeConfigSpec.DoubleValue ECHO_COLLAPSE_ENCHANTMENT_EFFECT_DAMAGE_PER_MOB;

    // Sculk Shrieker Redesigned
    public static int SCULK_SHRIEKER_SUCK_COOLDOWN = 20;
    public static int SCULK_SHRIEKER_DROP_ITEMS_COOLDOWN = 100; // 160
    public static float SCULK_SHRIEKER_EAT_SOUND_PITCH = 0.75F;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("General Mod Settings").push("general");

        WARDEN_SONIC_ARMOR_PENETRATION_MULTIPLIER_ENABLED = buildBoolean(builder, "wardenSonicArmorPenetrationMultiplierEnabled", true,
                "Enable for wardenSonicArmorPenetrationMultiplier");
        WARDEN_SONIC_ARMOR_PENETRATION_MULTIPLIER = buildDouble(builder, "wardenSonicArmorPenetrationMultiplier", 0.8, 0, 1,
                "1 (vanilla value) — Warden's Sonic Boom Attack ignores armor completely");

        // Echo Scythe
        ECHO_SCYTHE_SWEEP_RADIUS_MULTIPLIER = buildDouble(builder, "echoScytheSweepRadiusMultiplier", 2, 0, 100,
                "");
        ECHO_SCYTHE_BONUS_EXPERIENCE_REWARD_MULTIPLIER = buildDouble(builder, "echoScytheExperienceRewardMultiplier", 0.5, 0, 100,
                "");
        ECHO_SCYTHE_SWEEP_DAMAGE_MULTIPLIER = buildDouble(builder, "echoScytheSweepDamageMultiplier", 0.5, 0, 100,
                "");
        ECHO_SCYTHE_SWEEP_ATTACK_PARTICLE_SIZE_MULTIPLIER = buildDouble(builder, "echoScytheSweepAttackParticleSizeMultiplier",
                1.2, 0, 100, "");

        // Echo Scythe Enchantments
        REAPER_ENCHANTMENT_MULTIPLIER_PER_LEVEL = buildDouble(builder, "reaperEnchantmentMultiplierPerLevel", 0.1, 0, 100,
                "");

        ECHO_COLLAPSE_ENCHANTMENT_EFFECT_RADIUS = buildDouble(builder, "echoCollapseEnchantmentEffectRadius", 2.5, 0, 100,
                "");
        ECHO_COLLAPSE_ENCHANTMENT_EFFECT_DAMAGE_PER_MOB = buildDouble(builder, "echoCollapseEnchantmentEffectDamagePerMob", 0.5, 0, 100,
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
