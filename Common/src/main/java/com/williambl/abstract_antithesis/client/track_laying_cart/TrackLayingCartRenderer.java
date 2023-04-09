package com.williambl.abstract_antithesis.client.track_laying_cart;

import com.mojang.blaze3d.vertex.PoseStack;
import com.williambl.abstract_antithesis.track_laying_cart.TrackLayingCart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.williambl.abstract_antithesis.AbstractAntithesis.id;

@ParametersAreNonnullByDefault
public class TrackLayingCartRenderer extends MinecartRenderer<TrackLayingCart> {
    private static final ResourceLocation CART_TEXTURE = id("textures/entity/minecart/track_laying_cart.png");

    private final BlockRenderDispatcher blockRenderer;

    public TrackLayingCartRenderer(EntityRendererProvider.Context ctx, ModelLayerLocation modelLayer) {
        super(ctx, modelLayer);
        this.blockRenderer = ctx.getBlockRenderDispatcher();
    }

    @Override
    protected void renderMinecartContents(TrackLayingCart cart, float partialTicks, BlockState displayState, PoseStack poses, MultiBufferSource buffers, int light) {
        var displayStack = cart.getDisplayStack();
        if (!(displayStack.getItem() instanceof BlockItem bI)) {
            return;
        }

        var state = bI.getBlock().defaultBlockState();

        poses.pushPose();
        for (int i = 0; i < displayStack.getCount(); i++) {
            this.blockRenderer.renderSingleBlock(state, poses, buffers, light, OverlayTexture.NO_OVERLAY);
            poses.translate(0.0, 0.05, 0.0);
        }
        poses.popPose();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(TrackLayingCart cart) {
        return CART_TEXTURE;
    }
}
