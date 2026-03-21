package com.spokiy.echoesofthedeep.compat;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.server.recipe.SculkShriekerRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@JeiPlugin
public class JEICompat implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(EchoesOfTheDeep.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new JEISculkShriekerCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registry) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) return;

        RecipeManager recipeManager = minecraft.level.getRecipeManager();
        List<SculkShriekerRecipe> recipes = recipeManager.getAllRecipesFor(SculkShriekerRecipe.Type.INSTANCE);

        registry.addRecipes(JEISculkShriekerCategory.SCULK_SHRIEKER_TYPE, recipes);
    }

    @Override
    public void registerGuiHandlers(@NotNull IGuiHandlerRegistration registry) {
        IModPlugin.super.registerGuiHandlers(registry);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(Blocks.SCULK_SHRIEKER), JEISculkShriekerCategory.SCULK_SHRIEKER_TYPE);
    }
}
