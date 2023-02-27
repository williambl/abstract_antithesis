package com.williambl.abstract_antithesis;

import com.williambl.abstract_antithesis.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class AbstractAntithesis {

    public static void init() {
        new AARegistry();
        Services.INTEGRATIONS.registerCartLinkingDispenserBehaviours();
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(Constants.MOD_ID, path);
    }
}