package commands.search;

import commands.Command;
import commands.CommandEnum;
import responses.Response;
import user.User;

import java.util.ResourceBundle;

public class SalaryFromCommand extends Command {

    public SalaryFromCommand(String s, User user) {
        super(s, user);
    }

    @Override
    public Response execute() {

        response = new Response();

        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

        response.setMessage(constants.getString("filter_salary_helper"));

        user.setState(CommandEnum.SALARYFROM);

        return response;
    }
}
