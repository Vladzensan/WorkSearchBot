package responses;

import authorization.LoginService;
import authorization.LoginServiceImpl;

import java.util.HashMap;
import java.util.Map;

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
        if (isLoggedIn) {
            response.setMessage(Constants.LOGIN_SUCCESS_MSG);
        } else {
            response.setMessage(Constants.LOGIN_FAILURE_MSG);
        }

        return response;
    }

}
