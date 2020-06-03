package errors;

import filters.Filter;
import network.NetworkServiceImpl;

public class FilterChecker {

    private final String PLACE_OF_WORK_PATTERN = "1|2|3";
    private final String CATALOGUE_PATTERN = "\\d+";
    private final String PAYMENT_PATTERN = ">\\d+<\\d+";
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
        return input.matches(CATALOGUE_PATTERN) && new NetworkServiceImpl().getCataloguesList().contains(Integer.parseInt(input));
    }

    private boolean isCorrectPlaceOfWork(String input) {
        return input.matches(PLACE_OF_WORK_PATTERN);
    }
}
