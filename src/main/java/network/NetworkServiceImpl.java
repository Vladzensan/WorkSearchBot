package network;


import authorization.AuthService;
import authorization.AuthServiceImpl;
import authorization.AuthToken;
import filters.Filter;
import mappers.EntitiesMapper;
import mappers.JsonEntitiesMapper;
import user.UserInfo;
import vacancies.Catalogue;
import vacancies.VacanciesInfo;
import vacancies.Vacancy;

import javax.security.auth.login.FailedLoginException;
import javax.ws.rs.NotAuthorizedException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NetworkServiceImpl implements NetworkService {
    private final String APP_KEY = "v3.r.132136870.4ee38e902a0d001e916d40c50ebc65a0462696ec.cd36a27c87d8e690a1884ec256b337763d26d188";
    private final String APP_ID = "1463";
    private final String AUTH_PATH = "https://api.superjob.ru/2.0/oauth2/password/";
    private final String CATALOGUES_PATH = "https://api.superjob.ru/2.0/catalogues";
    private final String VACANCIES_PATH = "https://api.superjob.ru/2.0/vacancies/?";
    private final String CURRENT_USER_PATH = "https://api.superjob.ru/2.0/user/current";
    private final String FAVORITES_PATH = "https://api.superjob.ru/2.0/favorites/";
    private final String TOKEN_PATH = "https://api.superjob.ru/2.0/oauth2/access_token/";

    private EntitiesMapper entityMapper = new JsonEntitiesMapper();

    public AuthToken getAccessToken(String login, String password) throws FailedLoginException {
        URL url;
        AuthToken authToken;
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

                String jsonData = readData(con);
                authToken = entityMapper.extractToken(jsonData);

                return authToken;

            } else {
                System.out.println("Error occurred");
                throw new FailedLoginException();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean logout(long chatId) {
        String token = tryGetAuthToken(chatId);

        URL url;
        try {
            url = new URL(TOKEN_PATH);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("X-Api-App-Id", APP_KEY);
            con.setRequestMethod("DELETE");
            con.setDoOutput(true);

            writeData(con.getOutputStream(), "access_token=" + token.substring(token.indexOf(' ') + 1));

            return con.getResponseCode() == 204;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Catalogue> getCataloguesList() {
        List<Catalogue> catalogues;
        try {
            URL url = new URL(CATALOGUES_PATH);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            if (con.getResponseCode() == 200) {

                String jsonData = readData(con);
                catalogues = entityMapper.mapCatalogues(jsonData);

                return catalogues;
            } else {
                System.out.println("Error occurred");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Vacancy> getVacanciesList(Map<Filter, String> searchParameters) {
        List<Vacancy> vacancies;
        try {
            StringBuilder path = new StringBuilder(VACANCIES_PATH);
            if(searchParameters != null) {
                for (Filter parameter : searchParameters.keySet()) {
                    path.append(parameter.getName());
                    path.append("=");
                    path.append(searchParameters.get(parameter));
                    path.append("&");
                }
            }

            HttpURLConnection con = requestGet(path.toString(), null);

            if (con != null && con.getResponseCode() == 200) {
                String jsonData = readData(con);

                System.out.println(jsonData);
                VacanciesInfo vacanciesInfo = entityMapper.mapVacanciesInfo(jsonData);
                if(vacanciesInfo.getTotal() == 0){
                    return null;
                }else
                {
                    vacancies = new JsonEntitiesMapper().mapVacancies(vacanciesInfo.getObjects());
                }

                return vacancies;

            } else {
                System.out.println("Error occurred");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Vacancy getVacancy(long vacancyId) {
        return null;
    }

    @Override
    public List<Vacancy> getFavoriteVacancies(long chatId) {
        String token = tryGetAuthToken(chatId);

        List<Vacancy> vacancies;
        try {
            HttpURLConnection connection = requestGet(FAVORITES_PATH, token);

            if (connection != null && connection.getResponseCode() == 200) {
                String json = readData(connection);

                VacanciesInfo vacanciesInfo = entityMapper.mapVacanciesInfo(json);
                vacancies = new JsonEntitiesMapper().mapVacancies(vacanciesInfo.getObjects());

                return vacancies;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean addFavoriteVacancy(long chatId, long vacancyId) {
        String token = tryGetAuthToken(chatId);

        URL url;
        try {
            url = new URL(FAVORITES_PATH);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            con.setRequestProperty("X-Api-App-Id", APP_KEY);
            con.setRequestProperty("Authorization", token);

            writeData(con.getOutputStream(), "ids[0]=" + vacancyId);

            if (con.getResponseCode() == 201) {
                String json = readData(con);
                return json.contains("true");
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeFavoriteVacancy(long chatId, long vacancyId) {
        String token = tryGetAuthToken(chatId);

        URL url;
        try {
            url = new URL(FAVORITES_PATH);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            con.setRequestProperty("X-Api-App-Id", APP_KEY);
            con.setRequestProperty("Authorization", token);

            writeData(con.getOutputStream(), "ids[0]=" + vacancyId);

            return con.getResponseCode() == 204;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    private HttpURLConnection requestGet(String path, String authToken) {
        URL url;
        try {
            url = new URL(path);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.setRequestProperty("X-Api-App-Id", APP_KEY);

            if (authToken != null) {
                con.setRequestProperty("Authorization", authToken);
            }


            System.out.println("Response code for path " + path.substring(0, path.lastIndexOf('/')) + " " + con.getResponseCode());
            return con;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserInfo loadUser(long chatId) {
        String token = tryGetAuthToken(chatId);

        HttpURLConnection connection = requestGet(CURRENT_USER_PATH, token);
        UserInfo userInfo = null;
        try {
            if (connection != null && connection.getResponseCode() == 200) {
                String jsonUser = readData(connection);
                userInfo = entityMapper.mapUserInfo(jsonUser);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userInfo;
    }

    private String tryGetAuthToken(long chatId) {
        AuthService authService = AuthServiceImpl.getInstance();
        if (!authService.isLoggedIn(chatId)) {
            throw new NotAuthorizedException("User must be authorized to execute operation");
        }
        AuthToken authToken = authService.getToken(chatId);

        return authToken.getTokenType() + " " + authToken.getAccessToken();
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

    private void writeData(OutputStream out, String data) {
        try {
            out.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
