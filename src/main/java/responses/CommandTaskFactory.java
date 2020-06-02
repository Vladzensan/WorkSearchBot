package responses;

import commands.CommandEnum;
import filters.FiltersDao;
import filters.UserFiltersDao;
import locale.LocaleService;
import locale.LocaleServiceImpl;
import user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CommandTaskFactory {

    private static FiltersDao filtersDao = UserFiltersDao.getInstance();
    private static Map<CommandEnum, CommandTask> instance;
    private static LocaleService localeService = LocaleServiceImpl.getInstance();
    private static Response response;

    private CommandTaskFactory() {
    }

    public static CommandTask getTask(CommandEnum command) {
        if (instance == null) {
            initializeTasks();
        }

        return instance.get(command);
    }

    private static void initializeTasks() {
        instance = new HashMap<>();

    }

    private static Response handleSalaryFrom(String query, User user) {
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

        response = new Response();

        response.setMessage("Enter min salary");//TODO:add to constants

        user.setState(CommandEnum.SALARYFROM);

        return response;
    }

    private static Response handleSalaryTo(String query, User user) {
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

        response = new Response();

        response.setMessage("Enter max salary");//TODO:add to constants

        user.setState(CommandEnum.SALARYTO);

        return response;
    }


    private static Response handleCreateResume(String s, User user) {
        Response response = new Response();
        response.setMessage("Not implemented yet");
        return response;
    }

    private static Response handleResumes(String s, User user) {
        Response response = new Response();
        response.setMessage("Not implemented yet");
        return response;
    }


}
