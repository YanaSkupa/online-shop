package org.yana.db;


import org.yana.persistance.ObjectWithData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.yana.db.DbCollectionNames.SHOP_DB_COLLECTION;
import static org.yana.db.DbCollectionNames.USERS_DB_COLLECTION;

public class DataBase{

    public static DataBase INSTANCE;

    public DataBase() {
        loadDataCollections();
    }

    public static DataBase getInstance() {
        DataBase localInstance = INSTANCE;
        if (localInstance == null) {
            synchronized (DataBase.class) {
                localInstance = INSTANCE;
                if (localInstance == null) {
                    localInstance = INSTANCE = new DataBase();
                }
            }
        }
        return localInstance;
    }

    public final Map<DbCollectionNames, Map<UUID, ObjectWithData>> data = new HashMap<>();

    private void loadDataCollections() {
        data.put(USERS_DB_COLLECTION, new HashMap<>());
        data.put(SHOP_DB_COLLECTION, new HashMap<>());
    }
}