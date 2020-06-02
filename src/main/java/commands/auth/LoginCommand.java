package commands.auth;

import authorization.AuthService;
import authorization.AuthServiceImpl;
import commands.Command;
import responses.Response;
import user.User;

import java.util.ResourceBundle;

public class LoginCommand extends Command {
    public LoginCommand(String s, User user) {
        super(s, user);
    }

    @Override
    public Response execute() {
        response = new Response();
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

        AuthService loginService = AuthServiceImpl.getInstance();

        String[] tokens = s.split("\\s+");
        if (tokens.length == 1) {
            response.setMessage(constants.getString("login_info"));
            return response;
        }

        boolean isLoggedIn = loginService.login(tokens[1], tokens[2], user.getChatId());
        if (isLoggedIn) {
            response.setMessage(constants.getString("login_success"));
        } else {
            response.setMessage(constants.getString("login_failure"));
        }

        return response;
    }
}
