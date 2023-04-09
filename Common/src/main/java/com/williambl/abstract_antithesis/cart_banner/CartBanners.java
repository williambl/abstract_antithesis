package com.williambl.abstract_antithesis.cart_banner;

import com.williambl.abstract_antithesis.platform.Services;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class CartBanners {
    public static void setCartBannerPatterns(AbstractMinecart cart, ItemStack stack) {
        if (hasBannerPatterns(stack)) {
            CompoundTag compoundtag = Objects.requireNonNull(stack.getTag()).getCompound("cart_banner");
            Services.INTEGRATIONS.getBannerAccess(cart).setCartBannerPatternsNbt(compoundtag);
        }
    }

    public static void setItemBannerPatterns(ItemStack cartStack, ItemStack bannerStack) {
        CompoundTag bannerTag = BlockItem.getBlockEntityData(bannerStack);
        CompoundTag bannerTagCopy = bannerTag == null ? new CompoundTag() : bannerTag.copy();
        bannerTagCopy.putInt("Base", ((BannerItem) bannerStack.getItem()).getColor().getId());
        cartStack.getOrCreateTag().put("cart_banner", bannerTagCopy);
    }

    public static boolean hasBannerPatterns(ItemStack stack) {
        return (stack.getTag() != null && stack.getTag().contains("cart_banner", Tag.TAG_COMPOUND));
    }
}
