package com.spokiy.echoesofthedeep.mixin;

import com.spokiy.echoesofthedeep.server.block.EDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.SculkPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.SculkPatchConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(SculkPatchFeature.class)
public class SculkPatchFeatureMixin {

    @Inject(method = "place",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;below()Lnet/minecraft/core/BlockPos;"),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void place(FeaturePlaceContext<SculkPatchConfiguration> pContext, CallbackInfoReturnable<Boolean> cir, WorldGenLevel worldgenlevel, BlockPos blockpos) {
        SculkPatchConfiguration sculkpatchconfiguration = pContext.config();
        RandomSource randomsource = pContext.random();

        BlockPos blockpos2 = blockpos.below();
        if (randomsource.nextFloat() <= sculkpatchconfiguration.catalystChance() && worldgenlevel.getBlockState(blockpos2).isCollisionShapeFullBlock(worldgenlevel, blockpos2)) {
            worldgenlevel.setBlock(blockpos, Blocks.SCULK_CATALYST.defaultBlockState(), 3);
        }

        int i1 = sculkpatchconfiguration.extraRareGrowths().sample(randomsource);

        for(int j1 = 0; j1 < i1; ++j1) {
            BlockPos blockpos1 = blockpos.offset(randomsource.nextInt(5) - 2, 0, randomsource.nextInt(5) - 2);
            if (worldgenlevel.getBlockState(blockpos1).isAir() && worldgenlevel.getBlockState(blockpos1.below()).isFaceSturdy(worldgenlevel, blockpos1.below(), Direction.UP)) {
                worldgenlevel.setBlock(blockpos1, Blocks.SCULK_SHRIEKER.defaultBlockState().setValue(SculkShriekerBlock.CAN_SUMMON, Boolean.TRUE), 3);
            }
        }

        cir.setReturnValue(true);
        cir.cancel();

    }
}
