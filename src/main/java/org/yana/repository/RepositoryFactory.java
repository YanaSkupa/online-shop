package org.yana.repository;

import java.util.HashMap;
import java.util.Map;

public class RepositoryFactory  {
    public static RepositoryFactory INSTANCE;
    private final Map<String, Repository> services = new HashMap<>();

    public static RepositoryFactory getInstance() {
        RepositoryFactory localInstance = INSTANCE;
        if (localInstance == null) {
            synchronized (RepositoryFactory.class) {
                localInstance = INSTANCE;
                if (localInstance == null) {
                    localInstance = INSTANCE = new RepositoryFactory();
                }
            }
        }
        return localInstance;
    }

    public UserRepository getUserRepository() {
        UserRepository service = (UserRepository) services.putIfAbsent("userRepository", new UserRepository());
        if (service == null) {
            return (UserRepository) services.get("userRepository");
        }
        return service;
    }

    public ShopRepository getShopRepository() {
        ShopRepository service = (ShopRepository) services.putIfAbsent("shopRepository", new ShopRepository());
        if (service == null) {
            return (ShopRepository) services.get("shopRepository");
        }
        return service;
    }
}
