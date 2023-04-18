package com.williambl.abstract_antithesis;

import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.williambl.abstract_antithesis.AbstractAntithesis.id;

public class AATags {
    public static final TagKey<Item> DECORATABLE_MINECARTS = TagKey.create(Registry.ITEM_REGISTRY, id("decoratable_minecarts"));
    public static final TagKey<Item> DYES = TagKey.create(Registry.ITEM_REGISTRY, id("concrete_dyes"));
    public static final TagKey<Item> SAND = TagKey.create(Registry.ITEM_REGISTRY, id("concrete_sand"));
    public static final TagKey<Item> GRAVEL = TagKey.create(Registry.ITEM_REGISTRY, id("concrete_gravel"));
}
