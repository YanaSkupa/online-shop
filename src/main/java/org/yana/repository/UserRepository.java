package org.yana.repository;

import org.yana.db.DataBase;
import org.yana.persistance.User;

import java.util.Optional;

import static org.yana.db.DbCollectionNames.USERS_DB_COLLECTION;

public class UserRepository implements Repository {
    private final DataBase db = DataBase.getInstance();
    public Optional<User> getUserByLogin(String login){
        return db.data.get(USERS_DB_COLLECTION)
                .values()
                .stream()
                .map(u -> (User) u)
                .filter(u -> u.getLogin().equals(login))
                .findFirst();
    }

    public User saveUser(User user) {
        db.data.get(USERS_DB_COLLECTION).put(user.getId(), user);
        return user;
    }

}
