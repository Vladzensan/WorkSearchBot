package responses;

import authorization.LoginService;
import authorization.LoginServiceImpl;
import locale.LocaleService;
import locale.LocaleServiceImpl;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import user.ArrayListUserDao;
import user.User;
import user.UserDao;

import java.util.*;

public class CommandTaskFactory {

    private static Map<Command, CommandTask> instance;
    private static LocaleService localeService = LocaleServiceImpl.getInstance();
    private static UserDao userDao = ArrayListUserDao.getInstance();

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

    }

    private static Response helpHandler(String query, long chatId) {
        Response response = new Response();

        response.setMessage("Help info");

        return response;
    }


    private static Response loginHandler(String query, long chatId) {
        Response response = new Response();
        ResourceBundle constants = localeService.getMessageBundle(userDao.getUserById(chatId).getCurrentLocale());

        LoginService loginService = LoginServiceImpl.getInstance();

        String[] tokens = query.split("\\s+");
        if (tokens.length == 1) {
            response.setMessage(constants.getString("login_info"));
            return response;
        }

        boolean isLoggedIn = loginService.login(tokens[1], tokens[2], chatId);
        if (isLoggedIn) {
            response.setMessage(constants.getString("login_success"));
        } else {
            response.setMessage(constants.getString("login_failure"));
        }

        return response;
    }

    private static Response handleMenu(String query, long chatId) {
        final List<Command> menuCommands = new ArrayList<>(Arrays.asList(Command.PROFILE, Command.AUTH,
                Command.SEARCH, Command.FAVORITES, Command.LANGUAGE));
        User user = userDao.getUserById(chatId);
        Response response = new Response();
        response.setMessage("WorkSearch bot menu:");

        response.getMarkup().setKeyboard(mapButtonsByTwo(menuCommands, user.getCurrentLocale()));
        return response;
    }

    private static Response handleAuth(String query, long chatId) {
        final List<Command> menuCommands = new ArrayList<>(Arrays.asList(Command.LOGIN, Command.LOGOUT));
        User user = userDao.getUserById(chatId);

        Response response = new Response();
        response.setMessage(Command.AUTH.getCaption(user.getCurrentLocale()));
        response.getMarkup().setKeyboard(mapButtonsByTwo(menuCommands, user.getCurrentLocale()));

        return response;
    }


    private static Response handleLanguage(String query, long chatId) {
        User user = userDao.getUserById(chatId);
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());
        Response response = new Response();

        String[] tokens = query.split("\\s+");
        if (tokens.length == 1) {
            response.setMessage(constants.getString("language_info"));
            return response;
        }


        String language = tokens[1].toLowerCase();
        if (language.equals("en") || language.equals("ru")) {
            user.setCurrentLocale(new Locale(language));
            constants = localeService.getMessageBundle(userDao.getUserById(chatId).getCurrentLocale());

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
