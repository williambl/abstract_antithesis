package com.williambl.abstract_antithesis;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DetectorRailBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ItemDetectorRailBlock extends DetectorRailBlock implements EntityBlock {
    public ItemDetectorRailBlock(Properties $$0) {
        super($$0);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof ItemDetectorRailBlockEntity railBlockEntity) {
                player.openMenu(railBlockEntity);
                //player.awardStat(Stats.INTERACT_WITH_ITEM_DETECTOR_RAIL);
            }

            return InteractionResult.CONSUME;
        }
    }


    @Override
    protected <T extends AbstractMinecart> @NotNull List<T> getInteractingMinecartOfType(@NotNull Level level, @NotNull BlockPos pos, @NotNull Class<T> cartClass, @NotNull Predicate<Entity> predicate) {
        var blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof ItemDetectorRailBlockEntity railBlockEntity)) {
            return new ArrayList<>();
        } else {
            return super.getInteractingMinecartOfType(level, pos, cartClass, predicate.and(cart ->
                this.canAcceptCart(cart, railBlockEntity)
            ));
        }
    }

    protected boolean canAcceptCart(Entity cart, @NotNull ItemDetectorRailBlockEntity blockEntity) {
        return cart.getSelfAndPassengers().anyMatch(entity ->
            getStacksFromEntity(entity).map(blockEntity::anyStacksMatch).orElse(false)
        );
    }

    private static Optional<Stream<ItemStack>> getStacksFromEntity(Entity entity) {
        return Optional.<Stream<ItemStack>>empty().or(() ->
                entity instanceof InventoryCarrier carrier
                        ? Optional.of(IntStream.range(0, carrier.getInventory().getContainerSize()).mapToObj(carrier.getInventory()::getItem))
                        : Optional.empty()
        ).or(() ->
                entity instanceof Player player
                        ? Optional.of(IntStream.range(0, player.getInventory().getContainerSize()).mapToObj(player.getInventory()::getItem))
                        : Optional.empty()
        ).or(() ->
                entity instanceof Container container
                        ? Optional.of(IntStream.range(0, container.getContainerSize()).mapToObj(container::getItem))
                        : Optional.empty()
        );
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new ItemDetectorRailBlockEntity(blockPos, blockState);
    }
}
