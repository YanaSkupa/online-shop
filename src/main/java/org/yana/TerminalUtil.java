package org.yana;

import org.yana.persistance.Shop;
import org.yana.persistance.ShopType;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public final class TerminalUtil {

    private final Scanner scanner;

    public TerminalUtil(Scanner scanner) {
        this.scanner = scanner;
    }

    public int askUserForNumberInput(String[] menuOptions, int maxValue) {
        printMenu(menuOptions);
        int value = getIntInput();
        while (value < 1 || value > maxValue) {
            System.out.println("Invalid menu item, please try again");
            value = getIntInput();
        }
        return value;
    }

    public void printShopList(Map<Integer, Shop> idToShop){
        for (Map.Entry<Integer, Shop> shopEntry : idToShop.entrySet()) {
            System.out.println(shopEntry.getKey() + "- " + shopEntry.getValue().getName());
        }
    }

    public void printMenu(String[] options) {
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Choose your option: ");
    }

    public void printShopTypes() {
        for (ShopType option : ShopType.values()) {
            System.out.printf("%d- %s%n", option.getId(), option.name());
        }
    }

    public int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (InputMismatchException ex) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
    }

    public String getStringInput() {
        while (true) {
            try {
                return scanner.nextLine();
            } catch (Exception ex) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public double getDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
