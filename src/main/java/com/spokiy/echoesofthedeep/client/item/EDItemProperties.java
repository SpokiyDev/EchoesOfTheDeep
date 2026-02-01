package com.spokiy.echoesofthedeep.client.item;

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
                new ClampedItemPropertyFunction() {

                    // ⏱ НАЛАШТУВАННЯ
                    private static final int UPDATE_INTERVAL_TICKS = 20; // 1 сек
                    private static final int STATES = 64;                // кількість станів

                    private long lastUpdateTick = 0;
                    private int currentState = 0;

                    @Override
                    public float unclampedCall(@NotNull ItemStack stack,
                                               @Nullable ClientLevel level,
                                               @Nullable LivingEntity entity,
                                               int seed) {

                        if (level == null) {
                            return currentState / (float) STATES;
                        }

                        long gameTime = level.getGameTime();

                        // 🔁 змінюємо стан кожен інтервал
                        if (gameTime - lastUpdateTick >= UPDATE_INTERVAL_TICKS) {
                            lastUpdateTick = gameTime;

                            currentState = (currentState + 1) % STATES;
                        }

                        // 🔢 значення для item property
                        return currentState / (float) STATES;
                    }
                }
        );
    }

}
