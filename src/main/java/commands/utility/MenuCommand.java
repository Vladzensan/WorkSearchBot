package commands.utility;

import commands.Command;
import commands.CommandEnum;
import commands.Utilities;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import responses.Response;
import user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuCommand extends Command {
    private static final List<CommandEnum> menuCommandEnums = new ArrayList<>(Arrays.asList(CommandEnum.PROFILE, CommandEnum.AUTH,
            CommandEnum.SEARCH, CommandEnum.FAVORITES, CommandEnum.LANGUAGE));


    public MenuCommand(String s, User user) {
        super(s, user);
    }


    @Override
    public Response execute() {

        final List<CommandEnum> menuCommandEnums = new ArrayList<>(Arrays.asList(CommandEnum.PROFILE, CommandEnum.AUTH,
                CommandEnum.SEARCH, CommandEnum.FAVORITES, CommandEnum.LANGUAGE));
        response = new Response();
        response.setMessage("WorkSearch bot menu:");

        response.setMarkup(new InlineKeyboardMarkup(Utilities.mapButtonsByTwo(menuCommandEnums, user.getCurrentLocale())));
        return response;
    }

}
