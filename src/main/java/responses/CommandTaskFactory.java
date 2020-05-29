package responses;

import authorization.LoginService;
import authorization.LoginServiceImpl;
import locale.LocaleServiceImpl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class CommandTaskFactory {

    private static Map<Command, CommandTask> instance;

    private CommandTaskFactory() {
    }

    public static CommandTask getTask(Command command) {
        if (instance == null) {
            initializeTasks();
        }

        return instance.get(command);
    }

    private static void initializeTasks() {
        instance = new HashMap<>();

        instance.put(Command.HELP, CommandTaskFactory::helpHandler);
        instance.put(Command.LOGIN, CommandTaskFactory::loginHandler);

    }

    private static Response helpHandler(String query, long chatId) {
        Response response = new Response();

        response.setMessage("Help info");

        return response;
    }


    private static Response loginHandler(String query, long chatId) {
        LoginService loginService = LoginServiceImpl.getInstance();

        String[] tokens = query.split("\\s+");
        boolean isLoggedIn = loginService.login(tokens[1], tokens[2], chatId);

        Response response = new Response();
        ResourceBundle resourceBundle = LocaleServiceImpl.getInstance().getLocaleBundle(new Locale("en"));
        if (isLoggedIn) {
            response.setMessage(resourceBundle.getString("login_success_msg"));
        } else {
            response.setMessage(resourceBundle.getString("login_failure_msg"));
        }

        return response;
    }
}
