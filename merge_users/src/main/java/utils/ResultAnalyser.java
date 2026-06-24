package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResultAnalyser {


    private static final String RESULT = Paths.get(System.getProperty("user.dir"), "full_users.csv").toString();

    public void containsUser(String login, String name, String email) {
        try {
            List<String> lines = Files.readAllLines(Path.of(RESULT));
            assertTrue(lines.contains(login + "," + name + "," + email));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void doesNotContainUser(String login) {
        try {
            List<String> lines = Files.readAllLines(Path.of(RESULT));
            assertTrue(lines.stream().noneMatch(x -> x.startsWith(login)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void containsHeader() {
        try {
            assertEquals("login,name,email", Files.readAllLines(Path.of(RESULT)).get(0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
