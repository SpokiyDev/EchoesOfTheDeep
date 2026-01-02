package com.spokiy.echoesofthedeep.datagen;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.item.EDItems;
import com.spokiy.echoesofthedeep.item.alchemy.EDPotions;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
        import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class EDRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public EDRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        trimSmithing(pWriter, EDItems.SHRIEKER_SMITHING_TEMPLATE.get(), ResourceLocation.fromNamespaceAndPath(EchoesOfTheDeep.MOD_ID, "shrieker"));

    }

}