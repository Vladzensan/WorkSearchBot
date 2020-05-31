package filters;

import java.util.HashMap;
import java.util.Map;

public class UserFiltersDao implements FiltersDao {
    private Map<Long, Map<Filter, String>> userFilters;

    private UserFiltersDao() {
        userFilters = new HashMap<>();
    }

    public static UserFiltersDao getInstance() {
        return FilterDaoHolder.instance;
    }

    @Override
    public void clearFilters(long chatId) {
        userFilters.remove(chatId);
    }

    @Override
    public void addFilter(long chatId, Filter filter, String value) {
        if (!userFilters.containsKey(chatId)) {
            userFilters.put(chatId, new HashMap<>());
        }

        userFilters.get(chatId).put(filter, value);
    }

    @Override
    public Map<Filter, String> getFilters(long chatId) {
        return userFilters.get(chatId);
    }

    private static class FilterDaoHolder {
        static final UserFiltersDao instance = new UserFiltersDao();
    }
}
