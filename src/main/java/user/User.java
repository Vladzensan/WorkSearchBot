package user;

import lombok.Data;
import responses.Command;

import java.util.Locale;

@Data
public class User {
    private Locale currentLocale;
    private Command state;
    private long chatId;

    public User(long chatId) {
        this.currentLocale = new Locale("ru");
        this.chatId = chatId;
    }
}
