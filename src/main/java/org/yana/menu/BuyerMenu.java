package org.yana.menu;

import org.yana.TerminalUtil;
import org.yana.persistance.Item;
import org.yana.persistance.Shop;
import org.yana.persistance.User;
import org.yana.service.BuyerService;
import org.yana.service.ServiceFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.yana.MenuConstants.BUYER_MENU;
import static org.yana.MenuConstants.SHOP_MENU_SELLER;

public class BuyerMenu implements Menu {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final BuyerService buyerService = serviceFactory.getBuyerService();
    private final TerminalUtil terminalUtil;

    public BuyerMenu(TerminalUtil terminalUtil) {
        this.terminalUtil = terminalUtil;
    }

    public void buyerMenu(User user) {
        boolean back = false;
        while (!back) {
            terminalUtil.printMenu(BUYER_MENU);
            int option = terminalUtil.getIntInput();
            switch (option) {
                case 1:
                    replenishBalance(user);
                    break;
                case 2:
                    System.out.printf("Your balance is: %.2f$\n", user.getBalance());
                    break;
                case 3:
                    shopList(user);
                    break;
                case 4:
                    itemsList(user);
                    break;
                case 5:
                    back = true;
                    break;
            }
        }
    }

    private void shopList(User user) {
        boolean back = false;
        while (!back) {
            Map<Integer, Shop> idToShop = buyerService.getAllShopsWithID();
            terminalUtil.printShopList(idToShop);
            int option = terminalUtil.getIntInput();
            if (option < 1 || option > idToShop.size()) {
                System.out.println("Invalid option, please try again");
            } else {
                shopMenu(idToShop.get(option), user);
                back = true;
            }
        }
    }

    private void shopMenu(Shop shop, User user) {
        boolean back = false;
        while (!back) {
            terminalUtil.printMenu(SHOP_MENU_SELLER);
            int option = terminalUtil.getIntInput();
            switch (option) {
                case 1:
                    buyItems(shop, user);
                    break;
                case 2:
                    searchItems(shop, user);
                    break;
                case 3:
                    back = true;
                    break;
            }
        }
    }

    private void searchItems(Shop shop, User user) {
        System.out.println("Enter item name or key word to search: ");
        String searchKeyWord = terminalUtil.getStringInput();
        List<Item> items = buyerService.searchItems(shop, searchKeyWord);
        if (items.isEmpty()) {
            System.out.println("No items found");
        } else {
            buyItems(idToItemMap(items), shop, user);
        }
    }

    private void buyItems(Shop shop, User user) {
        Map<Integer, Item> idItemMap = idToItemMap(new ArrayList<>(shop.getItems().values()));
        buyItems(idItemMap, shop, user);
    }

    private void buyItems(Map<Integer, Item> idItemMap, Shop shop, User user) {
        System.out.println("ID - Name - Price");
        for (Map.Entry<Integer, Item> itemEntry : idItemMap.entrySet()) {
            System.out.printf("%d - %s - %.2f\n",itemEntry.getKey(), itemEntry.getValue().getName(),itemEntry.getValue().getPrice());
        }
        System.out.println("Enter item ID: ");
        int id = terminalUtil.getIntInput();
        System.out.println("Enter quantity: ");
        int quantity = terminalUtil.getIntInput();
        boolean isBought = buyerService.buyItems(shop, user, idItemMap.get(id), quantity);
        if (isBought) {
            System.out.println("Items bought successfully");
        } else {
            System.out.println("Not enough money or not enough items in stock");
        }
    }

    private void itemsList(User user) {
        user.getCart()
                .forEach((itemName, item) ->
                        System.out.printf("%s - %d - %.2f - Delivery time: %s days\n",
                                itemName,
                                item.getQuantity(),
                                item.getPrice(),
                                item.getShopType().getDeliveryTimeDays()
                        )
                );
    }

    private void replenishBalance(User user) {
        boolean back = false;
        while (!back) {
            System.out.println("Enter amount: ");
            double amount = terminalUtil.getDoubleInput();
            if (amount < 0) {
                System.out.println("Amount can not be negative");
            } else {
                back = true;
                buyerService.replenishBalance(user, amount);
            }
        }
    }
}
