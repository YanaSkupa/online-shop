package org.yana.menu;

import org.yana.TerminalUtil;
import org.yana.persistance.Item;
import org.yana.persistance.Shop;
import org.yana.persistance.ShopType;
import org.yana.persistance.User;
import org.yana.service.SellerService;
import org.yana.service.ServiceFactory;

import java.util.ArrayList;
import java.util.Map;

import static org.yana.MenuConstants.*;

public class SellerMenu implements Menu {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final SellerService sellerService = serviceFactory.getSellerService();
    private final TerminalUtil terminalUtil;

    public SellerMenu(TerminalUtil terminalUtil) {
        this.terminalUtil = terminalUtil;
    }

    public void mainMenu(User user) {
        boolean back = false;
        while (!back) {
            terminalUtil.printMenu(SELLER_MENU);
            int option = terminalUtil.getIntInput();
            switch (option) {
                case 1:
                    createShop(user);
                    break;
                case 2:
                    shopList(user);
                    break;
                case 3:
                    replenishBalance(user);
                    break;
                case 4:
                    System.out.printf("Your balance is: %.2f$\n", user.getBalance());
                    break;
                case 5:
                    back = true;
                    break;
            }
        }
    }

    private void createShop(User user) {
        System.out.println("Enter shop name: ");
        String shopName = terminalUtil.getStringInput();
        System.out.println("Choose shop type: ");
        terminalUtil.printShopTypes();
        int shopID = terminalUtil.getIntInput();
        sellerService.createShop(ShopType.getById(shopID), shopName, user);
    }

    private void shopList(User user) {
        boolean back = false;
        while (!back) {
            Map<Integer, Shop> idToShop = sellerService.idToShopMap(user);
            if (idToShop.isEmpty()) {
                System.out.println("You don't have any shops");
                return;
            }
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

    private void replenishBalance(User user) {
        boolean back = false;
        while (!back) {
            System.out.println("Enter amount: ");
            double amount = terminalUtil.getDoubleInput();
            if (amount < 0) {
                System.out.println("Amount can not be negative");
            } else {
                sellerService.replenishBalance(user, amount);
                back = true;
            }
        }
    }

    private void shopMenu(Shop shop, User user) {
        boolean back = false;
        while (!back) {
            terminalUtil.printMenu(SHOP_MENU);
            int option = terminalUtil.getIntInput();
            switch (option) {
                case 1:
                    buyItemsFromWarehouse(shop);
                    break;
                case 2:
                    transferMoneyToPersonalWallet(user, shop);
                    break;
                case 3:
                    transferMoneyToShopWallet(shop, user);
                    break;
                case 4:
                    itemList(shop);
                    break;
                case 5:
                    System.out.printf("Shop balance is: %.2f$\n", shop.getBalance());
                    break;
                case 6:
                    back = true;
                    break;
            }
        }
    }

    private void buyItemsFromWarehouse(Shop shop) {
        boolean back = false;
        while (!back) {
            System.out.println("Enter item name: ");
            String itemName = terminalUtil.getStringInput();
            System.out.println("Enter item price: ");
            double itemPrice = terminalUtil.getDoubleInput();
            System.out.println("Enter item quantity: ");
            int itemQuantity = terminalUtil.getIntInput();
            if (itemPrice < 0 || itemQuantity < 0) {
                System.out.println("Price and quantity can not be negative");
            } else {
                back = true;
            }
            if (itemPrice * itemQuantity > shop.getBalance()) {
                System.out.println("Not enough money in the shop wallet");
                back = false;
            } else {
                sellerService.buyItemsFromWarehouse(shop, itemName, itemPrice, itemQuantity);
            }
        }
    }

    private void transferMoneyToPersonalWallet(User user, Shop shop) {
        boolean back = false;
        while (!back) {
            System.out.println("Enter amount: ");
            double amount = terminalUtil.getDoubleInput();
            if (amount < 0) {
                System.out.println("Amount can not be negative");
            } else {
                back = true;
            }
            if (amount > shop.getBalance()) {
                System.out.println("Not enough money in the shop wallet");
                back = false;
            } else {
                sellerService.transferMoneyToPersonalWallet(user, shop, amount);
            }
        }
    }

    private void transferMoneyToShopWallet(Shop shop, User user) {
        boolean back = false;
        while (!back) {
            System.out.println("Enter amount: ");
            double amount = terminalUtil.getDoubleInput();
            if (amount < 0) {
                System.out.println("Amount can not be negative");
            } else {
                back = true;
            }
            if (amount > user.getBalance()) {
                System.out.println("Not enough money in the user wallet");
                back = false;
            } else {
                sellerService.transferMoneyToShopWallet(shop, user, amount);
            }
        }
    }

    private void itemList(Shop shop) {
        boolean back = false;
        while (!back) {
            Map<Integer, Item> idToItem = idToItemMap(new ArrayList<>(shop.getItems().values()));
            if (idToItem.isEmpty()) {
                System.out.println("Shop is empty");
                break;
            }
            for (Map.Entry<Integer, Item> itemEntry : idToItem.entrySet()) {
                System.out.println(itemEntry.getKey() + "- " + itemEntry.getValue().getName() + " - " + itemEntry.getValue().getPrice() + "$ - " + itemEntry.getValue().getQuantity() + " pcs");
            }
            int option = terminalUtil.getIntInput();
            if (option < 1 || option > idToItem.size()) {
                System.out.println("Invalid option, please try again");
            } else {
                itemMenu(idToItem.get(option), shop);
                back = true;
            }
        }
    }

    private void itemMenu(Item item, Shop shop) {
        terminalUtil.printMenu(ITEM_MENU);
        int option = terminalUtil.getIntInput();
        switch (option) {
            case 1:
                changePriceInput(item, shop);
                break;
            case 2:
                changeName(item, shop);
                break;
            case 3:
                decreaseAmount(item, shop);
                break;
            case 4:
                sellerService.removeItem(item, shop);
                break;
            case 5:
                break;
        }
    }


    private void changePriceInput(Item item, Shop shop) {
        boolean back = false;
        while (!back) {
            System.out.println("Enter new price: ");
            double newPrice = terminalUtil.getDoubleInput();
            if (newPrice < 0) {
                System.out.println("Price can not be negative");
            } else {
                back = true;
                sellerService.changePrice(item, shop, newPrice);
            }
        }
    }

    private void changeName(Item item, Shop shop) {
        System.out.println("Enter new name: ");
        String newName = terminalUtil.getStringInput();
        sellerService.changeItemName(item, shop, newName);
    }


    private void decreaseAmount(Item item, Shop shop) {
        boolean back = false;
        while (!back) {
            System.out.println("Enter amount: ");
            int amount = terminalUtil.getIntInput();
            if (amount < 0) {
                System.out.println("Amount can not be negative");
            } else {
                back = true;
            }
            if (amount > item.getQuantity()) {
                System.out.println("Not enough items in the shop");
                back = false;
            } else {
                sellerService.decreaseAmount(item, shop, amount);
            }
        }
    }
}
