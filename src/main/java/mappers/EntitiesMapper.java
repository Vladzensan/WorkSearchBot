package mappers;

import authorization.AuthToken;
import user.UserInfo;
import vacancies.Catalogue;
import vacancies.VacanciesInfo;
import vacancies.Vacancy;

import java.io.IOException;
import java.util.List;

public interface EntitiesMapper {
    List<Vacancy> mapVacancies(List<Object> rawVacancies);

    VacanciesInfo mapVacanciesInfo(String json) throws IOException;

    UserInfo mapUserInfo(String json) throws IOException;

    List<Catalogue> mapCatalogues(String json) throws IOException;

    AuthToken extractToken(String json);
}
