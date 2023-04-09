package com.williambl.abstract_antithesis.cart_banner;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.List;

public interface CartBannerAccess {
    void setCartBannerPatternsNbt(CompoundTag tag);
    CompoundTag getCartBannerPatternsNbt();
    default List<Pair<Holder<BannerPattern>, DyeColor>> getCartBannerPatterns() {
        var tag = this.getCartBannerPatternsNbt();
        var baseColour = tag == null ? DyeColor.WHITE : DyeColor.byId(tag.getInt("Base"));
        ListTag patterns = null;
        if (tag != null && tag.contains("Patterns", Tag.TAG_LIST)) {
            patterns = tag.getList("Patterns", Tag.TAG_COMPOUND).copy();
        }

        return BannerBlockEntity.createPatterns(baseColour, patterns);
    }

    default boolean hasCartBannerPatterns() {
        return this.getCartBannerPatternsNbt() != null;
    }
}
