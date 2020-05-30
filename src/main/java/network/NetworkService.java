package network;

import javax.security.auth.login.FailedLoginException;

public interface NetworkService extends NetworkVacanciesService {
    String getAccessToken(String login, String password) throws FailedLoginException;

}
