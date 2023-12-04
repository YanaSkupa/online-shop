package org.yana.persistance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class User extends ObjectWithData{
    String login;
    String password;
    Role role;

    public User(String login, String password, Role role) {
        this.setId(UUID.randomUUID());
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
