package user;

import commands.CommandEnum;
import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Locale;

@Data
public class User {
    private Locale currentLocale;
    private CommandEnum state;
    private Update currentUpdate;
    private long chatId;

    public User(long chatId) {
        this.currentLocale = new Locale("ru");
        this.chatId = chatId;
    }
}
