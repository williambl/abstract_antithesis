package com.williambl.abstract_antithesis.concrete_mixer;

import com.google.gson.JsonObject;
import com.williambl.abstract_antithesis.AARegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class ConcreteRecipe implements Recipe<Container> {
    public final Ingredient dye;
    public final Ingredient sand;
    public final Ingredient gravel;
    public final ItemStack result;
    private final ResourceLocation id;

    public ConcreteRecipe(ResourceLocation p_44523_, Ingredient dye, Ingredient sand, Ingredient gravel, ItemStack p_44526_) {
        this.id = p_44523_;
        this.dye = dye;
        this.sand = sand;
        this.gravel = gravel;
        this.result = p_44526_;
    }

    @Override
    public boolean matches(Container container, @NotNull Level level) {
        var dyeStack = container.getItem(0);
        var sandStack = container.getItem(1);
        var gravelStack = container.getItem(2);
        return dyeStack.getCount() >= 1 && this.dye.test(dyeStack)
                && sandStack.getCount() >= 4 && this.sand.test(sandStack)
                && gravelStack.getCount() >= 4 && this.gravel.test(gravelStack);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull Container p_44531_) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_44528_, int p_44529_) {
        return p_44528_ * p_44529_ >= 2;
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        return this.result;
    }

    @Override
    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(Blocks.WHITE_CONCRETE_POWDER);
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return this.id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return AARegistry.CONCRETE_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return AARegistry.CONCRETE_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<ConcreteRecipe> {
        @Override
        public @NotNull ConcreteRecipe fromJson(@NotNull ResourceLocation p_44562_, @NotNull JsonObject p_44563_) {
            Ingredient dye = Ingredient.fromJson(GsonHelper.getAsJsonObject(p_44563_, "dye"));
            Ingredient sand = Ingredient.fromJson(GsonHelper.getAsJsonObject(p_44563_, "sand"));
            Ingredient gravel = Ingredient.fromJson(GsonHelper.getAsJsonObject(p_44563_, "gravel"));
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(p_44563_, "result"));
            return new ConcreteRecipe(p_44562_, dye, sand, gravel, result);
        }

        @Override
        public @NotNull ConcreteRecipe fromNetwork(@NotNull ResourceLocation p_44565_, @NotNull FriendlyByteBuf p_44566_) {
            Ingredient dye = Ingredient.fromNetwork(p_44566_);
            Ingredient sand = Ingredient.fromNetwork(p_44566_);
            Ingredient gravel = Ingredient.fromNetwork(p_44566_);
            ItemStack itemstack = p_44566_.readItem();
            return new ConcreteRecipe(p_44565_, dye, sand, gravel, itemstack);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf p_44553_, ConcreteRecipe p_44554_) {
            p_44554_.dye.toNetwork(p_44553_);
            p_44554_.sand.toNetwork(p_44553_);
            p_44554_.gravel.toNetwork(p_44553_);
            p_44553_.writeItem(p_44554_.result);
        }
    }
}
