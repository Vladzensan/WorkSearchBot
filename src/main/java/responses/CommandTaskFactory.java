package responses;

import authorization.AuthService;
import authorization.AuthServiceImpl;
import locale.LocaleService;
import locale.LocaleServiceImpl;
import network.NetworkServiceImpl;
import network.NetworkUserService;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import user.User;
import user.UserInfo;

import java.util.*;

public class CommandTaskFactory {

    private static Map<Command, CommandTask> instance;
    private static LocaleService localeService = LocaleServiceImpl.getInstance();

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
        instance.put(Command.PROFILE, CommandTaskFactory::handleProfile);
        instance.put(Command.PROFILE_INFO, CommandTaskFactory::handleProfileInfo);
        instance.put(Command.RESUMES, CommandTaskFactory::handleResumes);
        instance.put(Command.CREATE_RESUME, CommandTaskFactory::handleCreateResume);

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

    private static Response helpHandler(String query, User user) {
        Response response = new Response();

        response.setMessage("Help info");

        return response;
    }


    private static Response loginHandler(String query, User user) {
        Response response = new Response();
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

    private static Response handleMenu(String query, User user) {
        final List<Command> menuCommands = new ArrayList<>(Arrays.asList(Command.PROFILE, Command.AUTH,
                Command.SEARCH, Command.FAVORITES, Command.LANGUAGE));
        Response response = new Response();
        response.setMessage("WorkSearch bot menu:");

        response.getMarkup().setKeyboard(mapButtonsByTwo(menuCommands, user.getCurrentLocale()));
        return response;
    }

    private static Response handleAuth(String query, User user) {
        final List<Command> menuCommands = new ArrayList<>(Arrays.asList(Command.LOGIN, Command.LOGOUT));

        Response response = new Response();
        response.setMessage(Command.AUTH.getCaption(user.getCurrentLocale()));
        response.getMarkup().setKeyboard(mapButtonsByTwo(menuCommands, user.getCurrentLocale()));

        return response;
    }


    private static Response handleLanguage(String query, User user) {
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
