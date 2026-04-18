package model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserManager {

    private static final UserManager INSTANCE = new UserManager();

    private User currentUser;

    private UserManager() {}

    public static UserManager getInstance() {
        return INSTANCE;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    private Path userFile(String id) {
        String safeId = (id == null ? "user" : id.trim()).replaceAll("\\s+", "_");
        return Paths.get("users", safeId + ".dat");
    }

    public User loadOrCreateUser(String username) {
        String id = (username == null ? "user" : username.trim());
        Path file = userFile(id);

        if (Files.exists(file)) {
            try (ObjectInputStream ois =
                         new ObjectInputStream(Files.newInputStream(file))) {

                User u = (User) ois.readObject();

                if (u.getId() == null || u.getId().trim().isEmpty()) {
                    u.setId(id);
                }

                this.currentUser = u;
                return u;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        User u = new User(id);
        u.setId(id);
        this.currentUser = u;
        save();
        return u;
    }

    public void save() {
        if (currentUser == null) return;

        try {
            Path dir = Paths.get("users");
            Files.createDirectories(dir);

            Path file = userFile(currentUser.getId());

            try (ObjectOutputStream oos =
                         new ObjectOutputStream(Files.newOutputStream(file))) {
                oos.writeObject(currentUser);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        currentUser = null;
    }
}
