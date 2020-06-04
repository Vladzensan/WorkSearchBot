package commands.search;

import commands.Command;
import commands.CommandEnum;
import commands.Utilities;
import filters.Filter;
import filters.FiltersDao;
import filters.UserFiltersDao;
import network.NetworkService;
import network.NetworkServiceImpl;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import responses.Response;
import user.User;
import vacancies.Vacancy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindCommand extends Command {
    public FindCommand(String s, User user) {
        super(s, user);
    }

    @Override
    public Response execute() {
        NetworkService networkService = new NetworkServiceImpl();
        FiltersDao filtersDao = UserFiltersDao.getInstance();
        filtersDao.addFilter(user.getChatId(), Filter.PAGE, "0");
        List<Vacancy> vacancies = networkService.getVacanciesList(filtersDao.getFilters(user.getChatId()));
        List<CommandEnum> navigationButtons = new ArrayList<>(Arrays.asList(CommandEnum.PREVIOUSPAGE,
                CommandEnum.NEXTPAGE));

        response = new Response();

        StringBuilder vacanciesString = new StringBuilder();

        if (vacancies != null && !vacancies.isEmpty()) {
            for (Vacancy vacancy : vacancies) {
                vacanciesString.append(vacancy.toString()).append("\n\n");
            }
            vacanciesString.append("Page - 0");
        } else {
            vacanciesString.append("Sorry, no vacancies found!");
        }

        response.setMarkup(new InlineKeyboardMarkup(Utilities.mapButtonsByTwo(navigationButtons, user.getCurrentLocale())));

        response.setMessage(vacanciesString.toString());

        user.setState(CommandEnum.FIND);

        return response;
    }
}
