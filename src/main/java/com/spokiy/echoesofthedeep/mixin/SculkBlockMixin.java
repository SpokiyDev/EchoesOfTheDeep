package com.spokiy.echoesofthedeep.mixin;

import com.spokiy.echoesofthedeep.server.worldgen.configured.EDConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SculkBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SculkBlock.class)
public abstract class SculkBlockMixin implements BonemealableBlock {
    /**
     * @return whether bonemeal can be used on this block
     */
    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, @NotNull BlockState pState, boolean pIsClient) {
        return pLevel.getBlockState(pPos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level pLevel, @NotNull RandomSource pRandom, @NotNull BlockPos pPos, @NotNull BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        BlockPos blockPos = pos.above();
        ChunkGenerator chunkGenerator = level.getChunkSource().getGenerator();
        Registry<ConfiguredFeature<?, ?>> registry = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);

        this.echoesOfTheDeep_1_20_x$place(registry, EDConfiguredFeatures.SCULK_SPROUTS_BONEMEAL, level, chunkGenerator, random, blockPos);

    }

    @Unique
    private void echoesOfTheDeep_1_20_x$place(
            Registry<ConfiguredFeature<?, ?>> registry,
            ResourceKey<ConfiguredFeature<?, ?>> key,
            ServerLevel level,
            ChunkGenerator chunkGenerator,
            RandomSource random,
            BlockPos pos
    ) {
        registry.getHolder(key).ifPresent((reference) -> {
            reference.value().place(level, chunkGenerator, random, pos);
        });
    }

}
