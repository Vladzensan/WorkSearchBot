package commands.profile;

import commands.Command;
import responses.Response;
import user.User;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageCommand extends Command {
    public LanguageCommand(String s, User user) {
        super(s, user);
    }

    @Override
    public Response execute() {
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());
        response = new Response();

        String[] tokens = s.split("\\s+");
        if (tokens.length == 1) {
            response.setMessage(constants.getString("language_info"));
            return response;
        }

        String language = tokens[1].toLowerCase();
        if (language.equals("en") || language.equals("ru")) {
            user.setCurrentLocale(new Locale(language));
            constants = localeService.getMessageBundle(user.getCurrentLocale());

            response.setMessage(constants.getString("language_success"));

        } else {
            response.setMessage(constants.getString("language_failure"));
        }

        return response;
    }

}
