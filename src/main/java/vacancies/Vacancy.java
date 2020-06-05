package vacancies;

import lombok.Data;

import java.util.Date;
import java.util.ResourceBundle;

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
    private String email;
    private String contactName;
    private String phone;
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

    public String getFullInfo(ResourceBundle resourceBundle) {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append("\n");
        if (profession != null) {
            sb.append("---").append(profession).append("---\n");
        }
        if (publicationDate != null) {
            sb.append("*");
            sb.append(publicationDate.toString()).append("\n");
        }

        if (description != null) {
            sb.append("*");
            sb.append(description).append("\n");
        }
        if (salaryTo > 0) {
            sb.append("*");
            sb.append(salaryFrom).append(" - ").append(salaryTo).append("RUB").append("\n");
        }

        if (typeOfWork != 0) {
            sb.append("*");
            sb.append(resourceBundle.getString("type_work_" + typeOfWork)).append("\n");
        }

        if (placeOfWork != 0) {
            sb.append("*");
            sb.append(resourceBundle.getString("place_work_" + placeOfWork)).append("\n");
        }


        if (companyName != null) {
            sb.append("*");
            sb.append(companyName).append("\n");
        }


        if (address != null) {
            sb.append("*");
            sb.append(address).append("\n");
        } else if (town != null) {
            sb.append("*");
            sb.append(town).append("\n");
        }

        sb.append("\n***").append(resourceBundle.getString("contacts_msg")).append(":");

        if (contactName != null) {
            sb.append("\n").append(contactName);
        }

        if (email != null) {
            sb.append(", ").append(email);
        }

        if (phone != null) {
            sb.append(", ").append(phone);
        }

        return sb.toString();
    }
}
