package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import responses.Response;
import responses.ResponseService;
import responses.ResponseServiceImpl;

import java.util.ArrayList;
import java.util.List;
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

            for (BotApiMethod method : extractMessages(response, chatId)) {
                try {
                    execute(method);

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
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

    private List<BotApiMethod> extractMessages(Response response, long chatId) {
        List<BotApiMethod> methods = new ArrayList<>();
        BotApiMethod method;
        if (response.getEditMessageId() == -1) {
            SendMessage sendMessage = new SendMessage()
                    .setChatId(chatId)
                    .setText(response.getMessage());

            if (response.hasKeyboardMarkup()) {
                sendMessage.setReplyMarkup(response.getMarkup());
            }

            methods.add(sendMessage);
        } else if (response.getEditMessageId() != -1) {
            methods.add(new EditMessageText()
                    .setChatId(chatId)
                    .setMessageId(response.getEditMessageId())
                    .setReplyMarkup((InlineKeyboardMarkup) response.getMarkup())
                    .setText(response.getMessage()));
        } else if (response.hasLocation()) {
            Location loc = response.getLocation();
            methods.add(new SendLocation()
                    .setChatId(chatId)
                    .setLatitude(loc.getLatitude())
                    .setLongitude(loc.getLatitude()));
        }
        return methods;
    }

    public String getBotUsername() {
        return "JobSearcher";
    }

    public String getBotToken() {
        return "996210404:AAHAYQcaZyNuxLl98n95I03H7aYzb2L_MbA";

    }
}
