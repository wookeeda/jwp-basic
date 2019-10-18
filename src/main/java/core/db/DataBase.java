package core.db;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import next.model.User;

public class DataBase {
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static User updateUser(User user){
        User target = users.get(user.getUserId());
        target.setPassword(user.getPassword());
        target.setName(user.getName());
        target.setEmail(user.getEmail());
        return target;
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
