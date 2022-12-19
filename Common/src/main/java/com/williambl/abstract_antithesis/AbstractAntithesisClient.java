package com.williambl.abstract_antithesis;

import com.williambl.abstract_antithesis.platform.ClientServices;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.MinecartRenderer;

public class AbstractAntithesisClient {
    public static void init() {
        ClientServices.CLIENT.registerEntityRenderer(AARegistry.TRACK_LAYING_CART.get(), ctx -> new MinecartRenderer<>(ctx, ModelLayers.MINECART));
    }
}
