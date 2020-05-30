package user;

public interface UserDao {
    User getUserById(long chatId);

    void addUser(User user);

    boolean containsChatId(long chatId);
}
