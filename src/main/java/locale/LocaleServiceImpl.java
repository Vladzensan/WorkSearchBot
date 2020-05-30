package locale;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class LocaleServiceImpl implements LocaleService {
    private static final String MESSAGE_BASENAME = "locale.message.MessageBundle";
    private static final String MENUITEM_BASENAME = "locale.menuitem.MenuItemBundle";

    private Map<Locale, Map<String, ResourceBundle>> localeMap;

    private LocaleServiceImpl() {
        localeMap = new HashMap<>();
    }

    public static LocaleServiceImpl getInstance() {
        return LocaleServiceHolder.instance;
    }

    @Override
    public ResourceBundle getMessageBundle(Locale locale) {
        if (!localeMap.containsKey(locale) || !localeMap.get(locale).containsKey(MESSAGE_BASENAME)) {
            loadLocale(MESSAGE_BASENAME, locale);
        }

        return localeMap.get(locale).get(MESSAGE_BASENAME);
    }

    @Override
    public ResourceBundle getMenuItemBundle(Locale locale) {
        if (!localeMap.containsKey(locale) || !localeMap.get(locale).containsKey(MENUITEM_BASENAME)) {
            loadLocale(MENUITEM_BASENAME, locale);
        }

        return localeMap.get(locale).get(MENUITEM_BASENAME);
    }

    private void loadLocale(String baseName, Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
        if (!localeMap.containsKey(locale)) {
            localeMap.put(locale, new HashMap<>());
        }

        localeMap.get(locale).put(baseName, bundle);
    }

    private static class LocaleServiceHolder {
        static final LocaleServiceImpl instance = new LocaleServiceImpl();
    }
}
