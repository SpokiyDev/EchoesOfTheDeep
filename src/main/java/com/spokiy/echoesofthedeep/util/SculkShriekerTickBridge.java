package com.spokiy.echoesofthedeep.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface SculkShriekerTickBridge {
    void echoesOfTheDeep$tickServer(Level level, BlockPos blockPos, BlockState state);
}
