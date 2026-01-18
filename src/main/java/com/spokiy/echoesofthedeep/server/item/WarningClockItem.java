package com.spokiy.echoesofthedeep.server.item;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Optional;

public class WarningClockItem extends Item {

    public WarningClockItem() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));

    }



    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @Nonnull InteractionHand hand) {
        player.startUsingItem(hand);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getItemInHand(hand));
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, Level level, @NotNull LivingEntity entity, int useRemaining) {
        int useTime = this.getUseDuration(stack) - useRemaining;

        if (entity instanceof ServerPlayer player && useTime >= 20) {
            //teleportToSpawn(player);
            player.getWardenSpawnTracker().get().setWarningLevel(0);
        }
    }

    @Nonnull
    @Override
    public UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }

    public static void teleportToSpawn(ServerPlayer player) {
        BlockPos respawnPos = player.getRespawnPosition();
        ResourceKey<Level> respawnDim = player.getRespawnDimension();

        ServerLevel targetLevel = player.getServer().getLevel(respawnDim);

        Vec3 targetPos;

        if (respawnPos != null && targetLevel != null) {
            Optional<Vec3> spawn = Player.findRespawnPositionAndUseSpawnBlock(
                    targetLevel,
                    respawnPos,
                    player.getRespawnAngle(),
                    false,
                    false
            );

            targetPos = spawn.orElseGet(() -> Vec3.atCenterOf(targetLevel.getSharedSpawnPos()));
        } else targetPos = Vec3.atCenterOf(targetLevel.getSharedSpawnPos());

        player.teleportTo(targetLevel, targetPos.x, targetPos.y, targetPos.z, player.getYRot(), player.getXRot());
    }


}
