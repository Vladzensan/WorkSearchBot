package commands.search;

import commands.Command;
import commands.CommandEnum;
import commands.Utilities;
import filters.Filter;
import filters.FiltersDao;
import filters.UserFiltersDao;
import network.NetworkService;
import network.NetworkServiceImpl;
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
        filtersDao.addFilter(user.getChatId(), Filter.PAGE, "1");
        List<Vacancy> vacancies = networkService.getVacanciesList(filtersDao.getFilters(user.getChatId()));
        List<CommandEnum> navigationButtons = new ArrayList<>(Arrays.asList(CommandEnum.PREVIOUSPAGE,
                CommandEnum.NEXTPAGE));

        response = new Response();

        StringBuilder vacanciesString = new StringBuilder();

        if (vacancies != null) {
            for (Vacancy vacancy : vacancies) {
                vacanciesString.append(vacancy.getId());
                vacanciesString.append(" ");
                vacanciesString.append(vacancy.getProfession() + "\n");
                vacanciesString.append(vacancy.getPublicationDate() + "\n");
                vacanciesString.append(vacancy.getTown() + "\n\n");
                vacanciesString.append("page - 1");
                response.setMessage(vacanciesString.toString());
            }
        } else {
            vacanciesString.append("Sorry, no vacancies found!");
        }

        response.getMarkup().setKeyboard(Utilities.mapButtonsByTwo(navigationButtons, user.getCurrentLocale()));

        response.setMessage(vacanciesString.toString());

        user.setState(CommandEnum.FIND);

        return response;
    }
}
