package com.spokiy.echoesofthedeep.server.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SculkShriekerRecipe implements Recipe<Container> {
    public record CountedIngredient(Ingredient ingredient, int count) {}
    private final NonNullList<CountedIngredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;

    public SculkShriekerRecipe(NonNullList<CountedIngredient> inputItems, ItemStack output, ResourceLocation id) {
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
    }

    @Override
    public boolean matches(@NotNull Container container, Level level) {
        if (level.isClientSide) return false;

        for (CountedIngredient ingredient : inputItems) {
            int found = 0;

            for (int slot = 0; slot < container.getContainerSize(); slot++) {
                ItemStack stack = container.getItem(slot);
                if (stack.isEmpty()) continue;

                if (ingredient.ingredient().test(stack)) {
                    found += stack.getCount();
                }
            }

            if (found < ingredient.count()) {
                return false;
            }
        }

        for (int slot = 0; slot < container.getContainerSize(); slot++) {
            ItemStack stack = container.getItem(slot);
            if (stack.isEmpty()) continue;

            boolean allowed = false;

            for (CountedIngredient ingredient : inputItems) {
                if (ingredient.ingredient().test(stack)) {
                    allowed = true;
                    break;
                }
            }

            if (!allowed) {
                return false;
            }
        }

        return true;
    }



    @Override
    public @NotNull ItemStack assemble(@NotNull Container container, @NotNull RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        for (CountedIngredient ing : inputItems) {
            list.add(ing.ingredient());
        }
        return list;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<SculkShriekerRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "sculk_shrieker";
    }

    public static class Serializer implements RecipeSerializer<SculkShriekerRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(EchoesOfTheDeep.MOD_ID, "sculk_shrieker");

        @Override
        public @NotNull SculkShriekerRecipe fromJson(
                @NotNull ResourceLocation recipeId,
                @NotNull JsonObject json
        ) {
            ItemStack output = ShapedRecipe.itemStackFromJson(
                    GsonHelper.getAsJsonObject(json, "output")
            );

            JsonArray ingredientsJson = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<CountedIngredient> inputs = NonNullList.create();

            for (int i = 0; i < ingredientsJson.size(); i++) {
                JsonObject obj = ingredientsJson.get(i).getAsJsonObject();

                Ingredient ingredient = Ingredient.fromJson(obj);
                int count = GsonHelper.getAsInt(obj, "count", 1);

                inputs.add(new CountedIngredient(ingredient, count));
            }

            return new SculkShriekerRecipe(inputs, output, recipeId);
        }


        @Override
        public @Nullable SculkShriekerRecipe fromNetwork(
                @NotNull ResourceLocation recipeId,
                FriendlyByteBuf buffer
        ) {
            int size = buffer.readInt();
            NonNullList<CountedIngredient> inputs = NonNullList.create();

            for (int i = 0; i < size; i++) {
                Ingredient ingredient = Ingredient.fromNetwork(buffer);
                int count = buffer.readInt();
                inputs.add(new CountedIngredient(ingredient, count));
            }

            ItemStack output = buffer.readItem();
            return new SculkShriekerRecipe(inputs, output, recipeId);
        }


        @Override
        public void toNetwork(FriendlyByteBuf buffer, SculkShriekerRecipe recipe) {
            buffer.writeInt(recipe.inputItems.size());

            for (CountedIngredient ingredient : recipe.inputItems) {
                ingredient.ingredient().toNetwork(buffer);
                buffer.writeInt(ingredient.count());
            }

            buffer.writeItem(recipe.output);
        }

    }
}
