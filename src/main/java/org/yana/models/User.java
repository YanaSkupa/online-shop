package org.yana.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    String login;
    String password;
    Role role;
}
