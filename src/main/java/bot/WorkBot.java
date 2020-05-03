package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import responses.Response;
import responses.ResponseService;
import responses.ResponseServiceImpl;

import java.util.logging.Logger;

public class WorkBot extends TelegramLongPollingBot {
    private ResponseService responseService;
    private static Logger logger = Logger.getLogger(WorkBot.class.getName());


    public WorkBot() {
        responseService = new ResponseServiceImpl();
    }


    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            String request = update.getMessage().getText();
            System.out.println(request);
            Response response = responseService.getResponse(request);


            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(update.getMessage().getChatId())
                    .setText(response.getMessage());

            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
    }

//    private String getResponse(){
//        Scanner scanner = new Scanner(System.in);
//        return scanner.nextLine();
//    }

    public String getBotUsername() {
        return "KVBot";
    }

    public String getBotToken() {
        return "996210404:AAHAYQcaZyNuxLl98n95I03H7aYzb2L_MbA";

    }
}
