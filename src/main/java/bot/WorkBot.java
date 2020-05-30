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
    private static Logger logger = Logger.getLogger(WorkBot.class.getName());
    private ResponseService responseService;


    public WorkBot() {
        responseService = new ResponseServiceImpl();
    }


    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text

        if (update.hasCallbackQuery() || update.hasMessage() && update.getMessage().hasText()) {

            Response response = responseService.getResponse(update);

            long chatId = getChatId(update);
            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(chatId)
                    .setText(response.getMessage());

            if (response.hasKeyboardMarkup()) {
                message.setReplyMarkup(response.getMarkup());
            }

            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
    }

    private long getChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId();
        } else {
            return update.getCallbackQuery().getMessage().getChatId();
        }
    }

    public String getBotUsername() {
        return "JobSearcher";
    }

    public String getBotToken() {
        return "996210404:AAHAYQcaZyNuxLl98n95I03H7aYzb2L_MbA";

    }
}
