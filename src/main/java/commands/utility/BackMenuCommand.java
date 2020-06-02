package commands.utility;

import commands.Command;
import responses.Response;
import user.User;

public class BackMenuCommand extends Command {
    public BackMenuCommand(String s, User user) {
        super(s, user);
    }

    @Override
    public Response execute() {
        Response response = new MenuCommand(s,user).execute();
        response.setEditMessageId(user.getCurrentUpdate().getCallbackQuery().getMessage().getMessageId());
        return response;
    }
}
