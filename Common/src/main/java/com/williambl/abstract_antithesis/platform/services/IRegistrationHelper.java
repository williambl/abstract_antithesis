package com.williambl.abstract_antithesis.platform.services;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.intellij.lang.annotations.Identifier;

import java.util.function.Supplier;

public interface IRegistrationHelper {
    public <T extends Item> Supplier<T> registerItem(String name, Supplier<T> sup);
    public <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> sup);
    public <T extends BlockEntityType<?>> Supplier<T> registerBEType(String name, Supplier<T> sup);
}
