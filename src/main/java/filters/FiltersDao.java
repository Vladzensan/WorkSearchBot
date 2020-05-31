package filters;

import java.util.Map;

public interface FiltersDao {
    void clearFilters(long chatId);

    void addFilter(long chatId, Filter filter, String value);

    Map<Filter, String> getFilters(long chatId);
}
