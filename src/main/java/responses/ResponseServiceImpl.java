package responses;

import commands.Command;
import commands.CommandCreator;
import commands.CommandEnum;
import commands.auth.AuthCommand;
import commands.auth.LoginCommand;
import commands.auth.LogoutCommand;
import commands.profile.FavoritesCommand;
import commands.profile.LanguageCommand;
import commands.profile.ProfileCommand;
import commands.profile.ProfileInfoCommand;
import commands.search.*;
import commands.utility.BackMenuCommand;
import commands.utility.MenuCommand;
import commands.utility.NextPageCommand;
import commands.utility.PrevPageCommand;
import org.telegram.telegrambots.meta.api.objects.Update;
import user.ArrayListUserDao;
import user.User;
import user.UserDao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ResponseServiceImpl implements ResponseService {

    private UserDao userDao = ArrayListUserDao.getInstance();
    private Map<CommandEnum, CommandCreator> commandCreator = getCommands();

    public Response getResponse(Update update) {
        String request = "undefined";

        long chatId = 0L;
        if (update.hasMessage()) {
            request = update.getMessage().getText();
            chatId = update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            request = update.getCallbackQuery().getData();
            chatId = update.getCallbackQuery().getMessage().getChatId();
        }

        validatePresentUser(chatId);

        String[] words = request.split("\\s+");

        CommandEnum commandEnum = defineCommand(words[0]);
        System.out.println(commandEnum.getCommand());

        CommandCreator creator = commandCreator.get(commandEnum);

        User user = userDao.getUserById(chatId);
        user.setCurrentUpdate(update);

        Command command = creator.create(request, user);

        System.out.println("shit");

        return command.execute();
    }

    private void validatePresentUser(long chatId) {
        if (!userDao.containsChatId(chatId)) {
            userDao.addUser(new User(chatId));
        }
    }

    private CommandEnum defineCommand(String request) {
        return Arrays.stream(CommandEnum.values())
                .filter(command -> command.getCommand().equals(request.trim().toLowerCase()))
                .findAny()
                .orElse(CommandEnum.OTHER);
    }


    private Map<CommandEnum, CommandCreator> getCommands() {
        Map<CommandEnum, CommandCreator> commands = new HashMap<>();

        commands.put(CommandEnum.MENU, MenuCommand::new);
        commands.put(CommandEnum.LANGUAGE, LanguageCommand::new);
        commands.put(CommandEnum.AUTH, AuthCommand::new);
        commands.put(CommandEnum.AGE, AgeCommand::new);
        commands.put(CommandEnum.BACK_MENU, BackMenuCommand::new);
        commands.put(CommandEnum.CATALOGUES, CataloguesCommand::new);
        commands.put(CommandEnum.EXPERIENCE, ExperienceCommand::new);
        commands.put(CommandEnum.FIND, FindCommand::new);
        commands.put(CommandEnum.FAVORITES, FavoritesCommand::new);
        commands.put(CommandEnum.LOGIN, LoginCommand::new);
        commands.put(CommandEnum.LOGOUT, LogoutCommand::new);
        commands.put(CommandEnum.NEXTPAGE, NextPageCommand::new);
        commands.put(CommandEnum.PREVIOUSPAGE, PrevPageCommand::new);
        commands.put(CommandEnum.OTHER, OtherCommand::new);
        commands.put(CommandEnum.SEARCH, SearchCommand::new);
        commands.put(CommandEnum.PROFILE, ProfileCommand::new);
        commands.put(CommandEnum.PROFILE_INFO, ProfileInfoCommand::new);
        commands.put(CommandEnum.PLACEOFWORK, WorkPlaceCommand::new);
        commands.put(CommandEnum.SALARYFROM, SalaryFromCommand::new);
        commands.put(CommandEnum.SALARYTO, SalaryToCommand::new);
        commands.put(CommandEnum.CLEARFILTERS, ClearFiltersCommand::new);

        return commands;
    }
}
