package org.yana.service;

import java.util.HashMap;
import java.util.Map;

public class ServiceFactory {
    public static ServiceFactory INSTANCE;
    private final Map<String, Service> services = new HashMap<>();

    public static ServiceFactory getInstance() {
        ServiceFactory localInstance = INSTANCE;
        if (localInstance == null) {
            synchronized (ServiceFactory.class) {
                localInstance = INSTANCE;
                if (localInstance == null) {
                    localInstance = INSTANCE = new ServiceFactory();
                }
            }
        }
        return localInstance;
    }
    public AuthService getAuthService() {
        AuthService service = (AuthService) services.putIfAbsent("authService", new AuthService());
        if (service == null) {
            return (AuthService) services.get("authService");
        }
        return service;
    }
    public BuyerService getBuyerService() {
        BuyerService service = (BuyerService) services.putIfAbsent("buyerService", new BuyerService());
        if (service == null) {
            return (BuyerService) services.get("buyerService");
        }
        return service;
    }
    public SellerService getSellerService() {
        SellerService service = (SellerService) services.putIfAbsent("sellerService", new SellerService());
        if (service == null) {
            return (SellerService) services.get("sellerService");
        }
        return service;
    }
}
