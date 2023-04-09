package com.williambl.abstract_antithesis.cart_banner;

import com.williambl.abstract_antithesis.AARegistry;
import com.williambl.abstract_antithesis.AATags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MinecartDecorationRecipe extends CustomRecipe {
    public MinecartDecorationRecipe(ResourceLocation $$0) {
        super($$0);
    }

    @Override
    public boolean matches(CraftingContainer container, @NotNull Level level) {
        ItemStack cartStack = ItemStack.EMPTY;
        ItemStack bannerStack = ItemStack.EMPTY;

        for(int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof BannerItem) {
                    if (!bannerStack.isEmpty()) {
                        return false;
                    }

                    bannerStack = stack;
                } else {
                    if (!stack.is(AATags.DECORATABLE_MINECARTS)) {
                        return false;
                    }

                    if (!cartStack.isEmpty()) {
                        return false;
                    }

                    if (CartBanners.hasBannerPatterns(stack)) {
                        return false;
                    }

                    cartStack = stack;
                }
            }
        }

        return !cartStack.isEmpty() && !bannerStack.isEmpty();
    }

    @Override
    public @NotNull ItemStack assemble(CraftingContainer container) {
        ItemStack bannerStack = ItemStack.EMPTY;
        ItemStack cartStack = ItemStack.EMPTY;

        for(int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof BannerItem) {
                    bannerStack = stack;
                } else if (stack.is(AATags.DECORATABLE_MINECARTS)) {
                    cartStack = stack.copy();
                }
            }
        }

        if (!cartStack.isEmpty()) {
            CartBanners.setItemBannerPatterns(cartStack, bannerStack);
        }

        return cartStack;
    }

    @Override
    public boolean canCraftInDimensions(int x, int y) {
        return x * y >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AARegistry.MINECART_DECORATION_RECIPE_SERIALIZER.get();
    }
}
