package com.williambl.abstract_antithesis.platform;

import com.williambl.abstract_antithesis.Constants;
import com.williambl.abstract_antithesis.platform.services.IPlatformHelper;
import com.williambl.abstract_antithesis.platform.services.IRegistrationHelper;
import com.williambl.abstract_antithesis.platform.services.client.IClientHelper;

import java.util.ServiceLoader;

public class ClientServices {

    public static final IClientHelper CLIENT = load(IClientHelper.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Constants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
