package network;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import vacancies.Catalogue;
import vacancies.VacanciesInfo;
import vacancies.Vacancy;

import javax.security.auth.login.FailedLoginException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class NetworkServiceImpl implements NetworkService {
    private final String APP_KEY = "v3.r.132136870.4ee38e902a0d001e916d40c50ebc65a0462696ec.cd36a27c87d8e690a1884ec256b337763d26d188";
    private final String APP_ID = "1463";
    private final String AUTH_PATH = "https://api.superjob.ru/2.0/oauth2/password/";
    private final String CATALOGUES_PATH = "https://api.superjob.ru/2.0/catalogues";
    private final String VACANCIES_PATH = "https://api.superjob.ru/2.0/vacancies/?";

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

    @Override
    public List<Catalogue> getCataloguesList() {
        List<Catalogue> catalogues;
        try {
            URL url = new URL(CATALOGUES_PATH);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            if (con.getResponseCode() == 200) {
                System.out.println(con.getResponseMessage());
                String jsonData = readData(con);
                ObjectMapper mapper = new ObjectMapper();
                catalogues = mapper.readValue(jsonData, new TypeReference<List<Catalogue>>() {
                });
                return catalogues;
            } else {
                System.out.println("Error occurred");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Vacancy> getVacanciesList(Map<String, String> searchParameters) {
        URL url;
        List<Vacancy> vacancies = new ArrayList<>();
        try {
            StringBuilder path = new StringBuilder(VACANCIES_PATH);
            for (String parameter : searchParameters.keySet()) {
                path.append(parameter);
                path.append("=");
                path.append(searchParameters.get(parameter));
                path.append("&");
            }
            url = new URL(path.toString());

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("X-Api-App-Id", APP_KEY);
            con.setRequestMethod("GET");

            if (con.getResponseCode() == 200) {
                System.out.println(con.getResponseMessage());
                String jsonData = readData(con);
                ObjectMapper mapper = new ObjectMapper();
                VacanciesInfo vacanciesInfo = mapper.readerFor(VacanciesInfo.class).readValue(jsonData);
                for (Object vacancyData : vacanciesInfo.getObjects()) {
                    LinkedHashMap<String, Object> rawVacancy = (LinkedHashMap<String, Object>) vacancyData;
                    Vacancy vacancy = new Vacancy();
                    vacancy.setId((int) rawVacancy.get("id"));
                    vacancy.setProfession((String) rawVacancy.get("profession"));
                    vacancy.setPublicationDate((int) rawVacancy.get("date_published"));
                    LinkedHashMap<String, Object> townInfo = (LinkedHashMap<String, Object>) rawVacancy.get("town");
                    vacancy.setTown((String) townInfo.get("title"));
                    vacancies.add(vacancy);
                }
                System.out.println(vacancies);
                return vacancies;

            } else {
                System.out.println("Error occurred");
                //throw new FailedLoginException();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
