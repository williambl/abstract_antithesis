package com.williambl.abstract_antithesis.track_laying_cart;

import com.williambl.abstract_antithesis.AARegistry;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.vehicle.AbstractMinecartContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TrackLayingCart extends AbstractMinecartContainer {
    private static final EntityDataAccessor<ItemStack> DISPLAY_STACK_KEY = SynchedEntityData.defineId(TrackLayingCart.class, EntityDataSerializers.ITEM_STACK);
    private static final int MAX_DISPLAY_RAILS = 14;

    private final Runnable ticker;

    public TrackLayingCart(EntityType<? extends TrackLayingCart> cart, Level level) {
        super(cart, level);
        this.ticker = this.createTicker(level);
    }

    public TrackLayingCart(Level level, double x, double y, double z) {
        super(AARegistry.TRACK_LAYING_CART.get(), x, y, z, level);
        this.ticker = this.createTicker(level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DISPLAY_STACK_KEY, ItemStack.EMPTY);
    }

    private Runnable createTicker(Level level) {
        return level instanceof ServerLevel ? () -> {
            var myPos = new BlockPos(Math.floor(this.getX()), Math.floor(this.getY()), Math.floor(this.getZ()));
            var nextPos = myPos.relative(this.getMotionDirection());
            boolean shouldPlace = this.level.getBlockState(nextPos).isAir();

            var railData = this.getRail(shouldPlace);

            int adjustedRailNum = railData.topRail == Items.AIR ?
                    0 :
                    Math.max(1, (int) (Mth.clamp(railData.railNum/(this.getContainerSize()*this.getMaxStackSize()*0.45f), 0f, 1f)*MAX_DISPLAY_RAILS));
            ItemStack displayStack = new ItemStack(railData.topRail(), adjustedRailNum);
            ItemStack currentDisplayStack = this.getDisplayStack();
            if (displayStack.getItem() != currentDisplayStack.getItem() || displayStack.getCount() != currentDisplayStack.getCount()) {
                this.setDisplayStack(displayStack);
            }

            if (shouldPlace) {
                railData.railState.ifPresent(rail ->
                        this.level.setBlockAndUpdate(nextPos, rail)
                );
            }
        } : () -> {
        };
    }

    private RailInvData getRail(boolean remove) {
        Optional<BlockState> railState = Optional.empty();
        Item topRail = Items.AIR;
        int railNum = 0;
        for(int i = 0; i < this.getContainerSize(); ++i) {
            ItemStack stack = this.getItem(i);
            if (stack.getItem() instanceof BlockItem bI && bI.getBlock() instanceof BaseRailBlock) {
                railNum += stack.getCount();
                if (railState.isEmpty()) {
                    if (remove) {
                        stack.shrink(1);
                    }
                    railState = Optional.of(bI.getBlock().defaultBlockState());
                    topRail = stack.getItem();
                }
            }
        }

        return new RailInvData(railState, topRail, railNum);
    }

    public ItemStack getDisplayStack() {
        return this.entityData.get(DISPLAY_STACK_KEY);
    }

    private void setDisplayStack(ItemStack stack) {
        this.entityData.set(DISPLAY_STACK_KEY, stack);
    }

    @Override
    public BlockState getDefaultDisplayBlockState() {
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
        return AARegistry.TRACK_LAYING_CART_ITEM.get();
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new ChestMenu(MenuType.GENERIC_9x1, i, inventory, this, 1);
    }

    @Override
    public int getContainerSize() {
        return 9;
    }

    private record RailInvData(Optional<BlockState> railState, Item topRail, int railNum) {}
}
