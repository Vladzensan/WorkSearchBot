package vacancies;

import lombok.Data;

import java.util.Date;

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

    @Override
    public String toString() {
        StringBuilder vacanciesString = new StringBuilder();
        vacanciesString.append(this.getId());
        vacanciesString.append(" ");
        vacanciesString.append(this.getProfession()).append("\n");
        vacanciesString.append(new Date(this.getPublicationDate() * 1000L)).append("\n");
        vacanciesString.append(this.getTown());
        return vacanciesString.toString();
    }
}
