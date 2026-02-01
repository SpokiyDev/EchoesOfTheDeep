package com.spokiy.echoesofthedeep.server.block;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.server.item.EDItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class EDBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EchoesOfTheDeep.MOD_ID);

    public static final RegistryObject<Block> SCULK_SPROUTS = registerWithItem("sculk_sprouts",
            () -> new SculkSproutsBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_SPROUTS)));
    public static final RegistryObject<Block> SCULK_GUARDIAN = registerWithItem("sculk_guardian",
            () -> new SculkGuardianBlock(BlockBehaviour.Properties.copy(Blocks.SCULK_SHRIEKER).lightLevel((level) -> 3) ));
    public static final RegistryObject<Block> CALIBRATED_SCULK_SHRIEKER = registerWithItem("calibrated_sculk_shrieker",
            () -> new CalibratedSculkShriekerBlock(BlockBehaviour.Properties.copy(Blocks.SCULK_SHRIEKER)));

    public static final RegistryObject<Block> WARDEN_HEAD = BLOCKS.register("warden_head",
            () -> new EDWardenHeadBlock(BlockBehaviour.Properties.of()
                    .strength(1.0F).pushReaction(PushReaction.DESTROY)
                    .instrument(NoteBlockInstrument.SKELETON)));



    private static <T extends Block> RegistryObject<T> registerWithItem(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        EDItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
