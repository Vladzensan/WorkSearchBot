package responses;

import org.telegram.telegrambots.meta.api.objects.Update;
import user.ArrayListUserDao;
import user.User;
import user.UserDao;

import java.util.Arrays;

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

        Command command = defineCommand(words[0]);
        System.out.println(command.getCommand());
        return CommandTaskFactory.getTask(command).execute(request, chatId);
    }

    private void validatePresentUser(long chatId) {
        if (!userDao.containsChatId(chatId)) {
            userDao.addUser(new User(chatId));
        }
    }

    private Command defineCommand(String request) {
        return Arrays.stream(Command.values())
                .filter(command -> command.getCommand().equals(request.trim().toLowerCase()))
                .findAny()
                .orElse(Command.UNDEFINED);
    }


}
