package commands.search;

import commands.Command;
import commands.CommandEnum;
import commands.Utilities;
import filters.Filter;
import filters.FiltersDao;
import filters.UserFiltersDao;
import responses.Response;
import user.User;

import java.util.*;

public class OtherCommand extends Command {
    public OtherCommand(String s, User user) {
        super(s, user);
    }

    @Override
    public Response execute() {
        final List<CommandEnum> filterCommandEnums = new ArrayList<>(Arrays.asList(CommandEnum.PLACEOFWORK, CommandEnum.EXPERIENCE,
                CommandEnum.SALARYFROM, CommandEnum.SALARYTO, CommandEnum.AGE, CommandEnum.CATALOGUES, CommandEnum.FIND));
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());
        Response response = new Response();

        if (!filterCommandEnums.contains(user.getState())) {
            response.setMessage(constants.getString("unsupported_cmd"));
        } else {
            response = setFilter(s, user);
        }

        return response;
    }

    private Response setFilter(String s, User user) {
        final List<CommandEnum> filterCommandEnums = new ArrayList<>(Arrays.asList(CommandEnum.CATALOGUES, CommandEnum.EXPERIENCE,
                CommandEnum.SALARYFROM, CommandEnum.SALARYTO, CommandEnum.AGE, CommandEnum.PLACEOFWORK, CommandEnum.FIND));

        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());
        Map<CommandEnum, Filter> commandsToFilters = new HashMap<>();
        commandsToFilters.put(CommandEnum.CATALOGUES, Filter.CATALOGUES);
        commandsToFilters.put(CommandEnum.PLACEOFWORK, Filter.PLACE_OF_WORK);
        commandsToFilters.put(CommandEnum.AGE, Filter.AGE);
        commandsToFilters.put(CommandEnum.EXPERIENCE, Filter.EXPERIENCE);
        commandsToFilters.put(CommandEnum.SALARYFROM, Filter.SALARY_FROM);
        commandsToFilters.put(CommandEnum.SALARYTO, Filter.SALARY_TO);

        response = new Response();

        FiltersDao filtersDao = UserFiltersDao.getInstance();
        filtersDao.addFilter(user.getChatId(), commandsToFilters.get(user.getState()), s);

        response.setMessage(constants.getString("filter_header"));

        response.getMarkup().setKeyboard(Utilities.mapButtonsByTwo(filterCommandEnums, user.getCurrentLocale()));

        user.setState(CommandEnum.SEARCH);

        return response;
    }
}
