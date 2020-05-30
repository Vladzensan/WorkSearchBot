package responses;

import user.User;

public interface CommandTask {
    Response execute(String query, User user);
}
