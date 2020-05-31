package responses;

import commands.*;
import org.telegram.telegrambots.meta.api.objects.Update;
import user.ArrayListUserDao;
import user.User;
import user.UserDao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//import static jdk.internal.org.jline.utils.Colors.s;

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
        commandCreator.put(CommandEnum.MENU, (String s, User user) -> new MenuCommand(s, user));
        commandCreator.put(CommandEnum.LANGUAGE, (String s, User user) -> new LanguageCommand(s, user));
        //commandCreator.put();
        //commandCreator.put();
        //commandCreator.put();

        System.out.println(commandEnum);

        creator = commandCreator.get(commandEnum);
//        if (commandCreator.containsKey(commandEnum)){
//            creator = commandCreator.get(commandEnum);
//        }else{
//            creator = commandCreator.get(CommandEnum.OTHER);
//        }

        Command command = creator.create(request, userDao.getUserById(chatId));

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
                .filter(commandEnum -> commandEnum.getCommand().equals(request.trim().toLowerCase()))
                .findAny()
                .orElse(CommandEnum.OTHER);
    }


}
