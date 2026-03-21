package com.spokiy.echoesofthedeep.server.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.spokiy.echoesofthedeep.server.enchantment.EDEnchantments;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.ImmutableSet.of;

public class SoulScytheItem extends DiggerItem implements Vanishable {
    Set<ToolAction> SOUL_SCYTHE_SET = of( ToolActions.HOE_DIG );

    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public SoulScytheItem(Tier tier, float damage, float speed) {
        super(damage, speed, tier, BlockTags.MINEABLE_WITH_HOE,
                new Item.Properties().rarity(Rarity.EPIC).durability(750));

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", damage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", speed, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    @Override
    public float getAttackDamage() {
        return super.getAttackDamage();
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack itemStack, @NotNull ItemStack itemStackMaterial) {
        return itemStackMaterial.is(Items.ECHO_SHARD);
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return SOUL_SCYTHE_SET.contains(toolAction);
    }

    public boolean canAttackBlock(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player pPlayer) {
        return !pPlayer.isCreative();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment == Enchantments.MOB_LOOTING || enchantment.category == EnchantmentCategory.DIGGER || enchantment.category == EDEnchantments.SOUL_SCYTHE_CATEGORY;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(Component.translatable("item.echoesofthedeep.soul_scythe.desc").withStyle(ChatFormatting.DARK_GREEN));
    }


    // Bars
    @Override
    public boolean isBarVisible(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if(tag != null && tag.contains("echoesofthedeep:echo_strike_charge")) return true;

        return super.isBarVisible(stack);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if(tag != null && tag.contains("echoesofthedeep:echo_strike_charge")) return 13;

        return super.getBarWidth(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if(tag != null && tag.contains("echoesofthedeep:echo_strike_charge")) return 0xffd000;

        return super.getBarColor(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        if (EnchantmentHelper.getTagEnchantmentLevel(EDEnchantments.ECHO_STRIKE.get(), stack) == 0) {
            CompoundTag tag = stack.getTag();
            if(tag != null) tag.remove("echoesofthedeep:echo_strike_charge");
        }
    }

}
