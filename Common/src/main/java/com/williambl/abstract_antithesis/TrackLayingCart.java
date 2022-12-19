package com.williambl.abstract_antithesis;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

@MethodsReturnNonnullByDefault
public class TrackLayingCart extends AbstractMinecart {
    private final Runnable ticker;

    protected TrackLayingCart(EntityType<? extends TrackLayingCart> cart, Level level) {
        super(cart, level);
        this.ticker = this.createTicker(level);
    }

    protected TrackLayingCart(Level $$1, double $$2, double $$3, double $$4) {
        super(AARegistry.TRACK_LAYING_CART.get(), $$1, $$2, $$3, $$4);
        this.ticker = this.createTicker($$1);
    }

    private Runnable createTicker(Level level) {
        return level instanceof ServerLevel ? () -> {
            var myPos = new BlockPos(Math.floor(this.getX()), Math.floor(this.getY()), Math.floor(this.getZ()));
            var nextPos = myPos.relative(this.getMotionDirection());
            if (this.level.getBlockState(nextPos).isAir()) {
                this.level.setBlockAndUpdate(nextPos, Blocks.RAIL.defaultBlockState());
            }
        } : () -> {
        };
    }

    @Override
    public BlockState getDisplayBlockState() {
        return Blocks.RAIL.defaultBlockState();
    }

    @Override
    public void tick() {
        super.tick();
        this.ticker.run();
    }

    @Override
    public Type getMinecartType() {
        return Type.SPAWNER;
    }

    @Override
    public ItemStack getPickResult() {
        return this.getDropItem().getDefaultInstance();
    }

    @Override
    public Item getDropItem() {
        return Items.AIR;
    }
}
