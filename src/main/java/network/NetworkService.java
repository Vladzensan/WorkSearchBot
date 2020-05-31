package network;

import authorization.AuthToken;

import javax.security.auth.login.FailedLoginException;

public interface NetworkService extends NetworkVacanciesService, NetworkUserService {
    AuthToken getAccessToken(String login, String password) throws FailedLoginException;

    boolean logout(long chatId);

}
