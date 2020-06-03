package commands;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Utilities {
    private Utilities() {
    }

    public static List<List<InlineKeyboardButton>> mapButtonsByTwo(List<CommandEnum> commandEnums, Locale locale) {
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

}
