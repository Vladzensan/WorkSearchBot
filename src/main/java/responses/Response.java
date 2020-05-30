package responses;

import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.Audio;

import java.util.Map;

@Data
public class Response {
    private String message = null;
    private Map<String,String> buttonsData;
    private Audio audio;

    public boolean hasMessage() {
        return message != null;
    }

}
