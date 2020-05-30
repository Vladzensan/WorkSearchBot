package services;

import vacancies.Vacancy;

import java.util.List;

public interface HireSearchService {
    List<Vacancy> getVacanciesWithFilters(String filters);
}
