package com.williambl.abstract_antithesis.forge;

import com.williambl.abstract_antithesis.AbstractAntithesis;
import com.williambl.abstract_antithesis.Constants;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class AbstractAntithesisInitialiser {
    
    public AbstractAntithesisInitialiser() {
        Constants.LOG.info("Hello Forge world!");
        AbstractAntithesis.init();
    }
    
}