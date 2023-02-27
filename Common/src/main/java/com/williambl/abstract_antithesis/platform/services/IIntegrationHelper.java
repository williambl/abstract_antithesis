package com.williambl.abstract_antithesis.platform.services;

import net.minecraft.world.entity.vehicle.AbstractMinecart;

import java.util.stream.Stream;

public interface IIntegrationHelper {

    Stream<AbstractMinecart> getConnectedMinecarts(AbstractMinecart start);

    void registerCartLinkingDispenserBehaviours();
}
