package com.williambl.abstract_antithesis;

import com.williambl.abstract_antithesis.platform.Services;
import com.williambl.abstract_antithesis.platform.services.IRegistrationHelper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Set;
import java.util.function.Supplier;

public class AARegistry {
    public static final Supplier<RecipeType<ConcreteRecipe>> CONCRETE_RECIPE_TYPE = Services.REGISTRATION_HELPER.registerRecipeType("concrete_mixing", () -> new RecipeType<>() {
        @Override
        public String toString() {
            return "concrete_mixing";
        }
    });

    public static final Supplier<RecipeSerializer<ConcreteRecipe>> CONCRETE_RECIPE_SERIALIZER = Services.REGISTRATION_HELPER.registerRecipeSerializer("concrete_mixing", ConcreteRecipe.Serializer::new);

    public static final Supplier<ConcreteMixerBlock> CONCRETE_MIXER_BLOCK = Services.REGISTRATION_HELPER.registerBlock("concrete_mixer", () -> new ConcreteMixerBlock(BlockBehaviour.Properties.copy(Blocks.FURNACE).lightLevel($ -> 0)));
    public static final Supplier<BlockEntityType<ConcreteMixerBlockEntity>> CONCRETE_MIXER_BE = Services.REGISTRATION_HELPER.registerBEType("concrete_mixer", ConcreteMixerBlockEntity::new, () -> Set.of(CONCRETE_MIXER_BLOCK.get()));

    public static final Supplier<BlockItem> CONCRETE_MIXER_ITEM = Services.REGISTRATION_HELPER.registerItem("concrete_mixer", () -> new BlockItem(CONCRETE_MIXER_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
}
