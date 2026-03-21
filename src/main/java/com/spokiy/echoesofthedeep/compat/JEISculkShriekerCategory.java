package com.spokiy.echoesofthedeep.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.server.recipe.SculkShriekerRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class JEISculkShriekerCategory implements IRecipeCategory<SculkShriekerRecipe> {
    public static final ResourceLocation UID = EchoesOfTheDeep.prefix("sculk_shrieker");
    public static final ResourceLocation TEXTURE = EchoesOfTheDeep.prefix("textures/gui/sculk_shrieker_jei.png");

    public static final RecipeType<SculkShriekerRecipe> SCULK_SHRIEKER_TYPE =
            new RecipeType<>(UID, SculkShriekerRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final Component localizedName;

    public JEISculkShriekerCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 162, 80);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Blocks.SCULK_SHRIEKER));
        this.localizedName = Component.translatable("block.minecraft.sculk_shrieker");
    }


    @Override
    public @NotNull RecipeType<SculkShriekerRecipe> getRecipeType() {
        return SCULK_SHRIEKER_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return this.localizedName;
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull SculkShriekerRecipe recipe,
                          @NotNull IFocusGroup focusGroup)
    {
        for (int i = 0; i < 9; i++) {
            // Add an empty slot
            var slot = builder.addSlot(RecipeIngredientRole.INPUT, 1 + i * 18, 1);

            // Draw an item
            if (i < recipe.getCountedIngredients().size()) {
                SculkShriekerRecipe.CountedIngredient counted = recipe.getCountedIngredients().get(i);

                for (ItemStack stack : counted.ingredient().getItems()) {
                    ItemStack displayStack = stack.copy();
                    displayStack.setCount(counted.count());
                    slot.addItemStack(displayStack);
                }
            }
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 81 - 8, 80 - 8 - 13)
                .addItemStack(recipe.getResultItem(net.minecraft.core.RegistryAccess.EMPTY));
    }
}
