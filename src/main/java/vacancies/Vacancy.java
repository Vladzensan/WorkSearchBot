package vacancies;

import lombok.Data;

import java.util.Date;

@Data
public class Vacancy {
    private int id;
    private String profession;
    private Date publicationDate;
    private String town;


    private String address;
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
        return this.getId() +
                " " +
                this.getProfession() + "\n" +
                publicationDate.toString() + "\n" +
                this.getTown();
    }
}
