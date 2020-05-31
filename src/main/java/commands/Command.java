package commands;

import locale.LocaleService;
import locale.LocaleServiceImpl;
import responses.Response;
import user.User;

public abstract class Command implements CommandCreator {

    public Command(String s, User user){
        this.s = s;
        this.user = user;
    }

    public void setCommandName(CommandEnum commandName){
        this.commandName = commandName;
    }

    public CommandEnum getCommandName(){
        return commandName;
    }

    protected static LocaleService localeService = LocaleServiceImpl.getInstance();
    protected static CommandEnum commandName;
    protected Response response;
    protected String s;
    protected User user;

    public abstract Response execute();

}


