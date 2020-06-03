package commands.profile;

import commands.Command;
import commands.CommandEnum;
import commands.Utilities;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import responses.Response;
import user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ProfileCommand extends Command {
    public ProfileCommand(String s, User user) {
        super(s, user);
    }

    @Override
    public Response execute() {
        final List<CommandEnum> menuCommands = new ArrayList<>(Arrays.asList(CommandEnum.PROFILE_INFO, CommandEnum.RESUMES, CommandEnum.CREATE_RESUME, CommandEnum.BACK_MENU));
        Response response = new Response();
        Locale locale = user.getCurrentLocale();
        response.setMessage(CommandEnum.PROFILE_INFO.getCaption(locale));
        response.setMarkup(new InlineKeyboardMarkup(Utilities.mapButtonsByTwo(menuCommands, locale)));

        int editMessageId = user.getCurrentUpdate().getCallbackQuery().getMessage().getMessageId();
        response.setEditMessageId(editMessageId);
        return response;
    }
}
