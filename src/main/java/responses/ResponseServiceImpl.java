package responses;

import services.LoginService;
import services.LoginServiceImpl;

import java.util.Arrays;

public class ResponseServiceImpl implements ResponseService {

    private LoginService loginService;

    public ResponseServiceImpl() {
        loginService = new LoginServiceImpl();
    }


    public Response getResponse(String request) {
        Command command = defineCommand(request);
        Response response;
        switch (command) {
            case HELP:
                response = new Response();
                response.setMessage(Constants.HELP_MSG);
            case START:
                response = new Response();
                response.setMessage(Constants.WELCOME_MSG);
            case LOGIN:
                response = getLoginResponse(request);
            default:
                response = new Response();
                response.setMessage(Constants.UNSUPPORTED_CMD_MSG);
        }

        return response;
    }

    private Command defineCommand(String request) {
        return Arrays.stream(Command.values())
                .filter(command -> command.getCommand().equals(request.trim().toLowerCase()))
                .findAny()
                .orElse(Command.UNDEFINED);
    }

    private Response getLoginResponse(String request) {
        request = request.trim();
        int lastSpaceIndex = request.lastIndexOf(" ");

        String password = request.trim().substring(lastSpaceIndex + 1);

        request = request.substring(0, lastSpaceIndex + 1).trim();
        lastSpaceIndex = request.lastIndexOf(" ");

        String login = request.substring(lastSpaceIndex + 1);

        boolean isLoggedIn = loginService.login(login, password);

        Response response = new Response();
        if (isLoggedIn) {
            response.setMessage(Constants.LOGIN_SUCCESS_MSG);
        } else {
            response.setMessage(Constants.LOGIN_FAILURE_MSG);
        }

        return response;
    }


}
