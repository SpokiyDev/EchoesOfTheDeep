package com.spokiy.echoesofthedeep.server.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class SculkSprouts extends BushBlock implements net.minecraftforge.common.IForgeShearable {
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);

    public SculkSprouts(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return state.is(Blocks.SCULK) ||
                state.is(Blocks.SCULK_CATALYST);

    }
}
