package com.spokiy.echoesofthedeep.server.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.spokiy.echoesofthedeep.config.EDConfigs;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.ImmutableSet.of;

public class EchoScytheItem extends DiggerItem implements Vanishable {
    Set<ToolAction> ECHO_SCYTHE_SET = of( ToolActions.SWORD_SWEEP, ToolActions.HOE_DIG );

    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public EchoScytheItem(Tier tier, float damage, float speed, Item.Properties pProperties) {
        super(damage, speed, tier, BlockTags.MINEABLE_WITH_HOE, pProperties);
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
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        Level level = target.level();
        if (!level.isClientSide) {
            if (target.getHealth() <= 0 && level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                double x = target.getX();
                double y = target.getY();
                double z = target.getZ();

                double experienceReward = target.getExperienceReward();
                int bonusExperienceReward = (int) Math.ceil(EDConfigs.ECHO_SCYTHE_BONUS_EXPERIENCE_REWARD_MULTIPLIER.get() * experienceReward);
                level.addFreshEntity(new ExperienceOrb(level, x, y, z, bonusExperienceReward));

            }
        }

        return super.hurtEnemy(stack, target, attacker);
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
        return ECHO_SCYTHE_SET.contains(toolAction);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment == Enchantments.MOB_LOOTING || enchantment.category == EnchantmentCategory.DIGGER;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(Component.translatable("item.echoesofthedeep.echo_scythe.desc").withStyle(ChatFormatting.DARK_GREEN));
    }
}
