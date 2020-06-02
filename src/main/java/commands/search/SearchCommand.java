package commands.search;

import commands.Command;
import commands.CommandEnum;
import commands.Utilities;
import filters.UserFiltersDao;
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
                CommandEnum.SALARYFROM, CommandEnum.SALARYTO, CommandEnum.AGE, CommandEnum.PLACEOFWORK, CommandEnum.FIND));

        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

        user.setState(CommandEnum.SEARCH);

        response = new Response();

        response.setMessage(constants.getString("filter_header"));

        response.getMarkup().setKeyboard(Utilities.mapButtonsByTwo(filterCommandEnums, user.getCurrentLocale()));

        UserFiltersDao.getInstance().clearFilters(user.getChatId());//clear filters to start new search history

        return response;
    }

}
