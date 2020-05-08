package responses;

public class Constants {

    public static final String WELCOME_MSG = "Welcome to Job Search Telegram bot. Type /help to get available commands\n";

    public static final String HELP_MSG = "Type: \n" +
            "/login {login} {password} to login. Authorization is necessary to use our bot\n" +
            "/find to search for jobs\n" +
            "/about to see some info about the bot";

    public static final String UNSUPPORTED_CMD_MSG = "Sorry, but i'm unable to process such command :( \n" +
            "But It'll be added soon ;)";

    public static final String LOGIN_SUCCESS_MSG = "You've successfully logged in!  Now You may start using our services.";
    public static final String LOGIN_FAILURE_MSG = "Login failed. Check your login/password and try again";
}
