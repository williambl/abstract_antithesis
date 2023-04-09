package com.williambl.abstract_antithesis.mixin;

import com.williambl.abstract_antithesis.cart_banner.CartBanners;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MinecartItem.class)
public class MinecartItemMixin {
    @Inject(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void abstractantithesis$addBannerPattern(UseOnContext $$0, CallbackInfoReturnable<InteractionResult> cir, Level $$1, BlockPos $$2, BlockState $$3, ItemStack stack, RailShape $$5, double $$6, AbstractMinecart cart) {
        CartBanners.setCartBannerPatterns(cart, stack);
    }
}
