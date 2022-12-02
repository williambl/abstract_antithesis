package com.williambl.abstract_antithesis.forge.platform;

import com.williambl.abstract_antithesis.Constants;
import com.williambl.abstract_antithesis.platform.services.IRegistrationHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ForgeRegistrationHelper implements IRegistrationHelper {

    private final DeferredRegister<Item> items = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
    private final DeferredRegister<Block> blocks = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);
    private final DeferredRegister<BlockEntityType<?>> blockEntityTypes = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Constants.MOD_ID);
    private final DeferredRegister<RecipeType<?>> recipeTypes = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Constants.MOD_ID);
    private final DeferredRegister<RecipeSerializer<?>> recipeSerializers = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Constants.MOD_ID);

    @Override
    public <T extends Item> Supplier<T> registerItem(String name, Supplier<T> sup) {
        return this.items.register(name, sup);
    }

    @Override
    public <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> sup) {
        return this.blocks.register(name, sup);
    }

    @Override
    public <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBEType(String name, BiFunction<BlockPos, BlockState, T> factory, Supplier<Set<Block>> blocksSup) {
        return this.blockEntityTypes.register(name, () -> BlockEntityType.Builder.<T>of(factory::apply, blocksSup.get().toArray(new Block[0])).build(null));
    }

    @Override
    public <T extends RecipeType<?>> Supplier<T> registerRecipeType(String name, Supplier<T> sup) {
        return this.recipeTypes.register(name, sup);
    }

    @Override
    public <T extends RecipeSerializer<?>> Supplier<T> registerRecipeSerializer(String name, Supplier<T> sup) {
        return this.recipeSerializers.register(name, sup);
    }
}
