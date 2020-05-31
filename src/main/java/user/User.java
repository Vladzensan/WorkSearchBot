package user;

import lombok.Data;
import commands.CommandEnum;

import java.util.Locale;

@Data
public class User {
    private Locale currentLocale;
    private CommandEnum state;
    private long chatId;

    public User(long chatId) {
        this.currentLocale = new Locale("ru");
        this.chatId = chatId;
    }
}
