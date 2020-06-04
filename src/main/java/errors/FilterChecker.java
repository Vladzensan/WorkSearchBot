package errors;

import commands.CommandEnum;
import filters.Filter;
import network.NetworkService;
import network.NetworkServiceImpl;
import vacancies.Catalogue;

import java.util.Arrays;
import java.util.List;

public class FilterChecker {

    private final String PLACE_OF_WORK_PATTERN = "1|2|3";
    private final String CATALOGUE_PATTERN = "\\d+";
    private final String PAYMENT_PATTERN = "\\d+";
    private final String EXPERIENCE_PATTERN = "1|2|3|4";
    private final String AGE_PATTERN = "\\d{2}";
    private final int MIN_AGE = 17;

    public boolean isCorrectInput(String input, Filter filters) {
        switch (filters) {
            case SALARY_TO:
            case SALARY_FROM:
                return isCorrectPayment(input);
            case AGE:
                return isCorrectAge(input);
            case CATALOGUES:
                return isCorrectCatalogue(input);
            case EXPERIENCE:
                return isCorrectExperience(input);
            case PLACE_OF_WORK:
                return isCorrectPlaceOfWork(input);
            default:
                throw new IllegalArgumentException();
        }
    }

    private boolean isCorrectPayment(String input) {
        return input.matches(PAYMENT_PATTERN);
    }

    private boolean isCorrectExperience(String input) {
        return input.matches(EXPERIENCE_PATTERN);
    }

    private boolean isCorrectAge(String input) {
        return input.matches(AGE_PATTERN) && Integer.parseInt(input) > MIN_AGE;
    }

    private boolean isCorrectCatalogue(String input) {
        NetworkServiceImpl networkService = new NetworkServiceImpl();
        List<Catalogue> catalogues = networkService.getCataloguesList();
        if(!input.matches(CATALOGUE_PATTERN)){
           return false;
        } else {
            for (Catalogue catalogue :
                    catalogues) {
                if (catalogue.getKey() == Integer.parseInt(input)) {
                    return true;
                }
            }
            return false;
        }
    }

    private boolean isCorrectPlaceOfWork(String input) {
        return input.matches(PLACE_OF_WORK_PATTERN);
    }
}
