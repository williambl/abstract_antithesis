package com.williambl.abstract_antithesis;

import com.williambl.abstract_antithesis.platform.Services;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.MinecartFurnace;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.stream.Stream;

public class AbstractAntithesis {

    public static void init() {
        new AARegistry();
        registerDispenserBehaviours();
    }

    public static void registerDispenserBehaviours() {
        Services.INTEGRATIONS.registerCartLinkingDispenserBehaviours();
        Stream.of(Items.COAL, Items.CHARCOAL).forEach(item -> DispenserBlock.registerBehavior(item, new OptionalDispenseItemBehavior() {
            @Override
            protected ItemStack execute(BlockSource blockSource, ItemStack stack) {
                var pos = DispenserBlock.getDispensePosition(blockSource);
                var level = blockSource.getLevel();
                this.setSuccess(level.getEntities(EntityTypeTest.forClass(AbstractMinecart.class), new AABB(pos.x() - 1., pos.y() - 1., pos.z() - 1., pos.x() + 1., pos.y() + 1., pos.z() + 1.), $ -> true)
                        .stream()
                        .filter(MinecartFurnace.class::isInstance)
                        .map(MinecartFurnace.class::cast)
                        .filter(cart -> cart.fuel + 3600 <= 32000)
                        .findAny()
                        .map(cart -> {
                            cart.fuel += 3600;
                            var dir = cart.getMotionDirection();
                            cart.xPush = dir.getStepX();
                            cart.zPush = dir.getStepZ();
                            stack.shrink(1);
                            return true;
                        }).orElse(false));
                return stack;
            }
        }));
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(Constants.MOD_ID, path);
    }
}