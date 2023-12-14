package org.yana;

import org.yana.menu.AuthMenu;
import org.yana.menu.MenuFactory;
import org.yana.persistance.*;
import org.yana.service.AuthService;
import org.yana.service.BuyerService;
import org.yana.service.SellerService;
import org.yana.service.ServiceFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.yana.MenuConstants.LOGIN_OPTIONS;

public class App {

    public static void main(String[] args) {
        final MenuFactory menuFactory = MenuFactory.getInstance();
        runTest();
        boolean isRunning = true;
        try (Scanner scanner = new Scanner(System.in)) {
            TerminalUtil terminalUtil = new TerminalUtil(scanner);
            AuthMenu authMenu = menuFactory.getAuthMenu(terminalUtil);
            while (isRunning) {
                final int mainMenuSelection = terminalUtil.askUserForNumberInput(LOGIN_OPTIONS, 3);
                switch (mainMenuSelection) {
                    case 1:
                        authMenu.signIN();
                        break;
                    case 2:
                        authMenu.signUP();
                        break;
                    case 3:
                        isRunning = false;
                }
            }
        } catch (Exception ex) {
            System.out.println("Please enter an integer value between 1 and 3");
        }
    }

    private static void runTest(){
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        SellerService sellerService = serviceFactory.getSellerService();
        BuyerService buyerService = serviceFactory.getBuyerService();
        AuthService authService = serviceFactory.getAuthService();

        User buyerStepan = authService.regNewUser("buyerStepan", "buyer", Role.BUYER, 0.0);
        User sellerStepan = authService.regNewUser("sellerStepan", "seller", Role.SELLER, 1000.0);
        User sellerIvan = authService.regNewUser("sellerIvan", "seller", Role.SELLER, 1000.0);

        buyerService.replenishBalance(buyerStepan, 10000.0);

        Shop silpo = sellerService.createShop(ShopType.FOOD, "Silpo", sellerStepan);
        Shop fora = sellerService.createShop(ShopType.FOOD, "Fora", sellerStepan);

        Shop novus = sellerService.createShop(ShopType.FOOD, "Novus", sellerIvan);
        Shop epicentr = sellerService.createShop(ShopType.BUILDING, "Epicentr", sellerIvan);

        sellerService.transferMoneyToShopWallet(silpo, sellerStepan, 500.0);
        sellerService.transferMoneyToShopWallet(fora, sellerStepan, 500.0);

        sellerService.transferMoneyToShopWallet(novus, sellerIvan, 500.0);
        sellerService.transferMoneyToShopWallet(epicentr, sellerIvan, 500.0);

        sellerService.buyItemsFromWarehouse(silpo, "Apple", 2, 100);
        sellerService.buyItemsFromWarehouse(silpo, "Peach", 1, 100);

        sellerService.buyItemsFromWarehouse(fora, "Beef", 20, 10);
        sellerService.buyItemsFromWarehouse(fora, "Pork", 20, 7);

        sellerService.buyItemsFromWarehouse(novus, "Cheese", 10, 8);
        sellerService.buyItemsFromWarehouse(novus, "Butter", 20, 5);

        sellerService.buyItemsFromWarehouse(epicentr, "Cement", 20, 20);
        sellerService.buyItemsFromWarehouse(epicentr, "Tile", 100, 4);

        buyerService.buyItems(silpo, buyerStepan, silpo.getItems().get("Apple"), 2);
        buyerService.buyItems(silpo, buyerStepan, silpo.getItems().get("Peach"), 5);
        buyerService.buyItems(epicentr, buyerStepan, epicentr.getItems().get("Cement"), 5);
        buyerService.buyItems(novus, buyerStepan, novus.getItems().get("Cheese"), 2);

        System.out.println("Silpo items:");
        printItems(new ArrayList<>(silpo.getItems().values()), false);
        System.out.println("Fora items:");
        printItems(new ArrayList<>(fora.getItems().values()), false);
        System.out.println("Novus items:");
        printItems(new ArrayList<>(novus.getItems().values()), false);
        System.out.println("Epicentr items:");
        printItems(new ArrayList<>(epicentr.getItems().values()),false);
        System.out.println();

        System.out.println("Stepan items: ");
        printItems(new ArrayList<>(buyerStepan.getCart().values()), true);

        sellerService.transferMoneyToPersonalWallet(sellerIvan, epicentr, 99.0);

        System.out.println("Stepan (buyer) balance: " + buyerStepan.getBalance());
        System.out.println("Ivan (seller) balance: " + sellerIvan.getBalance());

        sellerService.changeItemName(silpo.getItems().get("Apple"), silpo,  "Green Apple");
        sellerService.decreaseAmount(silpo.getItems().get("Green Apple"), silpo,  2);
    }

    private static void printItems(List<Item> items, boolean isBuyer){
        for(var item : items) {
            System.out.printf("%s - %.2f$ - %d pcs", item.getName(), item.getPrice(), item.getQuantity());
            if (isBuyer) {
                System.out.printf(" - Delivery time: %s days", item.getShopType().getDeliveryTimeDays());
            }
            System.out.println();
        }
    }
}