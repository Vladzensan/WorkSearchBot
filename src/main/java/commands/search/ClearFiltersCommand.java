package commands.search;

import commands.Command;
import filters.UserFiltersDao;
import responses.Response;
import user.User;

import java.util.ResourceBundle;

public class ClearFiltersCommand extends Command {

    public ClearFiltersCommand(String s, User user) {
        super(s, user);
    }

    @Override
    public Response execute() {
        UserFiltersDao filtersDao = UserFiltersDao.getInstance();
        if (filtersDao != null) {
            filtersDao.clearFilters(user.getChatId());
        }

        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

        response = new Response();

        response.setMessage(constants.getString("filters_cleared_notify"));

        return response;
    }
}
