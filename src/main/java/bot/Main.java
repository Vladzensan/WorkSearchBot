package bot;

import network.NetworkMapsService;
import network.NetworkMapsServiceImpl;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    public static void main(String[] args) {
//
//        ApiContextInitializer.init();
//        TelegramBotsApi botsApi = new TelegramBotsApi();
//        try {
//            botsApi.registerBot(new WorkBot());
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }

        new NetworkMapsServiceImpl().geocodeLocation("Москва");
    }
}
