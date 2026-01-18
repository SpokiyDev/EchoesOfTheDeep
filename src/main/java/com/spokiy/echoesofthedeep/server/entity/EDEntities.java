package com.spokiy.echoesofthedeep.server.entity;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.server.block.entity.CalibratedSculkShriekerShriekEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EDEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, EchoesOfTheDeep.MOD_ID);

    public static final RegistryObject<EntityType<CalibratedSculkShriekerShriekEntity>> CALIBRATED_SCULK_SHRIEKER_SHRIEK =
            ENTITIES.register("calibrated_sculk_shrieker_shriek",
                    () -> registerEntity(EntityType.Builder
                            .of(CalibratedSculkShriekerShriekEntity::new, MobCategory.MISC)
                            .sized(1.0F, 0.5F)
                            .clientTrackingRange(64)
                            .updateInterval(1),
                            "calibrated_sculk_shrieker_shriek"));

    private static EntityType registerEntity(EntityType.Builder builder, String entityName) {
        return builder.build(entityName);
    }

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
