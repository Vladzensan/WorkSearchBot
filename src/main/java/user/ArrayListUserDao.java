package user;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ArrayListUserDao implements UserDao {
    private List<User> users;

    private ArrayListUserDao() {
        users = new ArrayList<>();
    }

    public static ArrayListUserDao getInstance() {
        return UserDaoHolder.instance;
    }

    @Override
    public User getUserById(long chatId) {
        return users.stream()
                .filter(user -> user.getChatId() == chatId)
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("User with id " + chatId + " not found"));
    }

    @Override
    public void addUser(User user) {
        Optional<User> userOptional = users.stream()
                .filter(user1 -> user1.equals(user))
                .findAny();

        if (!userOptional.isPresent()) {
            users.add(user);
        }

    }

    @Override
    public boolean containsChatId(long chatId) {
        return users.stream()
                .anyMatch(user -> user.getChatId() == chatId);
    }

    private static class UserDaoHolder {
        static final ArrayListUserDao instance = new ArrayListUserDao();
    }

}
