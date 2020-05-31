package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
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
        if (update.hasCallbackQuery() || update.hasMessage() && update.getMessage().hasText()) {

            Response response = responseService.getResponse(update);

            long chatId = getChatId(update);

            BotApiMethod method;
            if (response.getEditMessageId() == -1) {
                SendMessage sendMessage = new SendMessage()
                        .setChatId(chatId)
                        .setText(response.getMessage());

                if (response.hasKeyboardMarkup()) {
                    sendMessage.setReplyMarkup(response.getMarkup());
                }

                method = sendMessage;
            } else {
                method = new EditMessageText()
                        .setChatId(chatId)
                        .setMessageId(response.getEditMessageId())
                        .setReplyMarkup(response.getMarkup())
                        .setText(response.getMessage());
            }

            try {
                execute(method);
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
