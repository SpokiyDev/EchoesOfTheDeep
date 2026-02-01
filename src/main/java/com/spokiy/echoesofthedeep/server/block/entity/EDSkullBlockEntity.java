package com.spokiy.echoesofthedeep.server.block.entity;

import com.spokiy.echoesofthedeep.server.block.EDBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class EDSkullBlockEntity extends SkullBlockEntity {
    public EDSkullBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public @NotNull BlockEntityType<?> getType() {
        return EDBlockEntities.SKULL.get();
    }

}
