package com.williambl.abstract_antithesis.cart_banner;

import com.williambl.abstract_antithesis.platform.Services;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.ItemStack;

public class CartBanners {
    public static void setCartBannerPatterns(AbstractMinecart cart, ItemStack stack) {
        if ((stack.getTag() != null && stack.getTag().contains("cart_banner", 10))) {
            CompoundTag compoundtag = stack.getTag().getCompound("cart_banner");
            Services.INTEGRATIONS.getBannerAccess(cart).setCartBannerPatternsNbt(compoundtag);
        }
    }
}
