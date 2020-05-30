package network;

import vacancies.Catalogue;

import javax.security.auth.login.FailedLoginException;
import java.util.List;

public interface NetworkService extends NetworkVacanciesService {
    String getAccessToken(String login, String password) throws FailedLoginException;

}
