package com.spokiy.echoesofthedeep.server.block;

import com.spokiy.echoesofthedeep.server.block.entity.CalibratedSculkShriekerShriekEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class CalibratedSculkShriekerBlock extends DirectionalBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    protected static final VoxelShape COLLIDER = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);

    public CalibratedSculkShriekerBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE).setValue(POWERED, Boolean.FALSE).setValue(TRIGGERED, Boolean.FALSE));
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(WATERLOGGED);
        pBuilder.add(TRIGGERED);
        pBuilder.add(POWERED);
    }

    public void shockwave(ServerLevel level, BlockPos pos) {
        CalibratedSculkShriekerShriekEntity wave =
                new CalibratedSculkShriekerShriekEntity(level, Vec3.atCenterOf(pos));
        level.addFreshEntity(wave);

    }

    @SuppressWarnings("deprecation")
    @Override
    public void tick(BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!state.getValue(POWERED)) {
            level.setBlock(pos, state.setValue(TRIGGERED, false), 3);
            return;
        }

        if (!state.getValue(TRIGGERED)) {
            shockwave(level, pos);

            level.setBlock(pos, state.setValue(TRIGGERED, true), 3);
            level.scheduleTick(pos, this, 40);
        }
        else {
            level.setBlock(pos, state.setValue(TRIGGERED, false), 3);
            if (state.getValue(POWERED)) level.scheduleTick(pos, this, 1);
        }
    }


    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(@NotNull BlockState state, Level level, @NotNull BlockPos pos,
                                @NotNull Block block, @NotNull BlockPos fromPos, boolean isMoving) {

        boolean hasSignal = level.hasNeighborSignal(pos);
        boolean powered = state.getValue(POWERED);

        if (powered != hasSignal) {
            level.setBlock(pos, state.setValue(POWERED, hasSignal), 3);

            if (hasSignal && !state.getValue(TRIGGERED)) {
                level.scheduleTick(pos, this, 1);
            }
        }
    }



    @SuppressWarnings("deprecation")
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }
    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return COLLIDER;
    }
    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getOcclusionShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos) {
        return COLLIDER;
    }
    @SuppressWarnings("deprecation")
    public @NotNull BlockState updateShape(BlockState pState, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        if (pState.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(pState, direction, neighborState, level, pos, neighborPos);
    }
    @SuppressWarnings("deprecation")
    public boolean useShapeForLightOcclusion(@NotNull BlockState state) {
        return true;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER)
                .setValue(POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos()));
    }
    @SuppressWarnings("deprecation")
    public @NotNull FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

}
