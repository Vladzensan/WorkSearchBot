package responses;

import authorization.AuthService;
import authorization.AuthServiceImpl;
import filters.Filter;
import filters.FiltersDao;
import filters.UserFiltersDao;
import locale.LocaleService;
import locale.LocaleServiceImpl;
import network.NetworkService;
import network.NetworkServiceImpl;
import network.NetworkUserService;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import user.User;
import user.UserInfo;
import vacancies.Catalogue;
import vacancies.Vacancy;

import javax.ws.rs.NotAuthorizedException;
import java.util.*;

public class CommandTaskFactory {

    private static FiltersDao filtersDao = UserFiltersDao.getInstance();
    private static Map<Command, CommandTask> instance;
    private static LocaleService localeService = LocaleServiceImpl.getInstance();
    private static Response response;

    private CommandTaskFactory() {
    }

    public static CommandTask getTask(Command command) {
        if (instance == null) {
            initializeTasks();
        }

        return instance.get(command);
    }

    private static void initializeTasks() {
        instance = new HashMap<>();

        instance.put(CommandEnum.HELP, CommandTaskFactory::helpHandler);
        instance.put(CommandEnum.LOGIN, CommandTaskFactory::loginHandler);
        instance.put(CommandEnum.MENU, CommandTaskFactory::handleMenu);
        instance.put(CommandEnum.AUTH, CommandTaskFactory::handleAuth);
        instance.put(CommandEnum.LANGUAGE, CommandTaskFactory::handleLanguage);
        instance.put(CommandEnum.SEARCH, CommandTaskFactory::handleSearch);
        instance.put(CommandEnum.CATALOGUES, CommandTaskFactory::handleCatalogues);
        instance.put(CommandEnum.EXPERIENCE, CommandTaskFactory::handleExperience);
        instance.put(CommandEnum.AGE, CommandTaskFactory::handleAge);
        instance.put(CommandEnum.SALARYFROM, CommandTaskFactory::handleSalaryFrom);
        instance.put(CommandEnum.SALARYTO, CommandTaskFactory::handleSalaryTo);
        instance.put(CommandEnum.PLACEOFWORK, CommandTaskFactory::handlePlaceOfWork);
        instance.put(CommandEnum.OTHER, CommandTaskFactory::handleOther);
        instance.put(CommandEnum.FIND, CommandTaskFactory::handleFind);
        instance.put(CommandEnum.PROFILE, CommandTaskFactory::handleProfile);
        instance.put(CommandEnum.PROFILE_INFO, CommandTaskFactory::handleProfileInfo);
        instance.put(CommandEnum.RESUMES, CommandTaskFactory::handleResumes);
        instance.put(CommandEnum.CREATE_RESUME, CommandTaskFactory::handleCreateResume);
        instance.put(CommandEnum.FAVORITES, CommandTaskFactory::handleFavorites);
        instance.put(CommandEnum.LOGOUT, CommandTaskFactory::handleLogout);
        instance.put(CommandEnum.BACK_MENU, CommandTaskFactory::handleBackMenu);

    }


    private static Response handleBackMenu(String s, User user) {
        Response response = handleMenu(s, user);
        response.setEditMessageId(user.getCurrentUpdate().getCallbackQuery().getMessage().getMessageId());
        return response;
    }

    private static Response handleLogout(String s, User user) {
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());
        AuthService authService = AuthServiceImpl.getInstance();
        boolean isSuccessful = authService.logout(user.getChatId());
        response = new Response();

