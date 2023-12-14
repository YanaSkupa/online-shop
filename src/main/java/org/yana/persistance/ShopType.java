package org.yana.persistance;

public enum ShopType {
    BUILDING(1, 7), COMPUTER(2, 1), FOOD(3, 0), HOUSEHOLD(4, 3);
    private final int id;
    private final int deliveryTimeDays;

    ShopType(int id, int deliveryTimeDays) {
        this.id = id;
        this.deliveryTimeDays = deliveryTimeDays;
    }

    public int getId() {
        return id;
    }

    public int getDeliveryTimeDays() {
        return deliveryTimeDays;
    }

    public static ShopType getById(int id) {
        for (ShopType type : ShopType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

}
