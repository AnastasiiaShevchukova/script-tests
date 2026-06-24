package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class DataGenerator {
    private static final String DIR_PATH = Paths.get(System.getProperty("user.dir"), "script").toString();
    private static final String USERS_FILE = Paths.get(DIR_PATH, "users.txt").toString();
    private static final String LOGINS_FILE = Paths.get(DIR_PATH, "logins.csv").toString();
    private static final String BANNED_FILE = Paths.get(DIR_PATH, "banned.json").toString();

    private static final String RESULT_FILE = Paths.get(DIR_PATH, "active_users.csv").toString();

    private void createDir() {
        File directory = new File(DIR_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private void writeData(String filePath, String data, boolean append) {
        try(FileWriter writer = new FileWriter(filePath, append)) {
            writer.write(data);
            System.out.println("Data was written to file " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error while writing data: " + e.getMessage());
        }
    }

    public void createUsersFile(String... users) {
        createDir();
        StringBuilder builder = new StringBuilder();
        Arrays.stream(users)
                .forEach(user -> builder.append(user).append("\n"));
        writeData(USERS_FILE, builder.toString(), false);
    }

    public void createEmptyUsersFile() {
        createDir();
        writeData(USERS_FILE, "", false);
    }

    public void createLoginsFile() {
        createDir();
        writeData(LOGINS_FILE, "login,last_login\n", false);
    }

    public void addLogin(String user, String date) {
        String login = String.format("%s,%s\n", user, date);
        writeData(LOGINS_FILE, login, true);

    }

    public void createBannedFile(String... users) {
        createDir();
        StringBuilder json = new StringBuilder();
        json.append("[\n");
        for (int i = 0; i < users.length; i++) {
            json.append("\"").append(users[i]).append("\"");

            if (i < users.length - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append("]");
        writeData(
                BANNED_FILE,
                json.toString(),
                false
        );
    }

    public void createInvalidJson() {
        createDir();
        writeData(BANNED_FILE, "{invalid json", false);
    }
    public void deleteUsersFile() {
        new File(USERS_FILE).delete();
    }

    public void deleteLoginsFile() {
        new File(LOGINS_FILE).delete();
    }

    public void deleteBannedFile() {
        new File(BANNED_FILE).delete();
    }

    public void resetFiles() {
        try {
            Files.deleteIfExists(Path.of(USERS_FILE));
            Files.deleteIfExists(Path.of(LOGINS_FILE));
            Files.deleteIfExists(Path.of(BANNED_FILE));
            Files.deleteIfExists(Path.of(RESULT_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initFiles() {
        try {
            Files.writeString(Path.of(USERS_FILE), "");
            Files.writeString(Path.of(LOGINS_FILE), "login,last_login\n");
            Files.writeString(Path.of(BANNED_FILE), "[]");
            Files.writeString(Path.of(RESULT_FILE), "login,last_login\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}