package org.yana.menu;

import org.yana.TerminalUtil;
import org.yana.persistance.Role;
import org.yana.persistance.User;
import org.yana.service.AuthService;
import org.yana.service.ServiceFactory;

import static org.yana.MenuConstants.SIGN_UP_OPTIONS;

public class AuthMenu implements Menu {
    private static final Double DEFAULT_SELLER_BALANCE = 1000.0;
    private static final Double DEFAULT_BUYER_BALANCE = 0.0;
    private final TerminalUtil terminalUtil;
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final AuthService authService = serviceFactory.getAuthService();
    private final SellerMenu sellerMenu;
    private final BuyerMenu buyerMenu;

    public AuthMenu(TerminalUtil terminalUtil) {
        this.terminalUtil = terminalUtil;
        MenuFactory menuFactory = MenuFactory.getInstance();
        this.sellerMenu = menuFactory.getSellerMenu(terminalUtil);
        this.buyerMenu = menuFactory.getBuyerMenu(terminalUtil);
    }

    public void signUP() {
        System.out.println("Which type of account do you want to create?");
        terminalUtil.printMenu(SIGN_UP_OPTIONS);
        int option = terminalUtil.getIntInput();
        switch (option) {
            case 1:
                regNewUser(Role.SELLER);
                break;
            case 2:
                regNewUser(Role.BUYER);
                break;
            case 3:
                break;
        }
    }

    public void signIN() {
        System.out.println("Login: ");
        String login = terminalUtil.getStringInput();
        System.out.println("Password: ");
        String password = terminalUtil.getStringInput();
        authService.checkAuthority(login, password)
                .ifPresentOrElse(user -> {
                            switch (user.getRole()) {
                                case BUYER:
                                    buyerMenu.buyerMenu(user);
                                    break;
                                case SELLER:
                                    sellerMenu.mainMenu(user);
                                    break;
                            }
                        },
                        () -> {
                            System.out.println("User with login " + login + " does not exist, or password is incorrect");
                            signIN();
                        });

    }

    private void regNewUser(Role role) {
        System.out.println("Login: ");
        String login = terminalUtil.getStringInput();
        System.out.println("Password: ");
        String password = terminalUtil.getStringInput();
        switch (role) {
            case BUYER:
                User buyer = authService.regNewUser(login, password, role, DEFAULT_BUYER_BALANCE);
                buyerMenu.buyerMenu(buyer);
                break;
            case SELLER:
                User seller = authService.regNewUser(login, password, role, DEFAULT_SELLER_BALANCE);
                sellerMenu.mainMenu(seller);
                break;
        }
    }
}
