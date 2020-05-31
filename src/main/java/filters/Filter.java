package filters;

public enum Filter {
    SALARY_FROM("payment_from"),
    SALARY_TO("payment_to"),
    AGE("age"),
    EXPERIENCE("experience"),
    PLACE_OF_WORK("place_of_work"),
    CATALOGUES("catalogues");

    private String name;

    public String getName() {
        return this.name;
    }

    Filter(String name) {
        this.name = name;
    }
}
