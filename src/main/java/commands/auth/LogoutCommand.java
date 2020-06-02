package commands.auth;

import authorization.AuthService;
import authorization.AuthServiceImpl;
import commands.Command;
import responses.Response;
import user.User;

import java.util.ResourceBundle;

public class LogoutCommand extends Command {
    public LogoutCommand(String s, User user) {
        super(s, user);
    }

    @Override
    public Response execute() {
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());
        AuthService authService = AuthServiceImpl.getInstance();
        boolean isSuccessful = authService.logout(user.getChatId());
        response = new Response();

        response.setMessage(isSuccessful
                ? constants.getString("logout_success_msg")
                : constants.getString("logout_failure_msg"));
        return response;
    }
}
