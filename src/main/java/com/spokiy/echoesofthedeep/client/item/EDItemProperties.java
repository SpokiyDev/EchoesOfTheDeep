package com.spokiy.echoesofthedeep.client.item;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.server.item.EDItems;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = EchoesOfTheDeep.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EDItemProperties {
    @SubscribeEvent
    public static void registerItemProperties(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(EDItems.WARNING_CLOCK.get(), ResourceLocation.parse("time"), new ClampedItemPropertyFunction() {
                private double rotation;
                private double rota;
                private long lastUpdateTick;

                public float unclampedCall(@NotNull ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity livingEntity, int p_174668_) {
                    if (!(livingEntity instanceof Player player)) {
                        return 0.0F;
                    } else {
                        if (level == null && player.level() instanceof ClientLevel) {
                            level = (ClientLevel)player.level();
                        }

                        if (level == null) {
                            return 0.0F;
                        } else {
                            int warning;
                            try {
                                assert stack.getTag() != null;
                                warning = stack.getTag().getInt("WardenWarning");
                            } catch (Exception ignored) { return 0.0F; }

                            float output = (float) this.wobble(level, (warning / 4f) * 63f / 64f);
                            player.sendSystemMessage(Component.literal("" + warning));
                            return output;
                        }
                    }
                }

                private double wobble(Level level, double originalValue) {
                    if (level.getGameTime() != this.lastUpdateTick) {
                        this.lastUpdateTick = level.getGameTime();
                        double value = originalValue - this.rotation;
                        value = Mth.positiveModulo(value + 0.5D, 1.0D) - 0.5D;
                        this.rota += value * 0.1D;
                        this.rota *= 0.9D;
                        this.rotation = Mth.positiveModulo(this.rotation + this.rota, 1.0D);
                    }

                    return this.rotation;
                }
            });
        });
    }
}
