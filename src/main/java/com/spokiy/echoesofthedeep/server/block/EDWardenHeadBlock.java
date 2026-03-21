package com.spokiy.echoesofthedeep.server.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Map;

public class EDWardenHeadBlock extends EDWallScullBlock {
    @Nullable private String descriptionId;
    private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 16.0D),
            Direction.SOUTH, Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 10.0D),
            Direction.WEST, Block.box(6.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Direction.EAST, Block.box(0.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D)
    ));

    public EDWardenHeadBlock(Properties properties) {
        super(EDBlocks.EDSkullTypes.WARDEN, properties);
    }

//    @Override
//    @Nullable public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
//        if (pLevel.isClientSide) {
//            return createTickerHelper(blockEntityType, BlockEntityType.SKULL, SkullBlockEntity::animation);
//        }
//        return null;
//    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return AABBS.get(state.getValue(FACING));
    }

    @Override public @NotNull BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }


    @Override
    public @NotNull String getDescriptionId() {
        if (this.descriptionId == null) this.descriptionId = Util.makeDescriptionId("block", BuiltInRegistries.BLOCK.getKey(this));
        return this.descriptionId;
    }

    @Override public EquipmentSlot getEquipmentSlot() {
        return null;
    }
}
