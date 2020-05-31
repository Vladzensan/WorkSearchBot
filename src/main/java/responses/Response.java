package responses;

import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Data
public class Response {
    private String message = null;
    private InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

    public boolean hasMessage() {
        return message != null;
    }

    public boolean hasKeyboardMarkup() {
        return markup.getKeyboard() != null && !markup.getKeyboard().isEmpty();
    }

}
