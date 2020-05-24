package responses;

import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.Audio;

@Data
public class Response {
    private String message = null;
    private Audio audio;

    public boolean hasMessage() {
        return message != null;
    }

}
