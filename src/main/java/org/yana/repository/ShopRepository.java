package org.yana.repository;

import org.yana.db.DataBase;
import org.yana.persistance.Shop;

import java.util.List;
import java.util.stream.Collectors;

import static org.yana.db.DbCollectionNames.SHOP_DB_COLLECTION;

public class ShopRepository implements Repository {
    private final DataBase db = DataBase.getInstance();
    public void saveNewShop(Shop shop) {
            db.data.get(SHOP_DB_COLLECTION).put(shop.getId(), shop);
    }

    public List<Shop> getShopsByUserLogin(String login){
        return db.data.get(SHOP_DB_COLLECTION)
                .values()
                .stream()
                .map(s -> (Shop) s)
                .filter(s -> s.getOwner().getLogin().equals(login))
                .collect(Collectors.toList());
    }

    public List<Shop> getAllShops() {
        return db.data.get(SHOP_DB_COLLECTION)
                .values()
                .stream()
                .map(s -> (Shop) s)
                .collect(Collectors.toList());
    }
}
