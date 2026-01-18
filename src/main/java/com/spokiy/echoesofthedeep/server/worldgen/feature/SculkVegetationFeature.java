package com.spokiy.echoesofthedeep.server.worldgen.feature;

import com.mojang.serialization.Codec;
import com.spokiy.echoesofthedeep.server.block.EDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;
import org.jetbrains.annotations.NotNull;

public class SculkVegetationFeature extends Feature<NetherForestVegetationConfig> {
    public SculkVegetationFeature(Codec<NetherForestVegetationConfig> configCodec) {
        super(configCodec);
    }

    public boolean place(@NotNull FeaturePlaceContext<NetherForestVegetationConfig> context) {
        WorldGenLevel worldGenLevel = context.level();
        BlockPos blockPos = context.origin();
        BlockState groundState = worldGenLevel.getBlockState(blockPos.below());
        NetherForestVegetationConfig config = context.config();
        RandomSource random = context.random();

        for (int i = 0; i < config.spreadWidth * config.spreadWidth; ++i) {
            BlockPos targetPos = blockPos.offset(
                    random.nextInt(config.spreadWidth) - random.nextInt(config.spreadWidth),
                    random.nextInt(config.spreadHeight) - random.nextInt(config.spreadHeight),
                    random.nextInt(config.spreadWidth) - random.nextInt(config.spreadWidth)
            );

            BlockState plantState = config.stateProvider.getState(random, targetPos);

            if (worldGenLevel.isEmptyBlock(targetPos) && plantState.canSurvive(worldGenLevel, targetPos)) {
                worldGenLevel.setBlock(targetPos, plantState, 3);
            }
        }

        return true;
    }
}