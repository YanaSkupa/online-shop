package org.yana.service;

import java.util.HashMap;
import java.util.Map;

public class ServiceFactory {
    private Map<String, Service> services = new HashMap<>();

    {
        services.put("registrationService", new RegistrationService());
    }

    public Service getService(String serviceName) {
        return services.get(serviceName);
    }
}
