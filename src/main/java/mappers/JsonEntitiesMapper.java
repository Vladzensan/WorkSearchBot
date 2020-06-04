package mappers;

import authorization.AuthToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import network.Coordinates;
import user.UserInfo;
import vacancies.Catalogue;
import vacancies.VacanciesInfo;
import vacancies.Vacancy;

import java.io.IOException;
import java.util.*;

public class JsonEntitiesMapper implements EntitiesMapper {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<Vacancy> mapVacancies(List<Object> rawVacancies) {
        List<Vacancy> vacancies = new LinkedList<>();
        for (Object vacancyData : rawVacancies) {
            LinkedHashMap<String, Object> rawVacancy = (LinkedHashMap<String, Object>) vacancyData;
            Vacancy vacancy = new Vacancy();
            vacancy.setId((int) rawVacancy.get("id"));
            vacancy.setProfession((String) rawVacancy.get("profession"));
            if (rawVacancy.get("address") != null) {
                vacancy.setAddress((String) rawVacancy.get("address"));
            }
            vacancy.setPublicationDate(new Date((int) rawVacancy.get("date_published") * 1000L));
            LinkedHashMap<String, Object> townInfo = (LinkedHashMap<String, Object>) rawVacancy.get("town");
            vacancy.setTown((String) townInfo.get("title"));
            vacancies.add(vacancy);
        }
        return vacancies;
    }

    @Override
    public VacanciesInfo mapVacanciesInfo(String json) throws IOException {
        return mapper.readerFor(VacanciesInfo.class).readValue(json);
    }

    @Override
    public UserInfo mapUserInfo(String json) throws IOException {
        return mapper.readerFor(UserInfo.class).readValue(json);
    }

    @Override
    public List<Catalogue> mapCatalogues(String json) throws IOException {
        return mapper.readValue(json, new TypeReference<List<Catalogue>>() {
        });
    }

    @Override
    public AuthToken extractToken(String json) {
        ObjectMapper mapper = new ObjectMapper();
        AuthToken authToken = new AuthToken();
        try {
            Map<String, String> values = mapper.readValue(json, Map.class);
            authToken.setAccessToken(values.get("access_token"));
            authToken.setTokenType(values.get("token_type"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return authToken;
    }

    @Override
    public Coordinates mapCoords(String json) {
        try {
            Map<String, Object> values = mapper.readValue(json, Map.class);
            Map<String, Object> response = (Map<String, Object>) values.get("response");
            Map<String, Object> geoObjectCollection = (Map<String, Object>) response.get("GeoObjectCollection");
            ArrayList<Object> featureMember = (ArrayList<Object>) geoObjectCollection.get("featureMember");
            Map<String, Object> geoObject = (Map<String, Object>) featureMember.get(0);
            Map<String, Object> innerObj = (Map<String, Object>) geoObject.get("GeoObject");
            Map<String, Object> point = (Map<String, Object>) innerObj.get("Point");

            String pos = (String) point.get("pos");
            String[] coords = pos.split("\\s+");

            return new Coordinates(Double.valueOf(coords[1]), Double.valueOf(coords[0]));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

//    Map<String, Object> values = mapper.readValue(json, Map.class);
//    ArrayList<Object> results = (ArrayList<Object>) values.get("results");
//    Map<String, Object> item = (Map<String, Object>) results.get(0);
//    ArrayList<Object> locations = (ArrayList<Object>) item.get("locations");
//    Map<String, Object> properties = (Map<String, Object>) locations.get(0);
//    Map<String, Double> coords = (Map<String, Double>) properties.get("displayLatLng");