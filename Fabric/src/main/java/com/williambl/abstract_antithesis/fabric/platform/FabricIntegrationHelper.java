package com.williambl.abstract_antithesis.fabric.platform;

import com.williambl.abstract_antithesis.fabric.CammieIntegration;
import com.williambl.abstract_antithesis.platform.services.IIntegrationHelper;
import net.minecraft.world.entity.vehicle.AbstractMinecart;

import java.util.stream.Stream;

public class FabricIntegrationHelper implements IIntegrationHelper {
    @Override
    public Stream<AbstractMinecart> getConnectedMinecarts(AbstractMinecart start) {
        if (CammieIntegration.hasInstalled()) {
            return CammieIntegration.getTrain(start);
        }

        return Stream.of();
    }

    @Override
    public void registerCartLinkingDispenserBehaviours() {
        if (CammieIntegration.hasInstalled()) {
            CammieIntegration.registerLinkingDispenserBehaviour();
        }
    }
}
