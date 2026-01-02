package com.spokiy.echoesofthedeep.item.alchemy;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EDPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, EchoesOfTheDeep.MOD_ID);

    public static final RegistryObject<Potion> DARKNESS = POTIONS.register("darkness", () -> new Potion(new MobEffectInstance(MobEffects.DARKNESS, 3600)));
    public static final RegistryObject<Potion> LONG_DARKNESS = POTIONS.register("long_darkness", () -> new Potion(new MobEffectInstance(MobEffects.DARKNESS, 9600)));

    public static ItemStack createPotion(RegistryObject<Potion> potion){
        return  PotionUtils.setPotion(new ItemStack(Items.POTION), potion.get());
    }
    public static ItemStack createPotion(Potion potion){
        return  PotionUtils.setPotion(new ItemStack(Items.POTION), potion);
    }

    public static void registerPotionsRecipes() {
        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(Potions.AWKWARD)), Ingredient.of(Items.ECHO_SHARD), createPotion(DARKNESS)));
        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(DARKNESS)), Ingredient.of(Items.REDSTONE), createPotion(LONG_DARKNESS)));
    }
    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }

}
