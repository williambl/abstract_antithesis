package com.williambl.abstract_antithesis.forge.platform;

import com.williambl.abstract_antithesis.cart_banner.CartBannerAccess;
import com.williambl.abstract_antithesis.platform.services.IIntegrationHelper;
import net.minecraft.world.entity.vehicle.AbstractMinecart;

import java.util.stream.Stream;

public class ForgeIntegrationHelper implements IIntegrationHelper {
    @Override
    public Stream<AbstractMinecart> getConnectedMinecarts(AbstractMinecart start) {
        return Stream.of();
    }

    @Override
    public void registerCartLinkingDispenserBehaviours() {}

    @Override
    public CartBannerAccess getBannerAccess(AbstractMinecart cart) {
        return null; //todo
    }
}