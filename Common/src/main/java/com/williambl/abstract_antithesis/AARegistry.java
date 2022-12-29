package com.williambl.abstract_antithesis;

import com.williambl.abstract_antithesis.platform.Services;
import com.williambl.abstract_antithesis.platform.services.IRegistrationHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
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

    public static final Supplier<EntityType<TrackLayingCart>> TRACK_LAYING_CART = Services.REGISTRATION_HELPER.registerMinecartType("track_laying_cart", TrackLayingCart::new);
    public static final Supplier<CustomCartItem> TRACK_LAYING_CART_ITEM = Services.REGISTRATION_HELPER.registerItem("track_laying_cart", () -> new CustomCartItem(TrackLayingCart::new, new Item.Properties().tab(CreativeModeTab.TAB_TRANSPORTATION)));

    public static final Supplier<ItemDetectorRailBlock> ITEM_DETECTOR_RAIL_BLOCK = Services.REGISTRATION_HELPER.registerBlock("item_detector_rail", () -> new ItemDetectorRailBlock(BlockBehaviour.Properties.copy(Blocks.DETECTOR_RAIL)));
    public static final Supplier<BlockEntityType<ItemDetectorRailBlockEntity>> ITEM_DETECTOR_RAIL_BE = Services.REGISTRATION_HELPER.registerBEType("item_detector_rail", ItemDetectorRailBlockEntity::new, () -> Set.of(ITEM_DETECTOR_RAIL_BLOCK.get()));

    public static final Supplier<BlockItem> ITEM_DETECTOR_RAIL_ITEM = Services.REGISTRATION_HELPER.registerItem("item_detector_rail", () -> new BlockItem(ITEM_DETECTOR_RAIL_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final Supplier<Item> TRAIN_TICKET = Services.REGISTRATION_HELPER.registerItem("train_ticket", () -> new Item(new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_TRANSPORTATION)));
}
