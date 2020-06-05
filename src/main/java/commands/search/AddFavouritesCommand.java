package commands.search;

import commands.Command;
import network.NetworkServiceImpl;
import responses.Response;
import user.User;

import javax.ws.rs.NotAuthorizedException;
import java.util.ResourceBundle;

public class AddFavouritesCommand extends Command {
    public AddFavouritesCommand(String s, User user) {
        super(s, user);
    }

    @Override
    public Response execute() {
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

        NetworkServiceImpl networkService = new NetworkServiceImpl();
        response = new Response();

        try {
            networkService.addFavoriteVacancy(user.getChatId(), user.getVacancy().getId());
        } catch (NotAuthorizedException e) {
            response.setMessage(constants.getString("login_require_msg"));

            return response;
        }

        response.setMessage(constants.getString("add_favorites_complete"));

        return response;
    }
}
