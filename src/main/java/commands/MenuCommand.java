package commands;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import responses.Response;
import user.User;

import java.util.*;

public class MenuCommand extends Command {
    private static final List<CommandEnum> menuCommandEnums = new ArrayList<>(Arrays.asList(CommandEnum.PROFILE, CommandEnum.AUTH,
            CommandEnum.SEARCH, CommandEnum.FAVORITES, CommandEnum.LANGUAGE));


    public MenuCommand(String s, User user) {
        super(s, user);
    }

    private static List<List<InlineKeyboardButton>> mapButtonsByTwo(List<CommandEnum> commandEnums, Locale locale) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();


        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        int i = 0;
        for (CommandEnum commandEnum
                : commandEnums) {

            if (i % 2 == 0 && i > 0) {
                rowsInline.add(rowInline);
                rowInline = new ArrayList<>();
            }

            rowInline.add(new InlineKeyboardButton()
                    .setText(commandEnum.getCaption(locale))
                    .setCallbackData(commandEnum.getCommand())
                    .setSwitchInlineQueryCurrentChat(commandEnum.getCommand()));
            i++;
        }

        if (!rowInline.isEmpty()) {
            rowsInline.add(rowInline);
        }

        return rowsInline;
    }

    @Override
    public Response execute() {

        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());

        response = new Response();

        response.setMessage(constants.getString("welcome_message"));

        response.getMarkup().setKeyboard(mapButtonsByTwo(menuCommandEnums, user.getCurrentLocale()));

        return response;
    }

    @Override
    public Command create(String s, User user) {
        return new MenuCommand(s, user);
    }
}
