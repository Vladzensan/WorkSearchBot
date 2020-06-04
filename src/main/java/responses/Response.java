package responses;

import lombok.Data;
import network.Coordinates;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Data
public class Response {
    Coordinates location;
    private String message = null;
    private ReplyKeyboard markup;
    private int editMessageId;

    public Response() {
        markup = new InlineKeyboardMarkup();
        editMessageId = -1;
    }

    public boolean hasLocation() {
        return location != null;
    }

    public boolean hasMessage() {
        return message != null;
    }

    public boolean hasKeyboardMarkup() {
        return markup != null;
    }

}
