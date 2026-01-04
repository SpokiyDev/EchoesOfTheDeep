package com.spokiy.echoesofthedeep.server.registry;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.server.block.entity.SculkGuardianBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EDBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, EchoesOfTheDeep.MOD_ID);

    public static final RegistryObject<BlockEntityType<SculkGuardianBlockEntity>> SCULK_GUARDIAN =
            BLOCK_ENTITIES.register("sculk_guardian_block_entity", () ->
                    BlockEntityType.Builder.of(SculkGuardianBlockEntity::new,
                            EDBlocks.SCULK_GUARDIAN.get()).build(null));


    public static void register (IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
