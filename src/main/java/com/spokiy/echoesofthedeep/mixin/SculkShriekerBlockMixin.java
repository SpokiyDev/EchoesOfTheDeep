package com.spokiy.echoesofthedeep.mixin;


import com.spokiy.echoesofthedeep.server.util.SculkShriekerTickBridge;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkShriekerBlock.class)
public class SculkShriekerBlockMixin {
    @Inject(method = "stepOn", at = @At("HEAD"), cancellable = true)
    public void cancelStepOn(Level level, BlockPos pos, BlockState state, Entity entity, CallbackInfo ci) {
        if (!level.isClientSide && entity instanceof ItemEntity) {
            ci.cancel();
        }

    }

    @Inject(method = "onRemove", at = @At("HEAD"))
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving, CallbackInfo ci) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof SculkShriekerBlockEntity) {
                Containers.dropContents(level, pos, (Container) blockentity);
            }

        }
    }

    @Inject(method = "getTicker", at = @At("RETURN"), cancellable = true)
    private <T extends BlockEntity> void wrapTicker(
            Level level, BlockState state, BlockEntityType<T> type, CallbackInfoReturnable<BlockEntityTicker<T>> cir
    ) {
        if (level.isClientSide) return;
        BlockEntityTicker<T> original = cir.getReturnValue();
        if (original == null) return;

        cir.setReturnValue((lvl, pos, st, be) -> {
            original.tick(lvl, pos, st, be);

            if (be instanceof SculkShriekerTickBridge shrieker) {
                shrieker.echoesOfTheDeep$tickServer(level, pos, state);
            }
        });

    }

}
