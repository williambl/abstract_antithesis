//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.williambl.abstract_antithesis;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.LecternMenu;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.williambl.abstract_antithesis.AbstractAntithesis.id;

@MethodsReturnNonnullByDefault
public class ConcreteMixerBlockEntity extends BlockEntity implements Clearable, WorldlyContainer, RecipeHolder {
    public static final int DYE_SLOT = 0;
    public static final int SAND_SLOT = 1;
    public static final int GRAVEL_SLOT = 2;
    public static final int RESULT_SLOT = 3;
    public static final int NUM_SLOTS = 4;

    public static final TagKey<Item> DYES = TagKey.create(Registry.ITEM_REGISTRY, id("concrete_dyes"));
    public static final TagKey<Item> SAND = TagKey.create(Registry.ITEM_REGISTRY, id("concrete_sand"));
    public static final TagKey<Item> GRAVEL = TagKey.create(Registry.ITEM_REGISTRY, id("concrete_gravel"));
    private final RecipeManager.CachedCheck<Container, ConcreteRecipe> quickCheck;

    ItemStack dye = ItemStack.EMPTY;
    ItemStack sand = ItemStack.EMPTY;
    ItemStack gravel = ItemStack.EMPTY;
    ItemStack result = ItemStack.EMPTY;
    @Nullable Recipe<?> recipeUsed = null;
    int ticksSinceCrafted = 0;

    public ConcreteMixerBlockEntity(BlockPos $$0, BlockState $$1) {
        super(AARegistry.CONCRETE_MIXER_BE.get(), $$0, $$1);
        this.quickCheck = RecipeManager.createCheck(AARegistry.CONCRETE_RECIPE_TYPE.get());
    }

    public boolean isValidForSlot(int slot, ItemStack item) {
        return switch (slot) {
            case DYE_SLOT -> item.is(DYES);
            case SAND_SLOT -> item.is(SAND);
            case GRAVEL_SLOT -> item.is(GRAVEL);
            default -> false;
        };
    }

    public int getRedstoneSignal() {
        double fullness = (this.dye.getCount() + this.sand.getCount() + this.gravel.getCount()) / 64.0*3.0;
        return Mth.floor(fullness * 15.0F);
    }

    @Override
    public boolean onlyOpCanSetNbt() {
        return true;
    }

