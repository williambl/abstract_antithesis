package com.williambl.abstract_antithesis.fabric.data;

import com.williambl.abstract_antithesis.AARegistry;
import com.williambl.abstract_antithesis.AATags;
import com.williambl.abstract_antithesis.Constants;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.Util;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.Direction;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.RailShape;

import java.util.function.Consumer;

import static com.williambl.abstract_antithesis.AbstractAntithesis.id;

public class AADatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(AAModels::new);
        fabricDataGenerator.addProvider(g -> new AABlockTags(g));
        fabricDataGenerator.addProvider(g -> new AAItemTags(g));
        fabricDataGenerator.addProvider(AARecipes::new);
        fabricDataGenerator.addProvider(AALang::new);
        fabricDataGenerator.addProvider(AALootTables::new);
        fabricDataGenerator.addProvider(AAAdvancements::new);
    }

    private static class AAModels extends FabricModelProvider {
        public AAModels(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
            blockStateModelGenerator.createActiveRail(AARegistry.ITEM_DETECTOR_RAIL_BLOCK.get());
            this.createOneWayRail(blockStateModelGenerator, AARegistry.ONE_WAY_RAIL_BLOCK.get());
        }

        public final void createOneWayRail(BlockModelGenerators generators, Block block) {
            TextureMapping textureMapping = TextureMapping.rail(block);
            ResourceLocation flatModel = ModelTemplates.RAIL_FLAT.create(block, textureMapping, generators.modelOutput);
            ResourceLocation raisedNEModel = ModelTemplates.RAIL_RAISED_NE.create(block, textureMapping, generators.modelOutput);
            ResourceLocation raisedSWModel = ModelTemplates.RAIL_RAISED_SW.create(block, textureMapping, generators.modelOutput);
            generators.createSimpleFlatItemModel(block);
            generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.properties(BlockStateProperties.RAIL_SHAPE_STRAIGHT, BlockStateProperties.HORIZONTAL_FACING)
                    .generate((shape, facing) -> {
                        switch (shape) {
                            case ASCENDING_EAST -> {
                                if (facing == Direction.EAST) {
                                    return Variant.variant().with(VariantProperties.MODEL, raisedNEModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
                                } else {
                                    return Variant.variant().with(VariantProperties.MODEL, raisedSWModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270);
                                }
                            }
                            case ASCENDING_WEST -> {
                                if (facing == Direction.EAST) {
                                    return Variant.variant().with(VariantProperties.MODEL, raisedSWModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
                                } else {
                                    return Variant.variant().with(VariantProperties.MODEL, raisedNEModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270);
                                }
                            }
                            case ASCENDING_NORTH -> {
                                if (facing == Direction.NORTH) {
                                    return Variant.variant().with(VariantProperties.MODEL, raisedNEModel);
                                } else {
                                    return Variant.variant().with(VariantProperties.MODEL, raisedSWModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180);
                                }
                            }
                            case ASCENDING_SOUTH -> {
                                if (facing == Direction.NORTH) {
                                    return Variant.variant().with(VariantProperties.MODEL, raisedSWModel);
                                } else {
                                    return Variant.variant().with(VariantProperties.MODEL, raisedNEModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180);
                                }
                            }
                            case NORTH_SOUTH -> {
                                if (facing == Direction.NORTH) {
                                    return Variant.variant().with(VariantProperties.MODEL, flatModel);
                                } else {
                                    return Variant.variant().with(VariantProperties.MODEL, flatModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180);
                                }
                            }
                            case EAST_WEST -> {
                                if (facing == Direction.EAST) {
                                    return Variant.variant().with(VariantProperties.MODEL, flatModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
                                } else {
                                    return Variant.variant().with(VariantProperties.MODEL, flatModel).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270);
                                }
                            }
                            default -> {
                                return Variant.variant().with(VariantProperties.MODEL, flatModel);
                            }
                        }
                    })));
        }


        @Override
        public void generateItemModels(ItemModelGenerators itemModelGenerator) {
            itemModelGenerator.generateFlatItem(AARegistry.TRAIN_TICKET.get(), ModelTemplates.FLAT_ITEM);
        }
    }

    private static class AABlockTags extends FabricTagProvider.BlockTagProvider {
        public AABlockTags(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        protected void generateTags() {
            this.tag(BlockTags.RAILS).add(AARegistry.ITEM_DETECTOR_RAIL_BLOCK.get(), AARegistry.ONE_WAY_RAIL_BLOCK.get());
        }
    }

    private static class AAItemTags extends FabricTagProvider.ItemTagProvider {
        public AAItemTags(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        protected void generateTags() {
            this.tag(AATags.DYES).add(this.registry.stream().filter(i -> i instanceof DyeItem).toArray(Item[]::new));
            this.tag(AATags.GRAVEL).add(Items.GRAVEL);
            this.getOrCreateTagBuilder(AATags.SAND).forceAddTag(ItemTags.SAND);
            this.tag(AATags.DECORATABLE_MINECARTS).add(Items.MINECART);
        }
    }

    private static class AARecipes extends FabricRecipeProvider {
        public AARecipes(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        protected void generateRecipes(Consumer<FinishedRecipe> exporter) {
            SpecialRecipeBuilder.special(AARegistry.MINECART_DECORATION_RECIPE_SERIALIZER.get())
                    .save(exporter, Constants.MOD_ID + ":minecart_decoration");
            //TODO concrete mixer recipes
            //TODO grinder recipes for removing banners from minecarts
        }
    }

    private static class AALootTables extends FabricBlockLootTableProvider {
        protected AALootTables(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        protected void generateBlockLootTables() {
            this.add(AARegistry.CONCRETE_MIXER_BLOCK.get(), BlockLoot::createSingleItemTable);
            this.add(AARegistry.ONE_WAY_RAIL_BLOCK.get(), BlockLoot::createSingleItemTable);
            this.add(AARegistry.ITEM_DETECTOR_RAIL_BLOCK.get(), BlockLoot::createSingleItemTable);
        }
    }

    //TODO entity loot tables

    private static class AALang extends FabricLanguageProvider {
        protected AALang(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        public void generateTranslations(TranslationBuilder translationBuilder) {
            translationBuilder.add(AARegistry.CONCRETE_MIXER_BLOCK.get(), "Concrete Mixer");
            translationBuilder.add(AARegistry.TRACK_LAYING_CART.get(), "Track-Laying Cart");
            translationBuilder.add(AARegistry.ITEM_DETECTOR_RAIL_BLOCK.get(), "Item Detector Rail");
            translationBuilder.add(Util.makeDescriptionId("container",  id("item_detector_rail")), "Item Detector Rail");
            translationBuilder.add(AARegistry.ONE_WAY_RAIL_BLOCK.get(), "One-Way Rail");
            translationBuilder.add(AARegistry.TRAIN_TICKET.get(), "Train Ticket");
        }
    }

    private static class AAAdvancements extends FabricAdvancementProvider {
        protected AAAdvancements(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        public void generateAdvancement(Consumer<Advancement> consumer) {
        }
    }
}
