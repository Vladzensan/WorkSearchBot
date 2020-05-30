package services;

import network.NetworkService;
import network.NetworkServiceImpl;
import vacancies.Catalogue;
import vacancies.Vacancy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HireSearchServiceImpl implements HireSearchService {

    private NetworkService networkService;

    @Override
    public List<Vacancy> getVacanciesWithFilters(String filters) {
        networkService = new NetworkServiceImpl();
        List<Vacancy> vacancies = new ArrayList<>();
        Map<String, String> searchParameters = new HashMap<>();
        //vacancies = networkService.getVacanciesList();
        return null;
    }


}
