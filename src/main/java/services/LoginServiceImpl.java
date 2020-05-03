package services;


import javax.security.auth.login.FailedLoginException;

public class LoginServiceImpl implements LoginService {
    private NetworkService networkService;

    public LoginServiceImpl() {
        networkService = new NetworkServiceImpl();
    }

    public boolean login(String login, String password) {
        try{
            String accessToken = networkService.getAccessToken(login, password);
            return true;
        } catch (FailedLoginException e){
            return false;
        }
    }
}
