package commands.search;

import commands.Command;
import responses.Response;
import user.User;

public class SalaryCommand extends Command {
    public SalaryCommand(String s, User user) {
        super(s, user);
    }

    @Override
    public Response execute() {
        return null;//TODO
    }
}
