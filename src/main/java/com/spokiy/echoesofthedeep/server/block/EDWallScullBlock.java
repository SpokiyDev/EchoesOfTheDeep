package com.spokiy.echoesofthedeep.server.block;

import com.spokiy.echoesofthedeep.server.block.entity.EDSkullBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class EDWallScullBlock extends WallSkullBlock {
    public EDWallScullBlock(SkullBlock.Type type, Properties properties) {
        super(type, properties);
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new EDSkullBlockEntity(pos, state);
    }

    public enum EDTypes implements SkullBlock.Type {
        WARDEN

    }

}