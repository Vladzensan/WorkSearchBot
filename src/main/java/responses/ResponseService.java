package responses;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface ResponseService {
    Response getResponse(Update update);
}
