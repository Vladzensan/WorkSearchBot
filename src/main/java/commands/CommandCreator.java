package commands;

import user.User;

public interface CommandCreator {
    Command create(String s, User user);
}
