package responses;

import authorization.LoginService;
import authorization.LoginServiceImpl;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

public class ResponseServiceImpl implements ResponseService {

    private LoginService loginService = LoginServiceImpl.getInstance();


    public Response getResponse(Update update) {
        String request = update.getMessage().getText();
        String[] words = request.split("\\s+");

        Command command = defineCommand(words[0]);
        Response response;
        switch (command) {
            case HELP:
                response = new Response();
                response.setMessage(Constants.HELP_MSG);
                break;
            case START:
                response = new Response();
                response.setMessage(Constants.WELCOME_MSG);
                break;
            case LOGIN:
                response = getLoginResponse(request, update.getMessage().getChatId());
                break;
            default:
                response = new Response();
                response.setMessage(Constants.UNSUPPORTED_CMD_MSG);
                break;
        }

        return response;
    }

    private Command defineCommand(String request) {
        return Arrays.stream(Command.values())
                .filter(command -> command.getCommand().equals(request.trim().toLowerCase()))
                .findAny()
                .orElse(Command.UNDEFINED);
    }

    private Response getLoginResponse(String request, long chatId) {
        String[] tokens = request.split("\\s+");
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
