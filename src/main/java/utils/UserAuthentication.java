package utils;

import java.util.HashMap;
import java.util.Map;

public class UserAuthentication {
    private static final Map<String, String> users = new HashMap<>();
    private static final Map<String, String> userRoles = new HashMap<>();

    static {
        users.put("admin", "admin123");
        users.put("user", "user123");
        users.put("guest", "guest123");

        userRoles.put("admin", "ROLE_ADMIN");
        userRoles.put("user", "ROLE_USER");
        userRoles.put("guest", "ROLE_GUEST");
    }

    public static boolean authenticate(String username, String password) {
        String storedPassword = users.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }

    public static String getRole(String username) {
        return userRoles.get(username);
    }
}

