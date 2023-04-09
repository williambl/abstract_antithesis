package com.williambl.abstract_antithesis.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.williambl.abstract_antithesis.cart_banner.CartBannerAccess;
import com.williambl.abstract_antithesis.cart_banner.CartBanners;
import com.williambl.abstract_antithesis.client.cart_banner.CartBannersClient;
import com.williambl.abstract_antithesis.platform.Services;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.MinecartModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecartRenderer.class)
public abstract class MinecartRendererMixin<T extends AbstractMinecart> extends EntityRenderer<T> {
    private @Unique ModelPart bannerPart;

    protected MinecartRendererMixin(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void abstractantithesis$setupBannerPart(EntityRendererProvider.Context ctx, ModelLayerLocation modelLayerLocation, CallbackInfo ci) {
        this.bannerPart = ctx.bakeLayer(CartBannersClient.CART_BANNER);
    }

    @Inject(method = "render(Lnet/minecraft/world/entity/vehicle/AbstractMinecart;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V"))
    private void abstractantithesis$renderMinecartBanner(T cart, float yaw, float partialTicks, PoseStack poses, MultiBufferSource buffers, int lighting, CallbackInfo ci) {
        CartBannerAccess banners = Services.INTEGRATIONS.getBannerAccess(cart);
        var patterns = banners.getCartBannerPatterns();
        poses.pushPose();
        //poses.scale(1.0f, 0.5f, 1.0f);
        poses.scale(-1.0F, -1.0F, 1.0F);
        BannerRenderer.renderPatterns(poses, buffers, lighting, OverlayTexture.NO_OVERLAY, this.bannerPart, ModelBakery.SHIELD_BASE, false, patterns);
        poses.popPose();
    }
}
