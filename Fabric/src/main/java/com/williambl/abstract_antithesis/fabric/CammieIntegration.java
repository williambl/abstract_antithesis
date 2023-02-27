package com.williambl.abstract_antithesis.fabric;

import dev.cammiescorner.cammiesminecarttweaks.api.Linkable;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class CammieIntegration {
    public static boolean hasInstalled() {
        return FabricLoader.getInstance().isModLoaded("minecarttweaks");
    }

    public static Stream<AbstractMinecart> getTrain(AbstractMinecart cart) {
        var parents = Stream.iterate(cart, c -> ((Linkable)c).getLinkedParent() != null, c -> ((Linkable)c).getLinkedParent());
        var children = Stream.iterate(cart, c -> ((Linkable)c).getLinkedChild() != null, c -> ((Linkable)c).getLinkedChild());
        return Stream.concat(parents, children).filter(c -> c != cart);
    }

    public static void registerLinkingDispenserBehaviour() {
        DispenserBlock.registerBehavior(Blocks.CHAIN, new OptionalDispenseItemBehavior() {
            @Override
            protected ItemStack execute(@NotNull BlockSource blockSource, @NotNull ItemStack itemStack) {
                var pos = DispenserBlock.getDispensePosition(blockSource);
                var level = blockSource.getLevel();
                var carts = level.getEntities(EntityTypeTest.forClass(AbstractMinecart.class), new AABB(pos.x() - 1., pos.y() - 1., pos.z() - 1., pos.x() + 1., pos.y() + 1., pos.z() + 1.), $ -> true);
                AbstractMinecart lastCart = null;
                for (var cart : carts) {
                    if (lastCart == null) {
                        lastCart = cart;
                        continue;
                    }

                    if (((Linkable)cart).getLinkedChild() == null && ((Linkable)lastCart).getLinkedParent() == null) {
                        Linkable.setParentChild((Linkable) cart, (Linkable) lastCart);
                        this.setSuccess(true);
                        itemStack.shrink(1);
                        return itemStack;
                    } else if (((Linkable)cart).getLinkedParent() == null && ((Linkable)lastCart).getLinkedChild() == null) {
                        Linkable.setParentChild((Linkable) lastCart, (Linkable) cart);
                        this.setSuccess(true);
                        itemStack.shrink(1);
                        return itemStack;
                    } else {
                        lastCart = cart;
                    }
                }
                this.setSuccess(false);
                return itemStack;
            }
        });
    }
}
