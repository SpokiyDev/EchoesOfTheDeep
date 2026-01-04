package com.spokiy.echoesofthedeep.datagen;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.registry.EDItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
        import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class EDRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public EDRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        // Shrieker armor trim
        trimSmithing(consumer, EDItems.SHRIEKER_SMITHING_TEMPLATE.get(), ResourceLocation.fromNamespaceAndPath(EchoesOfTheDeep.MOD_ID, "shrieker"));
        copySmithingTemplate(consumer, EDItems.SHRIEKER_SMITHING_TEMPLATE.get(), Items.SCULK);

    }

}