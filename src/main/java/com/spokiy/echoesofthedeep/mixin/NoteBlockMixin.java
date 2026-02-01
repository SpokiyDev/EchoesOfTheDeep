package com.spokiy.echoesofthedeep.mixin;

import com.spokiy.echoesofthedeep.server.block.EDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoteBlock.class)
public abstract class NoteBlockMixin {

    @ModifyVariable(method = "triggerEvent",
            at = @At(value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/world/level/block/state/properties/NoteBlockInstrument;getSoundEvent()Lnet/minecraft/core/Holder;"),
            name = "holder")
    private Holder<SoundEvent> echoesOfTheDeep$clearVanillaSound(Holder<SoundEvent> current, BlockState state, Level level, BlockPos pos, int id, int param) {
        BlockState blockAbove = level.getBlockState(pos.above());
        if (blockAbove.is(EDBlocks.WARDEN_HEAD.get())) {
            return null;
        }
        return current;
    }


    @Inject(method = "triggerEvent",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;playSeededSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/core/Holder;Lnet/minecraft/sounds/SoundSource;FFJ)V"))
    private void echoesOfTheDeep$customPlaySound(BlockState state, Level level, BlockPos pos, int id, int param, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockAbove = level.getBlockState(pos.above());
        if (blockAbove.is(EDBlocks.WARDEN_HEAD.get())) {
            level.playSeededSound(null,
                    (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D,
                    SoundEvents.WARDEN_AMBIENT, SoundSource.RECORDS, 3.0F, 1.0f, level.random.nextLong());
        }

    }

}
