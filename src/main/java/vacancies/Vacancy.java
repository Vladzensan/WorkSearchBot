package vacancies;

import lombok.Data;

@Data
public class Vacancy {
    private int id;
    private String profession;
    private long publicationDate;
    private String town;

    private int salaryFrom;
    private int salaryTo;
    private int age;
    private int gender;
    private int typeOfWork;
    private int experience;
    private int education;
    private int placeOfWork;
    private String companyName;
    private String description;
}
