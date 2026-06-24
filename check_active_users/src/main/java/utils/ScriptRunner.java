package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class ScriptRunner {
    private static final String USERS_INPUT_FILE = Paths.get(System.getProperty("user.dir"), "script", "users.txt").toString();
    private static final String LOGINS_INPUT_FILE = Paths.get(System.getProperty("user.dir"), "script", "logins.csv").toString();
    private static final String BANNED_INPUT_FILE = Paths.get(System.getProperty("user.dir"), "script", "banned.json").toString();

    private static final String SCRIPT_PATH = Paths.get(System.getProperty("user.dir"), "script", "check_active_users.sh").toString();

    private String outputMessage;
    private int exitCode;

    public void executeScript() {
        runScript(USERS_INPUT_FILE, LOGINS_INPUT_FILE, BANNED_INPUT_FILE);
    }

    public void executeScript(String fileUsersPath, String fileLoginsPath, String fileBannedPath) {
        runScript(fileUsersPath, fileLoginsPath, fileBannedPath);
    }

    private void runScript(String fileUsersPath, String fileLoginsPath, String fileBannedPath) {
        System.out.println("Running script..");
        ProcessBuilder processBuilder = new ProcessBuilder("bash", SCRIPT_PATH, fileUsersPath, fileLoginsPath, fileBannedPath);
        try {
            Process process = processBuilder.start();
            StringBuilder result = new StringBuilder();
            BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            while ((line = output.readLine()) != null) {result.append(line).append("\n");}
            while ((line = error.readLine()) != null) {result.append(line).append("\n");}
            exitCode = process.waitFor();
            outputMessage = result.toString();
        } catch (IOException | InterruptedException e) {
            outputMessage = e.getMessage();
            exitCode = 1;
        }
    }

    public int getExitCode() {
        System.out.println(outputMessage);
        return exitCode;
    }

    public String getOutputMessage() {
        return outputMessage;
    }
}