    @Override
    public void load(CompoundTag $$0) {
        super.load($$0);
        if ($$0.contains("Dye", Tag.TAG_COMPOUND)) {
            this.dye = ItemStack.of($$0.getCompound("Dye"));
        } else {
            this.dye = ItemStack.EMPTY;
        }

        if ($$0.contains("Sand", Tag.TAG_COMPOUND)) {
            this.sand = ItemStack.of($$0.getCompound("Sand"));
        } else {
            this.sand = ItemStack.EMPTY;
        }

        if ($$0.contains("Gravel", Tag.TAG_COMPOUND)) {
            this.gravel = ItemStack.of($$0.getCompound("Gravel"));
        } else {
            this.gravel = ItemStack.EMPTY;
        }

        if ($$0.contains("Result", Tag.TAG_COMPOUND)) {
            this.result = ItemStack.of($$0.getCompound("Result"));
        } else {
            this.result = ItemStack.EMPTY;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag $$0) {
        super.saveAdditional($$0);
        if (!this.dye.isEmpty()) {
            $$0.put("Dye", this.dye.save(new CompoundTag()));
        }

        if (!this.sand.isEmpty()) {
            $$0.put("Sand", this.sand.save(new CompoundTag()));
        }

        if (!this.gravel.isEmpty()) {
            $$0.put("Gravel", this.gravel.save(new CompoundTag()));
        }

        if (!this.result.isEmpty()) {
            $$0.put("Result", this.result.save(new CompoundTag()));
        }
    }

    @Override
    public void clearContent() {
        this.dye = ItemStack.EMPTY;
        this.sand = ItemStack.EMPTY;
        this.gravel = ItemStack.EMPTY;
        this.result = ItemStack.EMPTY;
    }

    @Override
    public int[] getSlotsForFace(@NotNull Direction direction) {
        return direction == Direction.DOWN ? new int[] { RESULT_SLOT }: new int[] { DYE_SLOT, SAND_SLOT, GRAVEL_SLOT };
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction) {
        return this.isValidForSlot(i, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return direction == Direction.DOWN && i == RESULT_SLOT;
    }

    @Override
    public int getContainerSize() {
        return NUM_SLOTS;
    }

    @Override
    public boolean isEmpty() {
        return this.dye.isEmpty()
                && this.sand.isEmpty()
                && this.gravel.isEmpty()
                && this.result.isEmpty();
    }

    @Override
    public ItemStack getItem(int i) {
        return switch (i) {
            case DYE_SLOT -> this.dye;
            case SAND_SLOT -> this.sand;
            case GRAVEL_SLOT -> this.gravel;
            case RESULT_SLOT -> this.result;
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        var removing = switch (slot) {
            case DYE_SLOT -> ConcreteMixerBlockEntity.this.dye;
            case SAND_SLOT -> ConcreteMixerBlockEntity.this.sand;
            case GRAVEL_SLOT -> ConcreteMixerBlockEntity.this.gravel;
            case RESULT_SLOT -> ConcreteMixerBlockEntity.this.result;
            default -> ItemStack.EMPTY;
        };

        if (removing.isEmpty()) {
            return ItemStack.EMPTY;
        }

        this.ticksSinceCrafted = 0;
        return removing.split(amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        var removing = switch (slot) {
            case DYE_SLOT -> ConcreteMixerBlockEntity.this.dye;
            case SAND_SLOT -> ConcreteMixerBlockEntity.this.sand;
            case GRAVEL_SLOT -> ConcreteMixerBlockEntity.this.gravel;
            case RESULT_SLOT -> ConcreteMixerBlockEntity.this.result;
            default -> ItemStack.EMPTY;
        };

        this.setItem(slot, ItemStack.EMPTY);

        return removing;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        switch (slot) {
            case DYE_SLOT -> ConcreteMixerBlockEntity.this.dye = stack;
            case SAND_SLOT -> ConcreteMixerBlockEntity.this.sand = stack;
            case GRAVEL_SLOT -> ConcreteMixerBlockEntity.this.gravel = stack;
            case RESULT_SLOT -> ConcreteMixerBlockEntity.this.result = stack;
        }
    }

    @Override
    public boolean stillValid(Player player) {
        if (ConcreteMixerBlockEntity.this.level.getBlockEntity(ConcreteMixerBlockEntity.this.worldPosition) != ConcreteMixerBlockEntity.this) {
            return false;
        } else {
            return !(player.distanceToSqr((double) ConcreteMixerBlockEntity.this.worldPosition.getX() + 0.5, (double) ConcreteMixerBlockEntity.this.worldPosition.getY() + 0.5, (double) ConcreteMixerBlockEntity.this.worldPosition.getZ() + 0.5) > 64.0);
        }
    }

    @Override
    public void setRecipeUsed(@Nullable Recipe<?> recipe) {
        this.recipeUsed = recipe;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        this.tryCraft(null);
    }

    @Nullable
    @Override
    public Recipe<?> getRecipeUsed() {
        return this.recipeUsed;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ConcreteMixerBlockEntity be) {
        if (!be.result.isEmpty()) {
            if (be.ticksSinceCrafted++ >= 20) {
                var resEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, be.result);
                level.addFreshEntity(resEntity);
                be.result = ItemStack.EMPTY;
                be.ticksSinceCrafted = 0;
            }
        }
    }

    public void tryCraft(@Nullable Player player) {
        if (this.level == null) {
            return;
        }

        var recipe = this.quickCheck.getRecipeFor(this, this.level);

        if (recipe.isPresent()) {
            var result = recipe.get().assemble(this);
            this.dye.shrink(1);
            this.sand.shrink(4);
            this.gravel.shrink(4);
            int resultToStore = this.result.isEmpty() ?
                    result.getCount() :
                    ItemStack.isSameItemSameTags(this.result, recipe.get().getResultItem()) ?
                            this.result.getMaxStackSize() - this.result.getCount() :
                            0;

            this.result = new ItemStack(result.getItem(), result.split(resultToStore).getCount() + this.result.getCount());
            if (result.hasTag()) {
                this.result.setTag(result.getTag());
            }

            if (player instanceof ServerPlayer serverPlayer) {
                this.setRecipeUsed(this.level, serverPlayer, recipe.get());
                this.awardUsedRecipes(player);
            }

            this.ticksSinceCrafted = 0;
        }
    }

    public InteractionResult tryInsertFromHand(Player player, ItemStack stack) {
        if (player.level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (stack.is(DYES) && (this.dye.isEmpty() || ItemStack.isSameItemSameTags(this.dye, stack))) {
            int maxAmount = this.dye.isEmpty() ? stack.getCount() : this.dye.getMaxStackSize() - this.dye.getCount();
            int amount = ContainerHelper.clearOrCountMatchingItems(
                    stack,
                    s -> s.is(DYES),
                    maxAmount,
                    true
            );
            this.dye = new ItemStack(stack.getItem(), amount + this.dye.getCount());
            stack.shrink(amount);
            if (stack.hasTag()) {
                this.dye.setTag(Objects.requireNonNull(stack.getTag()).copy());
            }

            this.tryCraft(player);
            return InteractionResult.SUCCESS;
        }
        if (stack.is(SAND) && (this.sand.isEmpty() || ItemStack.isSameItemSameTags(this.sand, stack))) {
            int maxAmount = this.sand.isEmpty() ? stack.getCount() : this.sand.getMaxStackSize() - this.sand.getCount();
            int amount = ContainerHelper.clearOrCountMatchingItems(
                    stack,
                    s -> s.is(SAND),
                    maxAmount,
                    true
            );
            this.sand = new ItemStack(stack.getItem(), amount + this.sand.getCount());
            stack.shrink(amount);
            if (stack.hasTag()) {
                this.sand.setTag(Objects.requireNonNull(stack.getTag()).copy());
            }

            this.tryCraft(player);
            return InteractionResult.SUCCESS;
        }
        if (stack.is(GRAVEL) && (this.gravel.isEmpty() || ItemStack.isSameItemSameTags(this.gravel, stack))) {
            int maxAmount = this.gravel.isEmpty() ? stack.getCount() : this.gravel.getMaxStackSize() - this.gravel.getCount();
            int amount = ContainerHelper.clearOrCountMatchingItems(
                    stack,
                    s -> s.is(GRAVEL),
                    maxAmount,
                    true
            );
            this.gravel = new ItemStack(stack.getItem(), amount + this.gravel.getCount());
            stack.shrink(amount);
            if (stack.hasTag()) {
                this.gravel.setTag(Objects.requireNonNull(stack.getTag()).copy());
            }

            this.tryCraft(player);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public void dropAll() {
        if (this.level == null) {
            return;
        }

        var dyes = new ItemEntity(this.level, this.getBlockPos().getX()+0.5, this.getBlockPos().getY()+0.5, this.getBlockPos().getZ()+0.5, this.dye);
        var sands = new ItemEntity(this.level, this.getBlockPos().getX()+0.5, this.getBlockPos().getY()+0.5, this.getBlockPos().getZ()+0.5, this.sand);
        var gravels = new ItemEntity(this.level, this.getBlockPos().getX()+0.5, this.getBlockPos().getY()+0.5, this.getBlockPos().getZ()+0.5, this.gravel);
        var results = new ItemEntity(this.level, this.getBlockPos().getX()+0.5, this.getBlockPos().getY()+0.5, this.getBlockPos().getZ()+0.5, this.result);

        this.level.addFreshEntity(dyes);
        this.level.addFreshEntity(sands);
        this.level.addFreshEntity(gravels);
        this.level.addFreshEntity(results);
    }
}