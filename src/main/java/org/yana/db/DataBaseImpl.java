package org.yana.db;


import org.yana.persistance.ObjectWithData;

import java.util.*;

public class DataBaseImpl implements DataBase {

    public static DataBaseImpl INSTANCE;

    public DataBaseImpl() {
        loadDataCollections();
    }

    public static DataBaseImpl getInstance() {
        DataBaseImpl localInstance = INSTANCE;
        if (localInstance == null) {
            synchronized (DataBaseImpl.class) {
                localInstance = INSTANCE;
                if (localInstance == null) {
                    localInstance = INSTANCE = new DataBaseImpl();
                }
            }
        }
        return localInstance;
    }
    private final Map<DbCollectionNames, Map<UUID, ObjectWithData>> data = new HashMap<>();
    @Override
    public ObjectWithData saveNewEntity(DbCollectionNames collectionName, ObjectWithData value) {
        if (data.get(collectionName).containsKey(value.getId())) {
            throw new RuntimeException(String.format("Value by key '%s' already exists.", value.getId()));
        }
        data.get(collectionName).put(value.getId(), value);
        return value;
    }

    @Override
    public void saveButch(DbCollectionNames collectionName, List<ObjectWithData> values) {
        values.forEach(e -> saveNewEntity(collectionName, e));
    }


    @Override
    public ObjectWithData getEntity(DbCollectionNames collectionName, UUID id) {
        ObjectWithData object = data.get(collectionName).get(id);
        if (object == null) {
            System.out.printf("Value by key '%s' does not exists.", id);
        }
        return object;
    }
    @Override
    public List<ObjectWithData> getAllEntities(DbCollectionNames collectionName) {
        return new ArrayList<>(data.get(collectionName).values());
    }
    @Override
    public ObjectWithData updateEntity(DbCollectionNames collectionName, UUID id, ObjectWithData value) {
        ObjectWithData object = data.get(collectionName).get(id);
        if (object == null) {
            System.out.printf("Value by key '%s' does not exists.", id);
        }
        data.get(collectionName).put(id, value);
        return data.get(collectionName).get(id);
    }
    public List<ObjectWithData> updateMany(DbCollectionNames collectionName, Map<UUID, ObjectWithData> values) {
        List<ObjectWithData> objects = new ArrayList<>();
        for (Map.Entry<UUID, ObjectWithData> entry : values.entrySet()) {
            objects.add(updateEntity(collectionName, entry.getKey(), entry.getValue()));
        }
        return objects;
    }
    @Override
    public boolean isEntityExists(DbCollectionNames collectionName, UUID id) {
        return data.get(collectionName).containsKey(id);
    }
    @Override
    public void deleteEntity(DbCollectionNames collectionName, UUID id) {
        data.get(collectionName).remove(id);
    }
    @Override
    public Map<DbCollectionNames, Map<UUID, ObjectWithData>> getData() {
        return data;
    }
    private void loadDataCollections() {
        data.put(DbCollectionNames.USERS_DB_COLLECTION, new HashMap<>());
    }
}