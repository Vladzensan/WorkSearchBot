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

        instance.put(Command.HELP, CommandTaskFactory::helpHandler);
        instance.put(Command.LOGIN, CommandTaskFactory::loginHandler);
        instance.put(Command.MENU, CommandTaskFactory::handleMenu);
        instance.put(Command.AUTH, CommandTaskFactory::handleAuth);
        instance.put(Command.LANGUAGE, CommandTaskFactory::handleLanguage);
        instance.put(Command.SEARCH, CommandTaskFactory::handleSearch);
        instance.put(Command.CATALOGUES, CommandTaskFactory::handleCatalogues);
        instance.put(Command.EXPERIENCE, CommandTaskFactory::handleExperience);
        instance.put(Command.AGE, CommandTaskFactory::handleAge);
        instance.put(Command.SALARY, CommandTaskFactory::handleSalary);
        instance.put(Command.PLACEOFWORK, CommandTaskFactory::handlePlaceOfWork);
        instance.put(Command.OTHER, CommandTaskFactory::handleOther);
        instance.put(Command.FIND, CommandTaskFactory::handleFind);
        instance.put(Command.PROFILE, CommandTaskFactory::handleProfile);
        instance.put(Command.PROFILE_INFO, CommandTaskFactory::handleProfileInfo);
        instance.put(Command.RESUMES, CommandTaskFactory::handleResumes);
        instance.put(Command.CREATE_RESUME, CommandTaskFactory::handleCreateResume);

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

    private static Response handleOther(String s, User user) {
        final List<Command> filterCommands = new ArrayList<>(Arrays.asList(Command.PLACEOFWORK, Command.SALARY, Command.EXPERIENCE, Command.CATALOGUES, Command.FIND));
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());
        Response response = new Response();

        if (!filterCommands.contains(user.getState())) {
            response.setMessage(constants.getString("unsupported_cmd"));
        } else {
            response = setFilter(s, user);
        }

        return response;
    }

    private static Response setFilter(String s, User user) {
        final List<Command> filterCommands = new ArrayList<>(Arrays.asList(Command.CATALOGUES, Command.EXPERIENCE,
                Command.SALARY, Command.AGE, Command.PLACEOFWORK, Command.FIND));

        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());
        Map<Command, Filter> commandsToFilters = new HashMap<>();
        commandsToFilters.put(Command.CATALOGUES, Filter.CATALOGUES);
        commandsToFilters.put(Command.PLACEOFWORK, Filter.PLACE_OF_WORK);
        commandsToFilters.put(Command.AGE, Filter.AGE);
        commandsToFilters.put(Command.EXPERIENCE, Filter.EXPERIENCE);
        //commandsToFilters.put(Command.SALARY, Filter.SALARY_FROM); TODO: change salary structure

        response = new Response();

        filtersDao.addFilter(user.getChatId(), commandsToFilters.get(user.getState()), s);

        response.setMessage(constants.getString("filter_header"));

        response.getMarkup().setKeyboard(mapButtonsByTwo(filterCommands, user.getCurrentLocale()));

        user.setState(Command.SEARCH);

        return response;
    }

    private static Response handleExperience(String query, User user) {
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

        response = new Response();

        response.setMessage(constants.getString("filter_experience_helper"));

        return response;
    }

    private static Response handleAge(String query, User user) {
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

        response = new Response();

        response.setMessage(constants.getString("filter_age_helper"));

        return response;
    }

    private static Response handleSalary(String query, User user) {
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

        response = new Response();

        response.setMessage(constants.getString("filter_salary_helper"));

        return response;
    }

    private static Response handlePlaceOfWork(String query, User user) {
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

        response = new Response();

        response.setMessage(constants.getString("filter_place_of_work_helper"));

        return response;
    }

    private static Response handleSearch(String query, User user) {
        final List<Command> filterCommands = new ArrayList<>(Arrays.asList(Command.CATALOGUES, Command.EXPERIENCE,
                Command.SALARY, Command.AGE, Command.PLACEOFWORK, Command.FIND));

        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

        user.setState(Command.SEARCH);

        response = new Response();

        response.setMessage(constants.getString("filter_header"));

        response.getMarkup().setKeyboard(mapButtonsByTwo(filterCommands, user.getCurrentLocale()));

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

        user.setState(Command.CATALOGUES);

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
        NetworkUserService network = new NetworkServiceImpl();
        UserInfo userInfo = network.loadUser(user.getChatId());
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

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
        final List<Command> menuCommands = new ArrayList<>(Arrays.asList(Command.PROFILE_INFO, Command.RESUMES, Command.CREATE_RESUME));
        Response response = new Response();
        Locale locale = user.getCurrentLocale();
        response.setMessage(Command.PROFILE_INFO.getCaption(locale));
        response.getMarkup().setKeyboard(mapButtonsByTwo(menuCommands, locale));
        return response;
    }


    private static Response handleMenu(String query, User user) {
        final List<Command> menuCommands = new ArrayList<>(Arrays.asList(Command.PROFILE, Command.AUTH,
                Command.SEARCH, Command.FAVORITES, Command.LANGUAGE));
        response = new Response();
        response.setMessage("WorkSearch bot menu:");

        response.getMarkup().setKeyboard(mapButtonsByTwo(menuCommands, user.getCurrentLocale()));
        return response;
    }

    private static Response handleAuth(String query, User user) {
        final List<Command> menuCommands = new ArrayList<>(Arrays.asList(Command.LOGIN, Command.LOGOUT));

        response = new Response();
        response.setMessage(Command.AUTH.getCaption(user.getCurrentLocale()));
        response.getMarkup().setKeyboard(mapButtonsByTwo(menuCommands, user.getCurrentLocale()));

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

    private static List<List<InlineKeyboardButton>> mapButtonsByTwo(List<Command> commands, Locale locale) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();


        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        int i = 0;
        for (Command command
                : commands) {

            if (i % 2 == 0 && i > 0) {
                rowsInline.add(rowInline);
                rowInline = new ArrayList<>();
            }

            rowInline.add(new InlineKeyboardButton()
                    .setText(command.getCaption(locale))
                    .setCallbackData(command.getCommand())
                    .setSwitchInlineQueryCurrentChat(command.getCommand()));
            i++;
        }

        if (!rowInline.isEmpty()) {
            rowsInline.add(rowInline);
        }

        return rowsInline;
    }


}
