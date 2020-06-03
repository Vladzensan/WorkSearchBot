package filters;

public enum Filter {
    PAGE("page"),
    SALARY_TO("payment_to"),
    SALARY_FROM("payment_from"),
    AGE("age"),
    EXPERIENCE("experience"),
    PLACE_OF_WORK("place_of_work"),
    CATALOGUES("catalogues");

    private String name;

    Filter(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
