package com.williambl.abstract_antithesis.fabric.platform;

import com.williambl.abstract_antithesis.platform.services.IRegistrationHelper;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

import static com.williambl.abstract_antithesis.AbstractAntithesis.id;

public class FabricRegistrationHelper implements IRegistrationHelper {
    @Override
    public <T extends Item> Supplier<T> registerItem(String name, Supplier<T> sup) {
        final var res = Registry.register(Registry.ITEM, id(name), sup.get());
        return () -> res;
    }

    @Override
    public <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> sup) {
        final var res = Registry.register(Registry.BLOCK, id(name), sup.get());
        return () -> res;
    }

    @Override
    public <T extends BlockEntityType<?>> Supplier<T> registerBEType(String name, Supplier<T> sup) {
        final var res = Registry.register(Registry.BLOCK_ENTITY_TYPE, id(name), sup.get());
        return () -> res;
    }
}
