package network;

import filters.Filter;
import vacancies.Catalogue;
import vacancies.Vacancy;

import java.util.List;
import java.util.Map;

public interface NetworkVacanciesService {
    List<Catalogue> getCataloguesList();

    List<Vacancy> getVacanciesList(Map<Filter, String> searchParameters);

    Vacancy getVacancy(long vacancyId);

    List<Vacancy> getFavoriteVacancies(long chatId);

    boolean addFavoriteVacancy(long chatId, long vacancyId);

    boolean removeFavoriteVacancy(long chatId, long vacancyId);
}
