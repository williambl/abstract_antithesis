package com.williambl.abstract_antithesis.fabric.platform;

import com.williambl.abstract_antithesis.cart_banner.CartBannerAccess;
import com.williambl.abstract_antithesis.fabric.CammieIntegration;
import com.williambl.abstract_antithesis.fabric.MinecartBannerComponent;
import com.williambl.abstract_antithesis.platform.services.IIntegrationHelper;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPatterns;

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

    @Override
    public CartBannerAccess getBannerAccess(AbstractMinecart cart) {
        return cart.getComponent(MinecartBannerComponent.KEY);
    }
}
