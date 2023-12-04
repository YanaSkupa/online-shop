package org.yana.service;

import org.yana.db.DataBase;
import org.yana.db.DataBaseImpl;
import org.yana.persistance.Role;
import org.yana.persistance.User;

import static org.yana.db.DbCollectionNames.USERS_DB_COLLECTION;

public class RegistrationService implements Service {
    private final DataBase db = DataBaseImpl.getInstance();

    public User register(String login, String password, Role role) {
        System.out.println(db);
        User newUser = new User(login, password, role);
        return (User) db.saveNewEntity(USERS_DB_COLLECTION, newUser);
    }
}
