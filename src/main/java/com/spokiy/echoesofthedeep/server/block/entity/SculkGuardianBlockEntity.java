package com.spokiy.echoesofthedeep.server.block.entity;

import com.spokiy.echoesofthedeep.server.block.SculkGuardian;
import com.spokiy.echoesofthedeep.server.block.EDBlockEntities;
import com.spokiy.echoesofthedeep.server.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class SculkGuardianBlockEntity extends BlockEntity implements GameEventListener.Holder<VibrationSystem.Listener>, VibrationSystem {
    private static final int LISTENER_RADIUS = 8;
    public static final double EYE_Y_OFFSET = 1;

    private final VibrationSystem.User vibrationUser = new SculkGuardianBlockEntity.VibrationUser();
    private final VibrationSystem.Data vibrationData = new VibrationSystem.Data();
    private final VibrationSystem.Listener vibrationListener = new VibrationSystem.Listener(this);

    public SculkGuardianBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(EDBlockEntities.SCULK_GUARDIAN.get(), pPos, pBlockState);
    }

    public void tryEmitSonicBoom(ServerLevel level, @Nullable Entity target, boolean onlySurvivalMode) {
        if (target != null) {
            // Check the player gamemode
            if (target instanceof ServerPlayer player &&
                    (player.gameMode.getGameModeForPlayer() == GameType.SPECTATOR ||
                    ( onlySurvivalMode && player.gameMode.getGameModeForPlayer() == GameType.CREATIVE ))
            ) return;

            BlockState blockState = this.getBlockState();
            if (this.canRespond(level) && !blockState.getValue(SculkGuardian.SHRIEKING)) {
                this.emitSonicBoom(level, blockState, target);
            }

        }
    }

    public void emitSonicBoom(ServerLevel level, BlockState blockState, Entity target) {
        if (!(target instanceof LivingEntity livingEntity)) return;
        BlockPos blockPos = this.getBlockPos();
        level.setBlock(blockPos, blockState.setValue(SculkShriekerBlock.SHRIEKING, Boolean.TRUE), 2);
        level.scheduleTick(blockPos, blockState.getBlock(), 60);

        Vec3 start = Vec3.atCenterOf(blockPos).add(0, EYE_Y_OFFSET, 0);
        Vec3 end = livingEntity.getEyePosition();

        Utils.emitSonicBoom(level, start, end, livingEntity, 8f);

    }

    private boolean canRespond (ServerLevel level) {
        return level.getDifficulty() != Difficulty.PEACEFUL;
    }


    public VibrationSystem.@NotNull Data getVibrationData() {
        return this.vibrationData;
    }
    public VibrationSystem.@NotNull User getVibrationUser() {
        return this.vibrationUser;
    }
    public VibrationSystem.@NotNull Listener getListener() {
        return this.vibrationListener;
    }

    class VibrationUser implements VibrationSystem.User {
        private final PositionSource positionSource = new BlockPositionSource(SculkGuardianBlockEntity.this.worldPosition);

        public VibrationUser() {
        }
        public int getListenerRadius() {
            return LISTENER_RADIUS;
        }

        public @NotNull PositionSource getPositionSource() {
            return this.positionSource;
        }
        public @NotNull TagKey<GameEvent> getListenableEvents() {
            return GameEventTags.SHRIEKER_CAN_LISTEN;
        }
        @Override
        public boolean canReceiveVibration(@NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull GameEvent event, GameEvent.@NotNull Context context) {
            return !SculkGuardianBlockEntity.this.getBlockState().getValue(SculkGuardian.SHRIEKING);

        }

        public void onDataChanged() {
            SculkGuardianBlockEntity.this.setChanged();
        }
        public boolean requiresAdjacentChunksToBeTicking() {
            return true;
        }
        public void onReceiveVibration(@NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull GameEvent event, @Nullable Entity target, @Nullable Entity receiver, float p_283119_) {
            SculkGuardianBlockEntity.this.tryEmitSonicBoom(
                    level,
                    SculkShriekerBlockEntity.tryGetPlayer(target != null ? target : receiver),
                    true
            );

        }

    }

}
