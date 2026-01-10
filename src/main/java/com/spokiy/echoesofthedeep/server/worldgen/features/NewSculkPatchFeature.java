package com.spokiy.echoesofthedeep.server.worldgen.features;

import com.mojang.serialization.Codec;
import com.spokiy.echoesofthedeep.server.block.EDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SculkPatchConfiguration;

import java.util.Random;

public class NewSculkPatchFeature extends Feature<SculkPatchConfiguration> {
    public NewSculkPatchFeature(Codec<SculkPatchConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SculkPatchConfiguration> context) {
        WorldGenLevel worldgenlevel = context.level();
        BlockPos blockpos = context.origin();
        if (!this.canSpreadFrom(worldgenlevel, blockpos)) {
            return false;
        } else {
            SculkPatchConfiguration configuration = context.config();
            RandomSource random = context.random();
            SculkSpreader sculkSpreader = SculkSpreader.createWorldGenSpreader();
            int i = configuration.spreadRounds() + configuration.growthRounds();

            for(int j = 0; j < i; ++j) {
                for(int k = 0; k < configuration.chargeCount(); ++k) {
                    sculkSpreader.addCursors(blockpos, configuration.amountPerCharge());
                }

                boolean flag = j < configuration.spreadRounds();

                for(int l = 0; l < configuration.spreadAttempts(); ++l) {
                    sculkSpreader.updateCursors(worldgenlevel, blockpos, random, flag);
                }

                sculkSpreader.clear();
            }

            BlockPos blockpos2 = blockpos.below();
            if (random.nextFloat() <= configuration.catalystChance() && worldgenlevel.getBlockState(blockpos2).isCollisionShapeFullBlock(worldgenlevel, blockpos2)) {
                int result = new Random().nextInt(2);
                switch (result) {
                    case 0 -> worldgenlevel.setBlock(blockpos, Blocks.SCULK_CATALYST.defaultBlockState(), 3);
                    case 1 -> worldgenlevel.setBlock(blockpos, EDBlocks.SCULK_GUARDIAN.get().defaultBlockState(), 3);

                }
            }

            int i1 = configuration.extraRareGrowths().sample(random);

            for(int j1 = 0; j1 < i1; ++j1) {
                BlockPos blockpos1 = blockpos.offset(random.nextInt(5) - 2, 0, random.nextInt(5) - 2);
                if (worldgenlevel.getBlockState(blockpos1).isAir() && worldgenlevel.getBlockState(blockpos1.below()).isFaceSturdy(worldgenlevel, blockpos1.below(), Direction.UP)) {
                    worldgenlevel.setBlock(blockpos1, Blocks.SCULK_SHRIEKER.defaultBlockState().setValue(SculkShriekerBlock.CAN_SUMMON, Boolean.TRUE), 3);
                }
            }

            return true;
        }
    }

    private boolean canSpreadFrom(LevelAccessor pLevel, BlockPos pPos) {
        BlockState blockstate = pLevel.getBlockState(pPos);
        if (blockstate.getBlock() instanceof SculkBehaviour) {
            return true;
        } else {
            return (blockstate.isAir() || (blockstate.is(Blocks.WATER) && blockstate.getFluidState().isSource())) && Direction.stream().map(pPos::relative).anyMatch((p_225245_) -> {
                return pLevel.getBlockState(p_225245_).isCollisionShapeFullBlock(pLevel, p_225245_);
            });
        }
    }
}