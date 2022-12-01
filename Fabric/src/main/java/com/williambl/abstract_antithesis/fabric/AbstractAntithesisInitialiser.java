package com.williambl.abstract_antithesis.fabric;

import com.williambl.abstract_antithesis.AbstractAntithesis;
import com.williambl.abstract_antithesis.Constants;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;

public class AbstractAntithesisInitialiser implements ModInitializer {
    
    @Override
    public void onInitialize() {
        Constants.LOG.info("Hello Fabric world!");
        AbstractAntithesis.init();
    }
}
