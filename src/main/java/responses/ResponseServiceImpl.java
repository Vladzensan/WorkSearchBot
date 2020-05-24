package responses;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

public class ResponseServiceImpl implements ResponseService {


    public Response getResponse(Update update) {
        String request = update.getMessage().getText();
        String[] words = request.split("\\s+");

        Command command = defineCommand(words[0]);

        return CommandTaskFactory.getTask(command).execute(request, update.getMessage().getChatId());
    }

    private Command defineCommand(String request) {
        return Arrays.stream(Command.values())
                .filter(command -> command.getCommand().equals(request.trim().toLowerCase()))
                .findAny()
                .orElse(Command.UNDEFINED);
    }

//    private Response getLoginResponse(String request, long chatId) {
//        String[] tokens = request.split("\\s+");
//        boolean isLoggedIn = loginService.login(tokens[1], tokens[2], chatId);
//
//        Response response = new Response();
//        if (isLoggedIn) {
//            response.setMessage(Constants.LOGIN_SUCCESS_MSG);
//        } else {
//            response.setMessage(Constants.LOGIN_FAILURE_MSG);
//        }
//
//        return response;
//    }


}
