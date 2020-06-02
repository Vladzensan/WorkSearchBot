package commands.utility;

import commands.Command;
import responses.Response;
import user.User;

public class PrevPageCommand extends Command {
    public PrevPageCommand(String s, User user) {
        super(s, user);
    }

    @Override
    public Response execute() {
        return null;//TODO
    }
}
