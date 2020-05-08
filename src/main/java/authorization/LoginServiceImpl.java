package authorization;


import network.NetworkService;
import network.NetworkServiceImpl;

import javax.security.auth.login.FailedLoginException;
import java.util.HashMap;
import java.util.Map;

public class LoginServiceImpl implements LoginService {
    private NetworkService networkService;
    private Map<Long, String> chatTokens = new HashMap<>();

    private LoginServiceImpl() {
        networkService = new NetworkServiceImpl();
    }

    public static LoginServiceImpl getInstance() {
        return LoginServiceHolder.INSTANCE;
    }

    public boolean login(String login, String password, long chatId) {
        try{
            String accessToken = networkService.getAccessToken(login, password);
            chatTokens.put(chatId, accessToken);
            return true;
        } catch (FailedLoginException e){
            return false;
        }
    }

    private static class LoginServiceHolder {
        static final LoginServiceImpl INSTANCE = new LoginServiceImpl();
    }
}
