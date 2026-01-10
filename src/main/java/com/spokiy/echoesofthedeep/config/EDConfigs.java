package com.spokiy.echoesofthedeep.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class EDConfigs {

    public static final ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.BooleanValue WARDEN_SONIC_ARMOR_PENETRATION_MULTIPLIER_ENABLED;
    public static ForgeConfigSpec.DoubleValue WARDEN_SONIC_ARMOR_PENETRATION_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue ECHO_SCYTHE_SWEEP_RADIUS_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue ECHO_SCYTHE_BONUS_EXPERIENCE_REWARD_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue ECHO_SCYTHE_SWEEP_DAMAGE_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue REAPER_ENCHANTMENT_MULTIPLIER_PER_LEVEL;
    public static ForgeConfigSpec.IntValue ECHO_RESONANCE_ENCHANTMENT_EFFECT_COLOR;
    public static ForgeConfigSpec.DoubleValue ECHO_COLLAPSE_ENCHANTMENT_EFFECT_RADIUS;
    public static ForgeConfigSpec.DoubleValue ECHO_COLLAPSE_ENCHANTMENT_EFFECT_DAMAGE_PER_MOB;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("General Mod Settings").push("general");

        WARDEN_SONIC_ARMOR_PENETRATION_MULTIPLIER_ENABLED = buildBoolean(builder, "wardenSonicArmorPenetrationMultiplierEnabled", true,
                "Enable for wardenSonicArmorPenetrationMultiplier");
        WARDEN_SONIC_ARMOR_PENETRATION_MULTIPLIER = buildDouble(builder, "wardenSonicArmorPenetrationMultiplier", 0.8, 0, 1,
                "1 (vanilla value) — Warden's Sonic Boom Attack ignores armor completely");

        // Echo Scythe
        ECHO_SCYTHE_SWEEP_RADIUS_MULTIPLIER = buildDouble(builder, "echoScytheSweepRadiusMultiplier", 2, 0, 10,
                "The value that will be multiplied with an experience reward");
        ECHO_SCYTHE_BONUS_EXPERIENCE_REWARD_MULTIPLIER = buildDouble(builder, "echoScytheExperienceRewardMultiplier", 0.5, 0, 10000,
                "The value that will be multiplied with an experience reward");
        ECHO_SCYTHE_SWEEP_DAMAGE_MULTIPLIER = buildDouble(builder, "echoScytheSweepDamageMultiplier", 0.5, 0, 10,
                "The value that will be multiplied with an experience reward");

        // Echo Scythe Enchantments
        REAPER_ENCHANTMENT_MULTIPLIER_PER_LEVEL = buildDouble(builder, "reaperEnchantmentMultiplierPerLevel", 0.1, 0, 1,
                "The value that will be multiplied with an experience reward");

        ECHO_COLLAPSE_ENCHANTMENT_EFFECT_RADIUS = buildDouble(builder, "echoCollapseEnchantmentEffectRadius", 2.5, 0, 50,
                "The value that will be multiplied with an experience reward");
        ECHO_COLLAPSE_ENCHANTMENT_EFFECT_DAMAGE_PER_MOB = buildDouble(builder, "echoCollapseEnchantmentEffectDamagePerMob", 0.5, 0, 100,
                "The value that will be multiplied with an experience reward");


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
