package com.spokiy.echoesofthedeep.client.item;

import com.spokiy.echoesofthedeep.config.EDConfigs;
import com.spokiy.echoesofthedeep.server.item.EDItems;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class EDItemProperties {
    public static void register() {
        ItemProperties.register(
                EDItems.WARNING_CLOCK.get(),
                ResourceLocation.parse("time"),
                (stack, level, entity, seed) -> {
                    if (!stack.hasTag()) return 0f;

                    int state = stack.getTag().getInt("WarningClockState");
                    return state / (float) EDConfigs.CLOCK_MAX_STATE;
                }
        );
    }

}
