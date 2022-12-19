package com.williambl.abstract_antithesis.forge;

import com.williambl.abstract_antithesis.AbstractAntithesis;
import com.williambl.abstract_antithesis.AbstractAntithesisClient;
import com.williambl.abstract_antithesis.Constants;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(Constants.MOD_ID)
public class AbstractAntithesisInitialiser {
    
    public AbstractAntithesisInitialiser() {
        Constants.LOG.info("Hello Forge world!");
        AbstractAntithesis.init();
    }

    @SubscribeEvent
    public void onClientInit(FMLClientSetupEvent event) {
        Constants.LOG.info("Hello Forge Client world!");
        AbstractAntithesisClient.init();
    }
    
}