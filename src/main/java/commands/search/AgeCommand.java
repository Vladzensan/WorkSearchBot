package commands.search;

import commands.Command;
import responses.Response;
import user.User;

import java.util.ResourceBundle;

public class AgeCommand extends Command {
    public AgeCommand(String s, User user) {
        super(s, user);
    }

    @Override
    public Response execute() {
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

        response = new Response();

        response.setMessage(constants.getString("filter_age_helper"));

        return response;
    }
}
