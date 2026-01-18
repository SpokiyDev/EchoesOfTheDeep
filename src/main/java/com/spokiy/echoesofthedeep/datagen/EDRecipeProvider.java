package com.spokiy.echoesofthedeep.datagen;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.server.item.EDItems;
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
        // Crafting Table
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, EDItems.WARNING_CLOCK.get())
                .pattern("SSS")
                .pattern("SCS")
                .pattern("SSS")
                .define('C', Items.CLOCK)
                .define('S', Items.ECHO_SHARD)
                .unlockedBy("has_echo_shard", has(Items.ECHO_SHARD)).save(consumer);

        // Armor Trims
        trimSmithing(consumer, EDItems.SHRIEKER_SMITHING_TEMPLATE.get(), ResourceLocation.fromNamespaceAndPath(EchoesOfTheDeep.MOD_ID, "shrieker"));
        copySmithingTemplate(consumer, EDItems.SHRIEKER_SMITHING_TEMPLATE.get(), Items.SCULK);

    }

}