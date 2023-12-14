package org.yana.service;

import org.yana.persistance.Item;
import org.yana.persistance.Shop;
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

public class BuyerService implements Service {
    private final RepositoryFactory repositoryFactory = RepositoryFactory.getInstance();
    private final ShopRepository shopRepository = repositoryFactory.getShopRepository();
    private final UserRepository userRepository = repositoryFactory.getUserRepository();

    public void replenishBalance(User user, double amount){
        user.setBalance(user.getBalance() + amount);
        userRepository.saveUser(user);
    }

    public Map<Integer, Shop> getAllShopsWithID() {
        List<Shop> shops = shopRepository.getAllShops();
        if (shops.isEmpty()) {
            return Collections.emptyMap();
        }
        return IntStream.range(1, shops.size() + 1)
                .boxed()
                .collect(Collectors.toMap(Function.identity(), i -> shops.get(i - 1)));
    }

    public boolean buyItems(Shop shop, User user, Item item, int quantity) {
        double productPrice = item.getPrice() * quantity;
        if (user.getBalance() < productPrice) {
            return false;
        }
        if (shop.getItems().get(item.getName()).getQuantity() < quantity) {
            return false;
        }
        user.setBalance(user.getBalance() - productPrice);
        shop.setBalance(shop.getBalance() + productPrice);
        shop.getItems()
                .get(item.getName())
                .setQuantity(shop.getItems().get(item.getName()).getQuantity() - quantity);

        user.addToCart(new Item(item.getName(), item.getPrice(), quantity, shop.getType()));
        userRepository.saveUser(user);
        shopRepository.saveNewShop(shop);
        return true;
    }

    public List<Item> searchItems(Shop shop, String searchKeyWord) {
        return shop.getItems().values().stream()
                .filter(i -> i.getName().toLowerCase().contains(searchKeyWord.toLowerCase()))
                .collect(Collectors.toList());
    }
}
