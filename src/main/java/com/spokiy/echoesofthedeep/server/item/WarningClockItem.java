package com.spokiy.echoesofthedeep.server.item;

import com.spokiy.echoesofthedeep.config.EDConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class WarningClockItem extends Item {
    public static final String NBT_STATE = "WarningClockState";

    public WarningClockItem() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));

    }

    @Override
    public void inventoryTick(ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        if (!stack.hasTag()) stack.getOrCreateTag();
        if (!(entity instanceof LivingEntity living)) return;

        ItemStack offhand = living.getOffhandItem();
        if (!isSelected && !ItemStack.isSameItemSameTags(stack, offhand)) {
            resetState(stack);

            try {
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("NO"));
            } catch (Exception ignored) {}
            return;

        }
        try {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("YES"));
        } catch (Exception ignored) {}

        int state = stack.getTag().getInt(NBT_STATE);
        if (state < EDConfigs.CLOCK_MAX_STATE) {
            state++;
            stack.getTag().putInt(NBT_STATE, state);
        }
    }

    private void resetState(ItemStack stack) {
        if (stack.hasTag()) stack.getTag().putInt(NBT_STATE, 0);
    }

    public static boolean isMaxState(ItemStack stack) {
        return stack.hasTag() && stack.getTag().getInt(NBT_STATE) == EDConfigs.CLOCK_MAX_STATE;
    }


}
