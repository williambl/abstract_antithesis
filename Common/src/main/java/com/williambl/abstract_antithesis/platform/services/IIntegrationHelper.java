package com.williambl.abstract_antithesis.platform.services;

import com.williambl.abstract_antithesis.cart_banner.CartBannerAccess;
import net.minecraft.world.entity.vehicle.AbstractMinecart;

import java.util.stream.Stream;

public interface IIntegrationHelper {

    Stream<AbstractMinecart> getConnectedMinecarts(AbstractMinecart start);

    void registerCartLinkingDispenserBehaviours();

    CartBannerAccess getBannerAccess(AbstractMinecart cart);
}
