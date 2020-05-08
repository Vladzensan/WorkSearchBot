package authorization;

public interface LoginService {
    boolean login(String login, String password, long chatId);
}
