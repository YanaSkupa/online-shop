package org.yana.menu;

import org.yana.TerminalUtil;

import java.util.HashMap;
import java.util.Map;

public class MenuFactory {

    public static MenuFactory INSTANCE;
    private final Map<String, Menu> menus = new HashMap<>();

    public static MenuFactory getInstance() {
        MenuFactory localInstance = INSTANCE;
        if (localInstance == null) {
            synchronized (MenuFactory.class) {
                localInstance = INSTANCE;
                if (localInstance == null) {
                    localInstance = INSTANCE = new MenuFactory();
                }
            }
        }
        return localInstance;
    }

    public SellerMenu getSellerMenu(TerminalUtil terminalUtil) {
        SellerMenu menu = (SellerMenu) menus.putIfAbsent("sellerMenu", new SellerMenu(terminalUtil));
        if (menu == null) {
            return (SellerMenu) menus.get("sellerMenu");
        }
        return menu;
    }

    public BuyerMenu getBuyerMenu(TerminalUtil terminalUtil) {
        BuyerMenu menu = (BuyerMenu) menus.putIfAbsent("buyerMenu", new BuyerMenu(terminalUtil));
        if (menu == null) {
            return (BuyerMenu) menus.get("buyerMenu");
        }
        return menu;
    }

    public AuthMenu getAuthMenu(TerminalUtil terminalUtil) {
        AuthMenu menu = (AuthMenu) menus.putIfAbsent("authMenu", new AuthMenu(terminalUtil));
        if (menu == null) {
            return (AuthMenu) menus.get("authMenu");
        }
        return menu;
    }

}
