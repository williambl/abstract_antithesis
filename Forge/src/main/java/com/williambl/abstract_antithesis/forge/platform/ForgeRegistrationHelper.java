package com.williambl.abstract_antithesis.forge.platform;

import com.williambl.abstract_antithesis.Constants;
import com.williambl.abstract_antithesis.platform.services.IRegistrationHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ForgeRegistrationHelper implements IRegistrationHelper {

    private final DeferredRegister<Item> items = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
    private final DeferredRegister<Block> blocks = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);
    private final DeferredRegister<BlockEntityType<?>> blockEntityTypes = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Constants.MOD_ID);

    @Override
    public <T extends Item> Supplier<T> registerItem(String name, Supplier<T> sup) {
        return this.items.register(name, sup);
    }

    @Override
    public <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> sup) {
        return this.blocks.register(name, sup);
    }

    @Override
    public <T extends BlockEntityType<?>> Supplier<T> registerBEType(String name, Supplier<T> sup) {
        return this.blockEntityTypes.register(name, sup);
    }
}
