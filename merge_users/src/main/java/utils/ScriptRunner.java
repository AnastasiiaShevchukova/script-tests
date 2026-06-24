package utils;

import java.nio.file.Paths;

public class ScriptRunner {

    private static final String SCRIPT = Paths.get(System.getProperty("user.dir"), "script", "merge_users.sh").toString();
    private static final String SCRIPT_DIR = Paths.get(System.getProperty("user.dir"), "script").toString();

    private int exitCode;
    private String output;


    public void executeScript() {
        try {
            ProcessBuilder builder =
                    new ProcessBuilder(
                            "bash",
                            SCRIPT,
                            Paths.get(SCRIPT_DIR, "users.txt").toString(),
                            Paths.get(SCRIPT_DIR, "users.json").toString(),
                            Paths.get(SCRIPT_DIR, "users.csv").toString()
                    );

            builder.redirectErrorStream(true);
            Process process = builder.start();
            output = new String(process.getInputStream().readAllBytes());
            exitCode = process.waitFor();

            output = new String(process.getInputStream().readAllBytes());

            System.out.println(output);

            System.out.println(
                    Paths.get("full_users.csv").toAbsolutePath()
            );

            System.out.println("EXIT CODE = " + exitCode);
            System.out.println("OUTPUT = ");
            System.out.println(output);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public int getExitCode() {
        return exitCode;
    }

    public String getOutput() {
        return output;
    }

    public String getErrorMessage(){
        return output;
    }
}
