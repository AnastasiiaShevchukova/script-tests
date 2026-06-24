package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.HashMap;

public class DataGenerator {

    private static final String DIR_PATH = Paths.get(System.getProperty("user.dir"), "script").toString();

    private static final String USERS_FILE = Paths.get(DIR_PATH, "users.txt").toString();
    private static final String JSON_FILE = Paths.get(DIR_PATH, "users.json").toString();
    private static final String CSV_FILE = Paths.get(DIR_PATH, "users.csv").toString();
    private static final String RESULT_FILE = Paths.get(System.getProperty("user.dir"), "full_users.csv").toString();


    private final Map<String, String> jsonUsers = new HashMap<>();

    public void resetFiles() {
        try {
            Files.deleteIfExists(Path.of(USERS_FILE));
            Files.deleteIfExists(Path.of(JSON_FILE));
            Files.deleteIfExists(Path.of(CSV_FILE));
            Files.deleteIfExists(Path.of(RESULT_FILE));

            jsonUsers.clear();

            System.out.println("FILES RESET");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void withUsers(String... users) {
        try (FileWriter writer = new FileWriter(USERS_FILE)) {
            for (String user : users) {
                writer.write(user + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createJson(String content) {
        write(JSON_FILE, content);
    }

    public void createCsv() {
        write(CSV_FILE, "login,email\n");
    }

    public void withEmail(String login, String email) {
        try (FileWriter writer = new FileWriter(CSV_FILE, true)) {
            writer.write(login + "," + email + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void write(String path, String data) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void withoutUsersFile() {
        delete(USERS_FILE);
    }

    public void withoutJsonFile() {
        delete(JSON_FILE);
    }

    public void withoutCsvFile() {
        delete(CSV_FILE);
    }

    private void delete(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}