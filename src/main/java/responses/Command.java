package responses;

import locale.LocaleService;
import locale.LocaleServiceImpl;

import java.util.Locale;

public enum Command {
    MENU("/menu", "menu_item"),
    HELP("/help", "help_item"),
    AUTH("/auth", "auth_item"),
    LOGIN("/login", "login_item"),
    LOGOUT("/logout", "logout_item"),
    PROFILE("/profile", "profile_item"),
    SEARCH("/search", "search_item"),
    FAVORITES("/favorites", "favorites_item"),
    CATALOGUES("/catalogues", "catalogue_item"),
    SALARY("/salary", "salary_item"),
    AGE("/age", "age_item"),
    GENDER("/gender", "gender_item"),
    LANGUAGE("/lang", "language_item"),
    UNDEFINED("undefined", "undefined_item");


    private static LocaleService localeService = LocaleServiceImpl.getInstance();
    private String command;
    private String name;


    Command(String command, String name) {
        this.command = command;
        this.name = name;
    }


    public String getCommand() {
        return command;
    }

    public String getCaption(Locale locale) {
        return localeService.getMenuItemBundle(locale).getString(this.name);
    }

}
