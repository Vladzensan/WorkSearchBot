package mappers;

import authorization.AuthToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import user.UserInfo;
import vacancies.Catalogue;
import vacancies.VacanciesInfo;
import vacancies.Vacancy;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
            vacancy.setPublicationDate((int) rawVacancy.get("date_published"));
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

}
