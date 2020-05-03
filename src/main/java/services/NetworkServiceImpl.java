package services;


import javax.security.auth.login.FailedLoginException;

public class NetworkServiceImpl implements NetworkService {
    private final String APP_KEY = "v3.r.132136870.4ee38e902a0d001e916d40c50ebc65a0462696ec.cd36a27c87d8e690a1884ec256b337763d26d188";
    private final String APP_ID = "1463";
    private final String AUTH_PATH = "https://www.superjob.ru/authorize/";
    public String getAccessToken(String login, String password) throws FailedLoginException {
//        URL url = null;
//        try {
//            url = new URL(AUTH_PATH);
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//            appendKey(con);
//
//
//        } catch (ProtocolException | MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        throw new FailedLoginException();
    }

//    private void appendKey(HttpURLConnection connection){
//        connection.addRequestProperty("X-Api-App-Id", APP_KEY);
//    }
}
