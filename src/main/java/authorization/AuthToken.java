package authorization;

import lombok.Data;

@Data
public class AuthToken {
    private String accessToken;
    private String tokenType;


}
