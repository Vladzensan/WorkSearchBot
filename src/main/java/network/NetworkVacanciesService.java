package network;

import filters.Filter;
import vacancies.Catalogue;
import vacancies.Vacancy;

import java.util.List;
import java.util.Map;

public interface NetworkVacanciesService {
    List<Catalogue> getCataloguesList();

    List<Vacancy> getVacanciesList(Map<Filter, String> searchParameters);
}
