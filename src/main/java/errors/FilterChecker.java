package errors;

import commands.CommandEnum;
import filters.Filter;
import network.NetworkServiceImpl;

import java.util.Arrays;

public class FilterChecker {

    private final String PLACE_OF_WORK_PATTERN = "1|2|3";
    private final String CATALOGUE_PATTERN = "\\d+";
    private final String PAYMENT_PATTERN = ">\\d+<\\d+";
    private final String EXPERIENCE_PATTERN = "1|2|3|4";
    private final String AGE_PATTERN = "\\d{2}";
    private final int MIN_AGE = 17;

    public boolean isCorrectInput(String input, Filter filters) {
        return switch (filters) {
            case SALARY_TO, SALARY_FROM -> isCorrectPayment(input);
            case AGE -> isCorrectAge(input);
            case CATALOGUES -> isCorrectCatalogue(input);
            case EXPERIENCE -> isCorrectExperience(input);
            case PLACE_OF_WORK -> isCorrectPlaceOfWork(input);
        };
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
