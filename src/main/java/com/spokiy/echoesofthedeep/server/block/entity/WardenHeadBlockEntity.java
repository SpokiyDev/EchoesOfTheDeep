package com.spokiy.echoesofthedeep.server.block.entity;

import com.spokiy.echoesofthedeep.server.block.EDBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WardenHeadBlockEntity extends BlockEntity {
    public WardenHeadBlockEntity(BlockPos pos, BlockState state) {
        super(EDBlockEntities.WARDEN_HEAD.get(), pos, state);
    }
}
