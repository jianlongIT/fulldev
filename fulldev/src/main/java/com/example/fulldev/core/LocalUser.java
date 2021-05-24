package com.example.fulldev.core;

import com.example.fulldev.model.User;
import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

public class LocalUser {
    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();


    public static void clear() {
        LocalUser.threadLocal.remove();
    }

    public static void setUser(User user, Integer scope) {
        Map<String, Object> map = new HashedMap();
        map.put("scope", scope);
        map.put("user", user);
        LocalUser.threadLocal.set(map);
    }

    public static User getUser() {
        return (User) LocalUser.threadLocal.get().get("user");
    }

    public static Integer getScope() {
        return (Integer) LocalUser.threadLocal.get().get("scope");
    }
}
