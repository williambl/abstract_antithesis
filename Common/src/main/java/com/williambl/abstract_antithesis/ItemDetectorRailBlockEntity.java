//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.williambl.abstract_antithesis;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.stream.Stream;

import static com.williambl.abstract_antithesis.AbstractAntithesis.id;

public class ItemDetectorRailBlockEntity extends BlockEntity implements Clearable, WorldlyContainer, MenuProvider {
    private static final int NUM_SLOTS = 9;
    private final NonNullList<ItemStack> stacks = NonNullList.withSize(NUM_SLOTS, ItemStack.EMPTY);

    public ItemDetectorRailBlockEntity(BlockPos $$0, BlockState $$1) {
        super(AARegistry.ITEM_DETECTOR_RAIL_BE.get(), $$0, $$1);
    }

    public boolean anyStacksMatch(Stream<ItemStack> stacks) {
        return stacks.anyMatch(s1 -> this.stacks.stream().anyMatch(s1::sameItem));
    }

    public boolean isValidForSlot(int slot, ItemStack item) {
        return slot < NUM_SLOTS;
    }

    @Override
    public boolean onlyOpCanSetNbt() {
        return true;
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, this.stacks);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.stacks);
    }

    @Override
    public void clearContent() {
        this.stacks.clear();
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction direction) {
        if (direction == Direction.UP) {
            return new int[0];
        }
        int[] slots = new int[NUM_SLOTS];
        for (int i = 0; i < NUM_SLOTS; i++) {
            slots[i] = i;
        }
        return slots;
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, @NotNull ItemStack itemStack, @Nullable Direction direction) {
        return direction != Direction.UP && this.isValidForSlot(i, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int i, @NotNull ItemStack itemStack, @NotNull Direction direction) {
        return direction != Direction.UP && i < NUM_SLOTS;
    }

    @Override
    public int getContainerSize() {
        return NUM_SLOTS;
    }

    @Override
    public boolean isEmpty() {
        return this.stacks.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public @NotNull ItemStack getItem(int i) {
        return i < NUM_SLOTS ? this.stacks.get(i) : ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        var removing = this.getItem(slot);

        if (removing.isEmpty()) {
            return ItemStack.EMPTY;
        }

        return removing.split(amount);
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        var removing = this.getItem(slot);

        this.setItem(slot, ItemStack.EMPTY);

        return removing;
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        if (slot < NUM_SLOTS) {
            this.stacks.set(slot, stack);
        }
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        if (this.level == null || this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return !(player.distanceToSqr((double) this.worldPosition.getX() + 0.5, (double) this.worldPosition.getY() + 0.5, (double) this.worldPosition.getZ() + 0.5) > 64.0);
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player) {
        return new ChestMenu(MenuType.GENERIC_9x1, i, inventory, this, 1);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.abstract_antithesis.item_detector_rail");
    }
}