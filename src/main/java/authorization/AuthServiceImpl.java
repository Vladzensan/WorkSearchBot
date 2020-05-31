package authorization;


import network.NetworkService;
import network.NetworkServiceImpl;

import javax.security.auth.login.FailedLoginException;
import javax.ws.rs.NotAuthorizedException;
import java.util.HashMap;
import java.util.Map;

public class AuthServiceImpl implements AuthService {
    private NetworkService networkService;
    private Map<Long, AuthToken> chatTokens = new HashMap<>();

    private AuthServiceImpl() {
        networkService = new NetworkServiceImpl();
    }

    public static AuthServiceImpl getInstance() {
        return LoginServiceHolder.INSTANCE;
    }

    public boolean login(String login, String password, long chatId) {
        try {
            AuthToken token = networkService.getAccessToken(login, password);
            chatTokens.put(chatId, token);
            return true;
        } catch (FailedLoginException e) {
            return false;
        }
    }

    @Override
    public AuthToken getToken(long chatId) {
        return chatTokens.get(chatId);
    }

    @Override
    public boolean isLoggedIn(long chatId) {
        return chatTokens.containsKey(chatId);
    }

    @Override
    public boolean logout(long chatId) {
        try {
            if (networkService.logout(chatId)) {
                chatTokens.remove(chatId);
                return true;
            }
        } catch (NotAuthorizedException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static class LoginServiceHolder {
        static final AuthServiceImpl INSTANCE = new AuthServiceImpl();
    }
}
