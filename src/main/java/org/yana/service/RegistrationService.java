package org.yana.service;

import org.yana.models.Role;
import org.yana.models.User;

public class RegistrationService implements Service{

    public User register(String login, String password, Role role){
        return new User(login, password, role);
    }
}
