package locale;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class LocaleServiceImpl implements LocaleService {
    private static final String BUNDLE_BASENAME = "locale.MessageBundle";

    private Map<Locale, ResourceBundle> localeMap = new HashMap<>();

    public static LocaleServiceImpl getInstance() {
        return LocaleServiceHolder.instance;
    }

    @Override
    public ResourceBundle getLocaleBundle(Locale locale) {
        if (!localeMap.containsKey(locale)) {
            loadLocale(locale);
        }

        return localeMap.get(locale);
    }

    private void loadLocale(Locale locale) {
        ResourceBundle messages = ResourceBundle.getBundle(BUNDLE_BASENAME, locale);
        localeMap.put(locale, messages);
    }

    private static class LocaleServiceHolder {
        static final LocaleServiceImpl instance = new LocaleServiceImpl();
    }
}
