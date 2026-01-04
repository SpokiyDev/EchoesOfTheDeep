package com.spokiy.echoesofthedeep.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class EDConfigs {

    public static final ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.BooleanValue WARDEN_SONIC_ARMOR_PENETRATION_MULTIPLIER_ENABLED;
    public static ForgeConfigSpec.DoubleValue WARDEN_SONIC_ARMOR_PENETRATION_MULTIPLIER;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("General Mod Settings").push("general");

        WARDEN_SONIC_ARMOR_PENETRATION_MULTIPLIER_ENABLED = buildBoolean(builder, "wardenSonicArmorPenetrationMultiplierEnabled", true,
                "Enable for wardenSonicArmorPenetrationMultiplier");
        WARDEN_SONIC_ARMOR_PENETRATION_MULTIPLIER = buildDouble(builder, "wardenSonicArmorPenetrationMultiplier", 0.8, 0, 1,
                "1 (vanilla value) — Warden's Sonic Boom Attack ignores armor completely");

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
