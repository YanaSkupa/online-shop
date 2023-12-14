package org.yana.persistance;

import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.UUID;

@Getter
@Setter
public class Item extends ObjectWithData {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    private String name;
    private Double price;
    private Integer quantity;
    private ShopType shopType;

    public Item(String name, Double price, Integer quantity, ShopType shopType){
        this.setId(UUID.randomUUID());
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.shopType = shopType;
    }

    @Override
    public String toString() {
        return name + " x" + quantity + " " + df.format(price) + "$";
    }
}
