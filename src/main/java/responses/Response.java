package responses;

import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Data
public class Response {
    Location location;
    private String message = null;
    private InlineKeyboardMarkup markup;
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
        return markup.getKeyboard() != null && !markup.getKeyboard().isEmpty();
    }

}
