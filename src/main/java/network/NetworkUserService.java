package network;

import user.UserInfo;

public interface NetworkUserService {
    UserInfo loadUser(long chatId);
}
