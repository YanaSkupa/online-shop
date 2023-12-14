package org.yana.persistance;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class User extends ObjectWithData {
    private String login;
    private String password;
    private Role role;
    private Double balance;
    private Map<String, Item> cart = new HashMap<>();

    public User(String login, String password, Role role, Double balance) {
        this.setId(UUID.randomUUID());
        this.login = login;
        this.password = password;
        this.role = role;
        this.balance = balance;
    }

    public void addToCart(Item item) {
        if (cart.containsKey(item.getName())) {
            Item existingItem = cart.get(item.getName());
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
        } else {
            cart.put(item.getName(), item);
        }
    }
}
