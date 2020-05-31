package commands;

import locale.LocaleService;
import locale.LocaleServiceImpl;

import java.util.Locale;

public enum CommandEnum {
    MENU("/menu", "menu_item"),
    HELP("/help", "help_item"),
    AUTH("/auth", "auth_item"),
    LOGIN("/login", "login_item"),
    LOGOUT("/logout", "logout_item"),
    PROFILE("/profile", "profile_item"),
    PROFILE_INFO("/profileinfo", "profile_info_item"),
    RESUMES("/resumes","resumes_item"),
    CREATE_RESUME("/createresume","create_resume_item"),
    SEARCH("/search", "search_item"),
    FIND("/find", "find_item"),
    FAVORITES("/favorites", "favorites_item"),
    CATALOGUES("/catalogues", "catalogues_item"),
    SALARYFROM("/salaryfrom", "salary_from_item"),
    SALARYTO("salaryto", "salary_to_item"),
    AGE("/age", "age_item"),
    EXPERIENCE("/experience", "experience_item"),
    PLACEOFWORK("/placeofwork", "place_of_work_item"),
    LANGUAGE("/lang", "language_item"),
    PREVIOUSPAGE("/previouspage", "previous_page_item"),
    NEXTPAGE("/nextpage", "next_page_item"),
    OTHER("undefined", "undefined_item");


    private static LocaleService localeService = LocaleServiceImpl.getInstance();
    private String command;
    private String name;


    CommandEnum(String command, String name) {
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
