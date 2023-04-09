package com.williambl.abstract_antithesis.client.cart_banner;

import com.williambl.abstract_antithesis.platform.ClientServices;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

import static com.williambl.abstract_antithesis.AbstractAntithesis.id;

public class CartBannersClient {
    public static final ModelLayerLocation CART_BANNER = new ModelLayerLocation(id("cart_banner"), "main");
    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        $$1.addOrReplaceChild("left", CubeListBuilder.create().texOffs(2, 2).addBox(-5.0F, -10.0F, -8.05F, 10.0F, 20.0F, 0.01F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0f, 0.0F, 1.5708F));
        $$1.addOrReplaceChild("right", CubeListBuilder.create().texOffs(2, 2).addBox(-5.0F, -10.0F, -8.05F, 10.0F, 20.0F, 0.01F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.14195F, 0.0F, -1.5708F));
        return LayerDefinition.create($$0, 64, 64);
    }

    public static void init() {
        ClientServices.CLIENT.registerLayerDefinition(CART_BANNER, CartBannersClient::createBodyLayer);
    }
}
