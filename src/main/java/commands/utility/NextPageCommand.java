package commands.utility;

import commands.Command;
import responses.Response;
import user.User;

public class NextPageCommand extends Command {
    public NextPageCommand(String s, User user) {
        super(s, user);
    }

    @Override
    public Response execute() {
        return null;//TODO
    }
}
