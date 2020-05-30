package bot;

import network.NetworkService;
import network.NetworkServiceImpl;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        NetworkServiceImpl networkService = new NetworkServiceImpl();
        Map<String, String> vacancies = new LinkedHashMap<>();
        vacancies.put("town", "4");
        vacancies.put("experience", "4");
        networkService.getVacanciesList(vacancies);
        networkService.getCataloguesList();
        try {
            botsApi.registerBot(new WorkBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