        response.setMessage(isSuccessful
                ? constants.getString("logout_success_msg")
                : constants.getString("logout_failure_msg"));
        return response;
    }

    private static Response handleFavorites(String s, User user) {
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());
        try {
            List<Vacancy> favoriteVacancies = networkService.getFavoriteVacancies(user.getChatId());
            return getVacanciesResponse(favoriteVacancies);
        } catch (NotAuthorizedException e) {
            response = new Response();
            response.setMessage(constants.getString("login_require_msg"));
            return response;
        }

    }

    private static Response handleFind(String s, User user) {
        NetworkService networkService = new NetworkServiceImpl();

        List<Vacancy> vacancies = networkService.getVacanciesList(filtersDao.getFilters(user.getChatId()));

        response = new Response();

        StringBuilder vacanciesString = new StringBuilder();

        for (Vacancy vacancy : vacancies) {
            vacanciesString.append(vacancy.getId());
            vacanciesString.append(" ");
            vacanciesString.append(vacancy.getProfession() + "\n");
            vacanciesString.append(vacancy.getPublicationDate() + "\n");
            vacanciesString.append(vacancy.getTown() + "\n\n");
        }

        response.setMessage(vacanciesString.toString());

        return response;
    }
    private static Response getVacanciesResponse(List<Vacancy> favoriteVacancies) {
        response = new Response();

        StringBuilder vacanciesString = new StringBuilder();
        for (Vacancy vacancy : favoriteVacancies) {
            vacanciesString.append(vacancy.getId());
            vacanciesString.append(" ");
            vacanciesString.append(vacancy.getProfession()).append("\n");
            vacanciesString.append(new Date(vacancy.getPublicationDate() * 1000L)).append("\n");
            vacanciesString.append(vacancy.getTown()).append("\n\n");
        }

        response.setMessage(vacanciesString.toString());

        return response;
    }

    private static Response handleOther(String s, User user) {
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

    private static Response setFilter(String s, User user) {
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

        filtersDao.addFilter(user.getChatId(), commandsToFilters.get(user.getState()), s);

        response.setMessage(constants.getString("filter_header"));

        response.getMarkup().setKeyboard(mapButtonsByTwo(filterCommandEnums, user.getCurrentLocale()));

        user.setState(CommandEnum.SEARCH);

        return response;
    }

    private static Response handleExperience(String query, User user) {
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

        response = new Response();

        response.setMessage(constants.getString("filter_experience_helper"));
        user.setState(CommandEnum.EXPERIENCE);

        return response;
    }

    private static Response handleAge(String query, User user) {
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

        response = new Response();

        response.setMessage(constants.getString("filter_age_helper"));

        return response;
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

    private static Response handlePlaceOfWork(String query, User user) {
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

        response = new Response();

        response.setMessage(constants.getString("filter_place_of_work_helper"));

        user.setState(CommandEnum.PLACEOFWORK);

        return response;
    }

    private static Response handleSearch(String query, User user) {
        final List<CommandEnum> filterCommandEnums = new ArrayList<>(Arrays.asList(CommandEnum.CATALOGUES, CommandEnum.EXPERIENCE,
                CommandEnum.SALARYFROM, CommandEnum.SALARYTO, CommandEnum.AGE, CommandEnum.PLACEOFWORK, CommandEnum.FIND));

        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

        user.setState(CommandEnum.SEARCH);

        response = new Response();

        response.setMessage(constants.getString("filter_header"));

        response.getMarkup().setKeyboard(mapButtonsByTwo(filterCommandEnums, user.getCurrentLocale()));

        filtersDao.clearFilters(user.getChatId());//clear filters to start new search history

        return response;
    }

    private static Response handleCatalogues(String query, User user) {
        NetworkService networkService = new NetworkServiceImpl();
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

        response = new Response();

        List<Catalogue> catalogues = networkService.getCataloguesList();

        StringBuilder messageCatalogues = new StringBuilder();

        for (Catalogue catalogue : catalogues) {
            messageCatalogues.append(catalogue.getKey());
            messageCatalogues.append(" ");
            messageCatalogues.append(catalogue.getTitle());
            messageCatalogues.append("\n");
        }

        messageCatalogues.append("\n" + constants.getString("filter_catalogues_helper"));

        response.setMessage(messageCatalogues.toString());

        user.setState(CommandEnum.CATALOGUES);

        return response;
    }

    private static Response helpHandler(String query, User user) {
        response = new Response();

        response.setMessage("Help info");

        return response;
    }


    private static Response loginHandler(String query, User user) {
        response = new Response();
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

        AuthService loginService = AuthServiceImpl.getInstance();

        String[] tokens = query.split("\\s+");
        if (tokens.length == 1) {
            response.setMessage(constants.getString("login_info"));
            return response;
        }

        boolean isLoggedIn = loginService.login(tokens[1], tokens[2], user.getChatId());
        if (isLoggedIn) {
            response.setMessage(constants.getString("login_success"));
        } else {
            response.setMessage(constants.getString("login_failure"));
        }

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

    private static Response handleProfileInfo(String s, User user) {
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());
        NetworkUserService network = new NetworkServiceImpl();
        UserInfo userInfo;
        try {
            userInfo = network.loadUser(user.getChatId());
        } catch (NotAuthorizedException e) {
            response = new Response();
            response.setMessage(constants.getString("login_require_msg"));
            return response;
        }

        Response response = new Response();

        response.setMessage("-" + constants.getString("name_property") + ": " + userInfo.getName() + "\n-" +
                constants.getString("email_property") + ": " + userInfo.getEmail() + "\n-" +
                constants.getString("phone_property") + ": " + userInfo.getPhoneNumber() + "\n-" +
                constants.getString("hr_property") + ": " + (userInfo.isHr() ? "+" : "-") + "\n-" +
                constants.getString("regdate_property") + ": " + new Date(userInfo.getRegistrationDate() * 1000L).toString() + "\n-" +
                constants.getString("photo_property") + ": " + userInfo.getPhotoPath());

        return response;
    }

    private static Response handleProfile(String query, User user) {
        final List<Command> menuCommands = new ArrayList<>(Arrays.asList(Command.PROFILE_INFO, Command.RESUMES, Command.CREATE_RESUME, Command.BACK_MENU));
        Response response = new Response();
        Locale locale = user.getCurrentLocale();
        response.setMessage(CommandEnum.PROFILE_INFO.getCaption(locale));
        response.getMarkup().setKeyboard(mapButtonsByTwo(menuCommands, locale));

        int editMessageId = user.getCurrentUpdate().getCallbackQuery().getMessage().getMessageId();
        response.setEditMessageId(editMessageId);
        return response;
    }


    private static Response handleMenu(String query, User user) {
        final List<CommandEnum> menuCommandEnums = new ArrayList<>(Arrays.asList(CommandEnum.PROFILE, CommandEnum.AUTH,
                CommandEnum.SEARCH, CommandEnum.FAVORITES, CommandEnum.LANGUAGE));
        response = new Response();
        response.setMessage("WorkSearch bot menu:");

        response.getMarkup().setKeyboard(mapButtonsByTwo(menuCommandEnums, user.getCurrentLocale()));
        return response;
    }

    private static Response handleAuth(String query, User user) {
        final List<CommandEnum> menuCommandEnums = new ArrayList<>(Arrays.asList(CommandEnum.LOGIN, CommandEnum.LOGOUT));

        response = new Response();
        response.setMessage(CommandEnum.AUTH.getCaption(user.getCurrentLocale()));
        response.getMarkup().setKeyboard(mapButtonsByTwo(menuCommandEnums, user.getCurrentLocale()));

        return response;
    }

    private static Response handleLanguage(String query, User user) {
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());
        response = new Response();

        String[] tokens = query.split("\\s+");
        if (tokens.length == 1) {
            response.setMessage(constants.getString("language_info"));
            return response;
        }


        String language = tokens[1].toLowerCase();
        if (language.equals("en") || language.equals("ru")) {
            user.setCurrentLocale(new Locale(language));
            constants = localeService.getMessageBundle(user.getCurrentLocale());

            response.setMessage(constants.getString("language_success"));

        } else {
            response.setMessage(constants.getString("language_failure"));
        }

        return response;
    }

    private static List<List<InlineKeyboardButton>> mapButtonsByTwo(List<CommandEnum> commandEnums, Locale locale) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();


        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        int i = 0;
        for (CommandEnum commandEnum
                : commandEnums) {

            if (i % 2 == 0 && i > 0) {
                rowsInline.add(rowInline);
                rowInline = new ArrayList<>();
            }

            rowInline.add(new InlineKeyboardButton()
                    .setText(commandEnum.getCaption(locale))
                    .setCallbackData(commandEnum.getCommand())
                    .setSwitchInlineQueryCurrentChat(commandEnum.getCommand()));
            i++;
        }

        if (!rowInline.isEmpty()) {
            rowsInline.add(rowInline);
        }

        return rowsInline;
    }


}
