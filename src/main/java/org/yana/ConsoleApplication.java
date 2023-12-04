package org.yana;

import org.yana.persistance.Role;
import org.yana.persistance.User;
import org.yana.service.RegistrationService;
import org.yana.service.ServiceFactory;

import java.util.Scanner;

import static org.yana.MenuConstants.*;

public class ConsoleApplication {

    private static ServiceFactory services = new ServiceFactory();

    private RegistrationService registrationService = (RegistrationService) services.getService("registrationService");

    public void run() {
        printMenu(LOGIN_OPTIONS);
        Scanner scanner = new Scanner(System.in);
        try {
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    signIn();
                    break;
                case 2:
                    signUP();
                    break;
                case 3:
                    exit(0);
                default:
                    run();
            }
        } catch (Exception ex) {
            System.out.println("Please enter an integer value between 1 and 3");
            run();
        }
    }

    private void signIn() {
        System.out.println("SIGN IN");
    }

    private void signUP() {
        System.out.println("Which type of account do you want to create?");
        printMenu(SIGN_UP_OPTIONS);
        Scanner scanner = new Scanner(System.in);
        try {
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    regNewUser(Role.SELLER);
                    break;
                case 2:
                    regNewUser(Role.BUYER);
                    break;
                default:
                    run();
            }
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println(ex.getMessage());
            System.out.println("Please enter an integer value between 1 and 3");
            signUP();
        }
    }

    private void regNewUser(Role role) {
        System.out.println("ROLE" + role);
        System.out.println("Login: ");
        Scanner scanner = new Scanner(System.in);
        String login = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();
        User user = registrationService.register(login, password, role);
        System.out.println(user.getLogin() + " " + user.getPassword() + " " + user.getRole());
//        System.out.println("Hello, " + user.getLogin());
        switch (role) {
            case BUYER:
                buyerMenu();
                break;
            case SELLER:
                sellerMenu();
                break;
        }
    }

    private void sellerMenu() {
        printMenu(SELLER_MENU);
        Scanner scanner = new Scanner(System.in);
        try {
            int option = scanner.nextInt();
            switch (option) {
                default:
                    run();
            }
        } catch (Exception ex) {
            System.out.println("Please enter an integer value between 1 and 3");
            signUP();
        }
    }

    private void buyerMenu() {
        printMenu(BUYER_MENU);
        Scanner scanner = new Scanner(System.in);
        try {
            int option = scanner.nextInt();
            switch (option) {
                default:
                    run();
            }
        } catch (Exception ex) {
            System.out.println("Please enter an integer value between 1 and 3");
            signUP();
        }
    }

    private void exit(int status) {
        System.out.println("EXIT");
        System.exit(0);
    }

    private void printMenu(String[] options) {
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Choose your option : ");
    }
}
