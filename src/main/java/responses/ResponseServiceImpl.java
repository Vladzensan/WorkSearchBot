package responses;

import commands.*;
import org.telegram.telegrambots.meta.api.objects.Update;
import user.ArrayListUserDao;
import user.User;
import user.UserDao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ResponseServiceImpl implements ResponseService {

    private UserDao userDao = ArrayListUserDao.getInstance();

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

        Map<CommandEnum, CommandCreator> commandCreator = new HashMap<>();
        CommandCreator creator;
        commandCreator.put(CommandEnum.MENU, MenuCommand::new);
        commandCreator.put(CommandEnum.LANGUAGE, LanguageCommand::new);

        System.out.println(commandEnum);

        creator = commandCreator.get(commandEnum);

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


}
