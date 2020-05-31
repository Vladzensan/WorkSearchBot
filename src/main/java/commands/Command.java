package commands;

import locale.LocaleService;
import locale.LocaleServiceImpl;
import responses.Response;
import user.User;

public abstract class Command implements CommandCreator {

    protected static LocaleService localeService = LocaleServiceImpl.getInstance();
    protected static CommandEnum commandName;
    protected Response response;
    protected String s;
    protected User user;
    public Command(String s, User user) {
        this.s = s;
        this.user = user;
    }

    public CommandEnum getCommandName() {
        return commandName;
    }

    public void setCommandName(CommandEnum commandName) {
        this.commandName = commandName;
    }

    public abstract Response execute();

}


