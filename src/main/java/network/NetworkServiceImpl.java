package network;


import com.fasterxml.jackson.databind.ObjectMapper;

import javax.security.auth.login.FailedLoginException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

public class NetworkServiceImpl implements NetworkService {
    private final String APP_KEY = "v3.r.132136870.4ee38e902a0d001e916d40c50ebc65a0462696ec.cd36a27c87d8e690a1884ec256b337763d26d188";
    private final String APP_ID = "1463";
    private final String AUTH_PATH = "https://api.superjob.ru/2.0/oauth2/password/";

    public String getAccessToken(String login, String password) throws FailedLoginException {
        URL url = null;
        try {
            String path = AUTH_PATH
                    + "?login=" + login
                    + "&password=" + password
                    + "&client_id=" + APP_ID
                    + "&client_secret=" + APP_KEY;

            url = new URL(path);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            if (con.getResponseCode() == 200) {
                System.out.println(con.getResponseMessage());
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String jsonData = readData(con);
                return "Hello";

            } else {
                System.out.println("Error occurred");
                throw new FailedLoginException();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }


    }

    private String readData(HttpURLConnection connection) {
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Objects.requireNonNull(result).toString();
    }

    private String extractToken(String jsonData) {
        ObjectMapper mapper = new ObjectMapper();
        String result = null;
        try {
            Map<String, String> values = mapper.readValue(jsonData, Map.class);
            result = values.get("access_token");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
