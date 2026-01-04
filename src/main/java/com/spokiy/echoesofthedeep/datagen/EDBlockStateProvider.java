package com.spokiy.echoesofthedeep.datagen;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class EDBlockStateProvider extends BlockStateProvider {
    public EDBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, EchoesOfTheDeep.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
//        simpleBlockWithItem(EDBlocks.SCULK_HOWLER.get(), cubeAll(EDBlocks.SCULK_HOWLER.get()));
    }
}
