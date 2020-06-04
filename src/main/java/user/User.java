package user;

import commands.CommandEnum;
import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.Update;
import vacancies.Vacancy;

import java.util.Locale;

@Data
public class User {
    private Locale currentLocale;
    private CommandEnum state;
    private Update currentUpdate;
    private long chatId;
    private Vacancy vacancy;

    public User(long chatId) {
        this.currentLocale = new Locale("ru");
        this.chatId = chatId;
    }
}
