package com.williambl.abstract_antithesis.mixin;

import com.williambl.abstract_antithesis.cart_banner.CartBanners;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(targets = "net/minecraft/world/item/MinecartItem$1")
public class MinecartItemDispenseBehaviourMixin {
    @Inject(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void abstractantithesis$addBannerPattern(BlockSource $$0, ItemStack stack, CallbackInfoReturnable<ItemStack> cir, Direction $$2, Level $$3, double $$4, double $$5, double $$6, BlockPos $$7, BlockState $$8, RailShape $$9, double $$16, AbstractMinecart cart) {
        CartBanners.setCartBannerPatterns(cart, stack);
    }
}
