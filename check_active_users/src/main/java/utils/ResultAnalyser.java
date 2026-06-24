package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ResultAnalyser {

    private static final String RESULT_FILE = Paths.get(System.getProperty("user.dir"), "active_users.csv").toString();
    private List<String[]> records = new LinkedList<>();

    public void containsUser(String login, String lastlogin) {
        if (records.isEmpty()) {
            readRecords();
        }

        String[] userRecord = records.stream()
                .filter(record -> record[0].equals(login))
                .findFirst()
                .orElseThrow();

        assertEquals(login, userRecord[0]);
        assertEquals(lastlogin, userRecord[1]);
    }

    public void containsUser(String login) {
        if(records.isEmpty()){readRecords();}
        boolean exists = records.stream()
                .anyMatch(record -> record[0].equals(login));

        assertTrue(exists);
    }


    public void doesNotContainUser(String login) {
        if(records.isEmpty()){readRecords();}
        boolean exists = records.stream()
                .anyMatch(record -> record[0].equals(login));
        assertFalse(exists);
    }


    private void readRecords() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(RESULT_FILE));
            for(int i = 1; i < lines.size(); i++){
                records.add(lines.get(i).split(","));
            }

        } catch(IOException e){
            throw new RuntimeException("Не удалось прочитать active_users.csv", e);
        }
    }

    public void containsHeader(String header) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(RESULT_FILE));
            assertEquals(header, lines.get(0));

        } catch(IOException e){
            throw new RuntimeException("Не удалось прочитать active_users.csv", e);
        }
    }


    public void assertError(String expectedError, String actualError) {
        assertTrue(actualError.contains(expectedError));
    }


    public void assertExitCode(int expectedCode, int actualCode) {
        assertEquals(expectedCode, actualCode);
    }
}
