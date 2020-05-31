package user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserInfo {
    private long userId;
    private String email;
    private String phoneNumber;
    private String name;
    private long registrationDate;
    private long lastLoginDate;
    private int idCv;
    private String autoLoginLink;
    private boolean isEmailConfirmed;
    private boolean isHr;
    private boolean isFullRegistration;
    private String photoPath;
    private boolean hasBan;
    private long soulId;

    public UserInfo(@JsonProperty("id") long userId, @JsonProperty("email") String email,
                    @JsonProperty("phone_number") String phoneNumber, @JsonProperty("name") String name,
                    @JsonProperty("date_reg") long registrationDate, @JsonProperty("date_lastlogin") long lastLoginDate,
                    @JsonProperty("email_confirmed") boolean isEmailConfirmed, @JsonProperty("hr") boolean isHr,
                    @JsonProperty("photo") String photoPath, @JsonProperty("id_cv") int idCv,
                    @JsonProperty("autologin") String autoLoginLink, @JsonProperty("is_full_registration") boolean isFullRegistration,
                    @JsonProperty("soul_id") long soulId, @JsonProperty("hasBan") boolean hasBan) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.registrationDate = registrationDate;
        this.isEmailConfirmed = isEmailConfirmed;
        this.lastLoginDate = lastLoginDate;
        this.phoneNumber = phoneNumber;
        this.isHr = isHr;
        this.idCv = idCv;
        this.autoLoginLink = autoLoginLink;
        this.photoPath = photoPath;
        this.isFullRegistration = isFullRegistration;
    }
}
