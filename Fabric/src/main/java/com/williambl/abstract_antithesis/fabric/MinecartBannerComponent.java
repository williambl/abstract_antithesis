package com.williambl.abstract_antithesis.fabric;

import com.williambl.abstract_antithesis.cart_banner.CartBannerAccess;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import static com.williambl.abstract_antithesis.AbstractAntithesis.id;

public class MinecartBannerComponent implements Component, CartBannerAccess, AutoSyncedComponent {
    private static final String NBT_TAG_KEY = "banner_patterns";
    private CompoundTag bannerPatterns = null;
    private final Object cart;

    public MinecartBannerComponent(Object cart) {
        this.cart = cart;
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        this.bannerPatterns = tag.contains(NBT_TAG_KEY, Tag.TAG_COMPOUND) ? tag.getCompound(NBT_TAG_KEY).copy() : null;
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        if (this.bannerPatterns != null) {
            tag.put(NBT_TAG_KEY, this.bannerPatterns.copy());
        }
    }

    @Override
    public void setCartBannerPatternsNbt(CompoundTag tag) {
        this.bannerPatterns = tag.copy();
        KEY.sync(this.cart);
    }

    @Override
    public CompoundTag getCartBannerPatternsNbt() {
        return this.bannerPatterns;
    }

    public static final ComponentKey<MinecartBannerComponent> KEY = ComponentRegistry.getOrCreate(id("minecart_banner"), MinecartBannerComponent.class);
}
