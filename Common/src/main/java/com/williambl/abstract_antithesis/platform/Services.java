package com.williambl.abstract_antithesis.platform;

import com.williambl.abstract_antithesis.Constants;
import com.williambl.abstract_antithesis.platform.services.IIntegrationHelper;
import com.williambl.abstract_antithesis.platform.services.IPlatformHelper;
import com.williambl.abstract_antithesis.platform.services.IRegistrationHelper;

import java.util.ServiceLoader;

public class Services {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final IRegistrationHelper REGISTRATION_HELPER = load(IRegistrationHelper.class);
    public static final IIntegrationHelper INTEGRATIONS = load(IIntegrationHelper.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Constants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
