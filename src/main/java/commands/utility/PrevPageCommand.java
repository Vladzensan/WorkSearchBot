package commands.utility;

import commands.Command;
import commands.CommandEnum;
import commands.Utilities;
import filters.Filter;
import filters.UserFiltersDao;
import network.NetworkServiceImpl;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import responses.Response;
import user.User;
import vacancies.Vacancy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrevPageCommand extends Command {
    public PrevPageCommand(String s, User user) {
        super(s, user);
    }

    @Override
    public Response execute() {
        UserFiltersDao filtersDao = UserFiltersDao.getInstance();

        List<CommandEnum> navigationButtons = new ArrayList<>(Arrays.asList(CommandEnum.PREVIOUSPAGE,
                CommandEnum.NEXTPAGE));

        NetworkServiceImpl networkService = new NetworkServiceImpl();

        response = new Response();

        if (user.getState() == CommandEnum.FIND) {
            int page = Integer.parseInt(filtersDao.getFilters(user.getChatId()).get(Filter.PAGE));
            page--;
            filtersDao.addFilter(user.getChatId(), Filter.PAGE, String.valueOf(Math.max(page, 0)));
            StringBuilder vacanciesString = new StringBuilder();
            List<Vacancy> vacancies = networkService.getVacanciesList(filtersDao.getFilters(user.getChatId()));
            if (vacancies != null && !vacancies.isEmpty()) {
                for (Vacancy vacancy : vacancies) {
                    vacanciesString.append("/").append(vacancy.toString()).append("\n\n");
                }
                vacanciesString.append("Page - ").append(filtersDao.getFilters(user.getChatId()).get(Filter.PAGE));
            } else {
                vacanciesString.append("Sorry, no vacancies found!");
            }
            response.setMessage(vacanciesString.toString());
        }

        response.setMarkup(new InlineKeyboardMarkup(Utilities.mapButtonsByTwo(navigationButtons, user.getCurrentLocale())));

        int editMessageId = user.getCurrentUpdate().getCallbackQuery().getMessage().getMessageId();
        response.setEditMessageId(editMessageId);

        return response;
    }
}
