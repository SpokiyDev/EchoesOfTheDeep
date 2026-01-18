package com.spokiy.echoesofthedeep.server.recipe;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EDRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, EchoesOfTheDeep.MOD_ID);

    public static final RegistryObject<RecipeSerializer<SculkShriekerRecipe>> SCULK_SHRIEKER_SERIALIZER =
            SERIALIZERS.register("sculk_shrieker", () -> SculkShriekerRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
