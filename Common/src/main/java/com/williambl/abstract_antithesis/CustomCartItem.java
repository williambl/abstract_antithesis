package com.williambl.abstract_antithesis;

import com.williambl.abstract_antithesis.cart_banner.CartBanners;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;

@MethodsReturnNonnullByDefault
public class CustomCartItem extends Item {
    private static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {
            Direction dir = source.getBlockState().getValue(DispenserBlock.FACING);
            Level level = source.getLevel();
            double x = source.x() + (double)dir.getStepX() * 1.125;
            double y = Math.floor(source.y()) + (double)dir.getStepY();
            double z = source.z() + (double)dir.getStepZ() * 1.125;
            BlockPos pos = source.getPos().relative(dir);
            BlockState state = level.getBlockState(pos);
            RailShape railShape = state.getBlock() instanceof BaseRailBlock ? state.getValue(((BaseRailBlock)state.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
            double yOffset;
            if (state.is(BlockTags.RAILS)) {
                if (railShape.isAscending()) {
                    yOffset = 0.6;
                } else {
                    yOffset = 0.1;
                }
            } else {
                if (!state.isAir() || !level.getBlockState(pos.below()).is(BlockTags.RAILS)) {
                    return this.defaultDispenseItemBehavior.dispense(source, stack);
                }

                BlockState stateBelow = level.getBlockState(pos.below());
                RailShape railShapeBelow = stateBelow.getBlock() instanceof BaseRailBlock ? stateBelow.getValue(((BaseRailBlock)stateBelow.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
                if (dir != Direction.DOWN && railShapeBelow.isAscending()) {
                    yOffset = -0.4;
                } else {
                    yOffset = -0.9;
                }
            }

            AbstractMinecart cart = ((CustomCartItem)stack.getItem()).factory.createCart(level, x, y + yOffset, z);
            if (stack.hasCustomHoverName()) {
                cart.setCustomName(stack.getHoverName());
            }

            CartBanners.setCartBannerPatterns(cart, stack);

            level.addFreshEntity(cart);
            stack.shrink(1);
            return stack;
        }

        @Override
        protected void playSound(BlockSource source) {
            source.getLevel().levelEvent(1000, source.getPos(), 0);
        }
    };
    final MinecartFactory factory;

    public CustomCartItem(MinecartFactory factory, Item.Properties properties) {
        super(properties);
        this.factory = factory;
        DispenserBlock.registerBehavior(this, DISPENSE_ITEM_BEHAVIOR);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        BlockState state = level.getBlockState(pos);
        if (!state.is(BlockTags.RAILS)) {
            return InteractionResult.FAIL;
        } else {
            ItemStack stack = ctx.getItemInHand();
            if (!level.isClientSide) {
                RailShape railShape = state.getBlock() instanceof BaseRailBlock ? state.getValue(((BaseRailBlock)state.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
                double yOffset = 0.0;
                if (railShape.isAscending()) {
                    yOffset = 0.5;
                }

                AbstractMinecart cart = this.factory.createCart(level, (double)pos.getX() + 0.5, (double)pos.getY() + 0.0625 + yOffset, (double)pos.getZ() + 0.5);
                if (stack.hasCustomHoverName()) {
                    cart.setCustomName(stack.getHoverName());
                }

                CartBanners.setCartBannerPatterns(cart, stack);

                level.addFreshEntity(cart);
                level.gameEvent(GameEvent.ENTITY_PLACE, pos, Context.of(ctx.getPlayer(), level.getBlockState(pos.below())));
            }

            stack.shrink(1);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }


    @FunctionalInterface
    public interface MinecartFactory {
        AbstractMinecart createCart(Level level, double x, double y, double z);
    }
}
