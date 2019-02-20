package main.java.software.jevera.configuration;

import main.java.software.jevera.service.CardService;
import main.java.software.jevera.service.UserService;

public class AppFactory {

    public static final UserService userService;
    public static final CardService cardService;

    static{
        userService = new UserService(new User);
    }
}
