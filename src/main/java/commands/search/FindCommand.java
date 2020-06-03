package commands.search;

import commands.Command;
import filters.FiltersDao;
import filters.UserFiltersDao;
import network.NetworkService;
import network.NetworkServiceImpl;
import responses.Response;
import user.User;
import vacancies.Vacancy;

import java.util.List;

public class FindCommand extends Command {
    public FindCommand(String s, User user) {
        super(s, user);
    }

    @Override
    public Response execute() {
        NetworkService networkService = new NetworkServiceImpl();
        FiltersDao filtersDao = UserFiltersDao.getInstance();
        List<Vacancy> vacancies = networkService.getVacanciesList(filtersDao.getFilters(user.getChatId()));

        response = new Response();

        StringBuilder vacanciesString = new StringBuilder();

        if (vacancies != null) {
            for (Vacancy vacancy : vacancies) {
                vacanciesString.append(vacancy.getId());
                vacanciesString.append(" ");
                vacanciesString.append(vacancy.getProfession() + "\n");
                vacanciesString.append(vacancy.getPublicationDate() + "\n");
                vacanciesString.append(vacancy.getTown() + "\n\n");
            }
        } else {
            vacanciesString.append("Sorry, no vacancies found!");
        }

        response.setMessage(vacanciesString.toString());

        return response;
    }
}
