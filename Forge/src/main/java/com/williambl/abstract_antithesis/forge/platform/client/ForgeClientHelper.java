package com.williambl.abstract_antithesis.forge.platform.client;

import com.williambl.abstract_antithesis.Constants;
import com.williambl.abstract_antithesis.platform.services.client.IClientHelper;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid= Constants.MOD_ID)
public class ForgeClientHelper implements IClientHelper {
    private static final List<RendererProviderEntry<?>> rendererProviders = new ArrayList<>();

    @Override
    public <T extends Entity> void registerEntityRenderer(EntityType<T> type, EntityRendererProvider<T> rendererSup) {
        rendererProviders.add(new RendererProviderEntry<>(type, rendererSup));
    }

    @SubscribeEvent
    public static void onRendererRegistration(EntityRenderersEvent.RegisterRenderers event) {
        for (var entry : rendererProviders) {
            entry.register(event);
        }
    }

    private record RendererProviderEntry<T extends Entity>(EntityType<? extends T> type, EntityRendererProvider<T> rendererProvider) {
        void register(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(this.type, this.rendererProvider);
        }
    }
}
