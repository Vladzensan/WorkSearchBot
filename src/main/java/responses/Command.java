package responses;

public enum Command {
    START("/start", "Start"),
    HELP("/help", "Help"),
    AUTH("/auth", "Authorization"),
    LOGIN("/login", "Login"),
    LOGOUT("/logout", "Logout"),
    PROFILE("/profile", "Profile"),
    SEARCH("/search", "Search vacancies"),
    CATALOGUES("/catalogues", "Catalogue"),
    SALARY("/salary", "Salary"),
    AGE("/age", "Age"),
    GENDER("/gender", "Gender"),
    FAVORITES("/favorites", "Favorite vacancies"),
    UNDEFINED("undefined", "Undefined command");

    private String command;
    private String caption;


    Command(String command, String caption) {
        this.command = command;
        this.caption = caption;
    }

    public String getCommand() {
        return command;
    }

    public String getCaption() {
        return caption;
    }

}
