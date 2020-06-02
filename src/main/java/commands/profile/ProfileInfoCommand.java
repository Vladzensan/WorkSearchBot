package commands.profile;

import commands.Command;
import network.NetworkServiceImpl;
import network.NetworkUserService;
import responses.Response;
import user.User;
import user.UserInfo;

import javax.ws.rs.NotAuthorizedException;
import java.util.Date;
import java.util.ResourceBundle;

public class ProfileInfoCommand extends Command {
    public ProfileInfoCommand(String s, User user) {
        super(s, user);
    }

    @Override
    public Response execute() {
        ResourceBundle constants = localeService.getMessageBundle(user.getCurrentLocale());
        NetworkUserService network = new NetworkServiceImpl();
        UserInfo userInfo;
        try {
            userInfo = network.loadUser(user.getChatId());
        } catch (NotAuthorizedException e) {
            response = new Response();
            response.setMessage(constants.getString("login_require_msg"));
            return response;
        }

        Response response = new Response();

        response.setMessage("-" + constants.getString("name_property") + ": " + userInfo.getName() + "\n-" +
                constants.getString("email_property") + ": " + userInfo.getEmail() + "\n-" +
                constants.getString("phone_property") + ": " + userInfo.getPhoneNumber() + "\n-" +
                constants.getString("hr_property") + ": " + (userInfo.isHr() ? "+" : "-") + "\n-" +
                constants.getString("regdate_property") + ": " + new Date(userInfo.getRegistrationDate() * 1000L).toString() + "\n-" +
                constants.getString("photo_property") + ": " + userInfo.getPhotoPath());

        return response;
    }
}
