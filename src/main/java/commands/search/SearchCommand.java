package commands.search;

import commands.Command;
import commands.CommandEnum;
import commands.Utilities;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import responses.Response;
import user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class SearchCommand extends Command {
    public SearchCommand(String s, User user) {
        super(s, user);
    }

    @Override
    public Response execute() {
        final List<CommandEnum> filterCommandEnums = new ArrayList<>(Arrays.asList(CommandEnum.CATALOGUES, CommandEnum.EXPERIENCE,
                CommandEnum.SALARYFROM, CommandEnum.SALARYTO, CommandEnum.AGE, CommandEnum.PLACEOFWORK, CommandEnum.FIND, CommandEnum.CLEARFILTERS));

        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

        user.setState(CommandEnum.SEARCH);

        response = new Response();

        response.setMessage(constants.getString("filter_header"));

        response.setMarkup(new InlineKeyboardMarkup(Utilities.mapButtonsByTwo(filterCommandEnums, user.getCurrentLocale())));

        return response;
    }

}
