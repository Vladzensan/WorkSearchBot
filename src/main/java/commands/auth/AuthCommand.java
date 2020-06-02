package commands.auth;

import commands.Command;
import commands.CommandEnum;
import commands.Utilities;
import responses.Response;
import user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AuthCommand extends Command {
    public AuthCommand(String s, User user) {
        super(s, user);
    }

    @Override
    public Response execute() {
        final List<CommandEnum> menuCommandEnums = new ArrayList<>(Arrays.asList(CommandEnum.LOGIN, CommandEnum.LOGOUT));

        response = new Response();
        response.setMessage(CommandEnum.AUTH.getCaption(user.getCurrentLocale()));
        response.getMarkup().setKeyboard(Utilities.mapButtonsByTwo(menuCommandEnums, user.getCurrentLocale()));

        return response;
    }
}
