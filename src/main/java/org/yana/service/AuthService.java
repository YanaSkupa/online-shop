package org.yana.service;

import org.yana.persistance.Role;
import org.yana.persistance.User;
import org.yana.repository.RepositoryFactory;
import org.yana.repository.UserRepository;

import java.util.Optional;

public class AuthService implements Service {

    private final RepositoryFactory repositoryFactory = RepositoryFactory.getInstance();
    private final UserRepository userRepository = repositoryFactory.getUserRepository();

    public Optional<User> checkAuthority(String login, String password) {
        return userRepository
                .getUserByLogin(login)
                .filter(u -> u.getPassword().equals(password));
    }

    public User regNewUser(String login, String password, Role role, Double balance) {
        return userRepository.saveUser(new User(login, password, role, balance));
    }

}
