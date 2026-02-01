package com.spokiy.echoesofthedeep.datagen.block;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.server.block.EDBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Locale;
import java.util.Objects;

import static net.minecraft.Util.prefix;

public class EDBlockStateProvider extends BlockStateProvider {
    protected static final ResourceLocation SOLID = ResourceLocation.withDefaultNamespace("solid");
    protected static final ResourceLocation CUTOUT = ResourceLocation.withDefaultNamespace("cutout");
    protected static final ResourceLocation CUTOUT_MIPPED = ResourceLocation.withDefaultNamespace("cutout_mipped");
    protected static final ResourceLocation TRANSLUCENT = ResourceLocation.withDefaultNamespace("translucent");

    public EDBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, EchoesOfTheDeep.MOD_ID, exFileHelper);

    }

    @Override
    protected void registerStatesAndModels() {
        randomVariantBlock(EDBlocks.SCULK_SPROUTS, "sculk_sprouts_0", "sculk_sprouts_1");
        blockWithExistingModel(EDBlocks.SCULK_GUARDIAN);
        blockWithExistingModel(EDBlocks.CALIBRATED_SCULK_SHRIEKER);
        blockStateWithPath(EDBlocks.WARDEN_HEAD, "block/skull");
    }

    public static String name(Block block) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
    }

    private void blockStateWithPath(RegistryObject<Block> block, String path) {
        this.simpleBlock(
                block.get(),
                new ModelFile.ExistingModelFile(
                        ResourceLocation.fromNamespaceAndPath("minecraft", path),
                        models().existingFileHelper
                )
        );
    }

    private void blockWithExistingModel(RegistryObject<Block> block) {
        this.simpleBlock(
                block.get(),
                new ModelFile.ExistingModelFile(
                        modLoc("block/" + name(block.get())),
                        models().existingFileHelper
                )
        );
    }

    private void randomVariantBlock(RegistryObject<Block> block, String... modelNames) {
        ConfiguredModel[] models = new ConfiguredModel[modelNames.length];

        for (int i = 0; i < modelNames.length; i++) {
            models[i] = new ConfiguredModel(
                    new ModelFile.ExistingModelFile(
                            modLoc("block/" + modelNames[i]),
                            models().existingFileHelper
                    )
            );
        }

        getVariantBuilder(block.get())
                .partialState()
                .setModels(models);
    }


    public void crossBlock(RegistryObject<Block> cross) {
        this.simpleBlock(cross.get(), models().cross(name(cross.get()), blockTexture(cross.get())).renderType(CUTOUT));
    }

}
