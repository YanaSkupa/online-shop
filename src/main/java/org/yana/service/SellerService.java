package org.yana.service;

import org.yana.persistance.Item;
import org.yana.persistance.Shop;
import org.yana.persistance.ShopType;
import org.yana.persistance.User;
import org.yana.repository.RepositoryFactory;
import org.yana.repository.ShopRepository;
import org.yana.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SellerService implements Service {
    private static final Double PROFIT_PERCENTAGE = 1.2;
    private final RepositoryFactory repositoryFactory = RepositoryFactory.getInstance();
    private final ShopRepository shopRepository = repositoryFactory.getShopRepository();
    private final UserRepository userRepository = repositoryFactory.getUserRepository();

    public void replenishBalance(User user, double amount) {
        user.setBalance(user.getBalance() + amount);
        userRepository.saveUser(user);
    }

    public Map<Integer, Shop> idToShopMap(User user) {
        List<Shop> shops = shopRepository.getShopsByUserLogin(user.getLogin());
        if (shops.isEmpty()) {
            return Collections.emptyMap();
        }
        return IntStream.range(1, shops.size() + 1)
                .boxed()
                .collect(Collectors.toMap(Function.identity(), i -> shops.get(i - 1)));
    }

    public void removeItem(Item item, Shop shop) {
        shop.getItems().remove(item.getName());
        shopRepository.saveNewShop(shop);
    }

    public void decreaseAmount(Item item, Shop shop, Integer amount) {
        item.setQuantity(item.getQuantity() - amount);
        shopRepository.saveNewShop(shop);
    }

    public void changeItemName(Item item, Shop shop, String newName) {
        shop.getItems().remove(item.getName());
        item.setName(newName);
        shop.addItem(item);
        shopRepository.saveNewShop(shop);
    }

    public void changePrice(Item item, Shop shop, double newPrice) {
        if (newPrice < 0) {
            throw new IllegalArgumentException("Price can not be negative");
        }
        item.setPrice(newPrice);
        shopRepository.saveNewShop(shop);
    }

    public void transferMoneyToShopWallet(Shop shop, User user, double amount) {
        shop.setBalance(shop.getBalance() + amount);
        user.setBalance(user.getBalance() - amount);
        shopRepository.saveNewShop(shop);
        userRepository.saveUser(user);
    }

    public void transferMoneyToPersonalWallet(User user, Shop shop, double amount) {
        shop.setBalance(shop.getBalance() - amount);
        user.setBalance(user.getBalance() + amount);
        shopRepository.saveNewShop(shop);
        userRepository.saveUser(user);
    }

    public void buyItemsFromWarehouse(Shop shop, String itemName, double itemPrice, int itemQuantity) {
        shop.setBalance(shop.getBalance() - itemPrice * itemQuantity);
        shop.addItem(new Item(itemName, itemPrice * PROFIT_PERCENTAGE, itemQuantity, shop.getType()));
        shopRepository.saveNewShop(shop);
    }

    public Shop createShop(ShopType shopType, String shopName, User user) {
        Shop shop = new Shop(shopName, shopType, user);
        shopRepository.saveNewShop(shop);
        return shop;
    }

}
