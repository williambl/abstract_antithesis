package com.williambl.abstract_antithesis.fabric.platform.client;

import com.williambl.abstract_antithesis.platform.services.client.IClientHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public class FabricClientHelper implements IClientHelper {
    @Override
    public <T extends Entity> void registerEntityRenderer(EntityType<T> type, EntityRendererProvider<T> rendererSup) {
        EntityRendererRegistry.register(type, rendererSup);
    }
}
