package locale;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public interface LocaleService {
    ResourceBundle getLocaleBundle(Locale locale);
}
