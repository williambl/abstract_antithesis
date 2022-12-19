package com.williambl.abstract_antithesis.fabric;

import com.williambl.abstract_antithesis.AbstractAntithesis;
import com.williambl.abstract_antithesis.AbstractAntithesisClient;
import com.williambl.abstract_antithesis.Constants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class AbstractAntithesisClientInitialiser implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        Constants.LOG.info("Hello Fabric Client world!");
        AbstractAntithesisClient.init();
    }
}
