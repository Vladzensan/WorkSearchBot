package commands.search;

import commands.Command;
import commands.CommandEnum;
import responses.Response;
import user.User;

import java.util.ResourceBundle;

public class ExperienceCommand extends Command {
    public ExperienceCommand(String s, User user) {
        super(s, user);
    }

    @Override
    public Response execute() {
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

        response = new Response();

        response.setMessage(constants.getString("filter_experience_helper"));
        user.setState(CommandEnum.EXPERIENCE);

        return response;
    }
}
