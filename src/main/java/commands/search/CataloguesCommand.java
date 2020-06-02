package commands.search;

import commands.Command;
import commands.CommandEnum;
import network.NetworkService;
import network.NetworkServiceImpl;
import responses.Response;
import user.User;
import vacancies.Catalogue;

import java.util.List;
import java.util.ResourceBundle;

public class CataloguesCommand extends Command {
    public CataloguesCommand(String s, User user) {
        super(s, user);
    }

    @Override
    public Response execute() {
        NetworkService networkService = new NetworkServiceImpl();
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

        response = new Response();

        List<Catalogue> catalogues = networkService.getCataloguesList();

        StringBuilder messageCatalogues = new StringBuilder();

        for (Catalogue catalogue : catalogues) {
            messageCatalogues.append(catalogue.getKey());
            messageCatalogues.append(" ");
            messageCatalogues.append(catalogue.getTitle());
            messageCatalogues.append("\n");
        }

        messageCatalogues.append("\n" + constants.getString("filter_catalogues_helper"));

        response.setMessage(messageCatalogues.toString());

        user.setState(CommandEnum.CATALOGUES);

        return response;
    }
}
