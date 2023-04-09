package com.williambl.abstract_antithesis.fabric;

import com.williambl.abstract_antithesis.AbstractAntithesis;
import com.williambl.abstract_antithesis.Constants;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.entity.vehicle.AbstractMinecart;

public class AbstractAntithesisInitialiser implements ModInitializer, EntityComponentInitializer {
    
    @Override
    public void onInitialize() {
        Constants.LOG.info("Hello Fabric world!");
        AbstractAntithesis.init();
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(AbstractMinecart.class, MinecartBannerComponent.KEY, MinecartBannerComponent::new);
    }
}
