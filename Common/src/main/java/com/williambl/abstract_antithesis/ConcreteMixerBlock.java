package com.williambl.abstract_antithesis;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConcreteMixerBlock extends BaseEntityBlock {

    public ConcreteMixerBlock(BlockBehaviour.Properties p_54479_) {
        super(p_54479_);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState p_54559_) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos p_153573_, BlockState p_153574_) {
        return new ConcreteMixerBlockEntity(p_153573_, p_153574_);
    }

    @Override
    public void onRemove(BlockState p_54531_, Level p_54532_, BlockPos p_54533_, BlockState p_54534_, boolean p_54535_) {
        if (!p_54531_.is(p_54534_.getBlock())) {
            BlockEntity blockentity = p_54532_.getBlockEntity(p_54533_);
            if (blockentity instanceof ConcreteMixerBlockEntity mixer) {
                mixer.dropAll();
            }

            super.onRemove(p_54531_, p_54532_, p_54533_, p_54534_, p_54535_);
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState p_54503_) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState p_54520_, Level p_54521_, BlockPos p_54522_) {
        BlockEntity blockentity = p_54521_.getBlockEntity(p_54522_);
        if (blockentity instanceof ConcreteMixerBlockEntity mixer) {
            return mixer.getRedstoneSignal();
        }

        return 0;
    }

    @Override
    public InteractionResult use(BlockState p_54524_, Level p_54525_, BlockPos p_54526_, Player p_54527_, InteractionHand p_54528_, BlockHitResult p_54529_) {
        ItemStack itemstack = p_54527_.getItemInHand(p_54528_);
        var be = p_54525_.getBlockEntity(p_54526_);
        if (!(be instanceof ConcreteMixerBlockEntity mixer)) {
            return InteractionResult.FAIL;
        }

        return mixer.tryInsertFromHand(p_54527_, itemstack.split(1));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
        return $$0.isClientSide() ? null : createTickerHelper($$2, AARegistry.CONCRETE_MIXER_BE.get(), ConcreteMixerBlockEntity::serverTick);
    }
}