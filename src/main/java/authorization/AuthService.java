package authorization;

public interface AuthService {
    boolean login(String login, String password, long chatId);

    AuthToken getToken(long chatId);

    boolean isLoggedIn(long chatId);

}
