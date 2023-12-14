package org.yana.persistance;

import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.*;

@Getter
@Setter
public class Shop extends ObjectWithData {
    private String name;
    private ShopType type;
    private User owner;
    private Double balance;
    private Map<String, Item> items = new HashMap<>();

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public Shop(String name, ShopType type, User owner) {
        this.setId(UUID.randomUUID());
        this.name = name;
        this.type = type;
        this.owner = owner;
        this.balance = 0.0;
    }

    public void addItem(Item item) {
        if (items.containsKey(item.getName())) {
            Item existingItem = items.get(item.getName());
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
        } else {
            items.put(item.getName(), item);
        }
    }

    @Override
    public String toString() {
        return "[" + type + "]" + " - \"" + name + "\", Balance: " + df.format(balance) + "$";
    }
}
