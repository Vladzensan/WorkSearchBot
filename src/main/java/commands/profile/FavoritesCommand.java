package commands.profile;

import commands.Command;
import network.NetworkService;
import network.NetworkServiceImpl;
import responses.Response;
import user.User;
import vacancies.Vacancy;

import javax.ws.rs.NotAuthorizedException;
import java.util.List;
import java.util.ResourceBundle;

public class FavoritesCommand extends Command {
    public FavoritesCommand(String s, User user) {
        super(s, user);
    }

    @Override
    public Response execute() {
        Response response;
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());
        try {
            response = new Response();
            NetworkService networkService = new NetworkServiceImpl();
            List<Vacancy> favoriteVacancies = networkService.getFavoriteVacancies(user.getChatId());

            StringBuilder vacanciesString = new StringBuilder();
            for (Vacancy vacancy : favoriteVacancies) {
                vacanciesString.append(vacancy.toString()).append("\n\n");
            }
            response.setMessage(vacanciesString.toString());
            return response;
        } catch (NotAuthorizedException e) {
            response = new Response();
            response.setMessage(constants.getString("login_require_msg"));
            return response;
        }
    }
}
