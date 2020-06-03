package commands.utility;

import commands.Command;
import commands.CommandEnum;
import commands.Utilities;
import filters.Filter;
import filters.FiltersDao;
import filters.UserFiltersDao;
import network.NetworkServiceImpl;
import responses.Response;
import user.User;
import vacancies.Vacancy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NextPageCommand extends Command {
    public NextPageCommand(String s, User user) {
        super(s, user);
    }

    @Override
    public Response execute() {
        UserFiltersDao filtersDao = UserFiltersDao.getInstance();

        List<CommandEnum> navigationButtons = new ArrayList<>(Arrays.asList(CommandEnum.PREVIOUSPAGE,
                CommandEnum.NEXTPAGE));

        NetworkServiceImpl networkService = new NetworkServiceImpl();

        response = new Response();

        if(user.getState() == CommandEnum.FIND){
            int page = Integer.parseInt(filtersDao.getFilters(user.getChatId()).get(Filter.PAGE));
            filtersDao.addFilter(user.getChatId(), Filter.PAGE, String.valueOf(++page));
            StringBuilder vacanciesString = new StringBuilder();
            List<Vacancy> vacancies = networkService.getVacanciesList(filtersDao.getFilters(user.getChatId()));
            if (vacancies != null) {
                for (Vacancy vacancy : vacancies) {
                    vacanciesString.append(vacancy.getId());
                    vacanciesString.append(" ");
                    vacanciesString.append(vacancy.getProfession() + "\n");
                    vacanciesString.append(vacancy.getPublicationDate() + "\n");
                    vacanciesString.append(vacancy.getTown() + "\n\n");
                }
                vacanciesString.append("page - " + filtersDao.getFilters(user.getChatId()).get(Filter.PAGE));
            } else {
                vacanciesString.append("Sorry, no vacancies found!");
            }
            response.setMessage(vacanciesString.toString());
        }

        response.getMarkup().setKeyboard(Utilities.mapButtonsByTwo(navigationButtons, user.getCurrentLocale()));

        int editMessageId = user.getCurrentUpdate().getCallbackQuery().getMessage().getMessageId();
        response.setEditMessageId(editMessageId);

        return response;
    }
}
