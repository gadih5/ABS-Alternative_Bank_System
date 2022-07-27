package bank;

import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class UserManager {
    private final Set<String> usersSet;

    public UserManager() {
        usersSet = new HashSet<>();
    }

    public synchronized void addUser(String username) {
        usersSet.add(username);
    }

    public boolean isUserExists(String username) {
        return usersSet.contains(username);
    }

    public ArrayList<HttpServletResponse> responses= new ArrayList<>();
}