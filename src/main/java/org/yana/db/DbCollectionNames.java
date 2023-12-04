package org.yana.db;

public enum DbCollectionNames {

    USERS_DB_COLLECTION("users");

    private final String name;

    DbCollectionNames(String name) {
        this.name = name;
    }
}
