import helpers.ResultAnalyser;
import helpers.ScriptRunner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThenStep {

    private final ScriptRunner scriptRunner;
    private final ResultAnalyser resultAnalyser;

    private final long executionTime;


    public ThenStep(ScriptRunner scriptRunner, ResultAnalyser resultAnalyser, long executionTime){
        this.scriptRunner = scriptRunner;
        this.resultAnalyser = resultAnalyser;
        this.executionTime = executionTime;
    }

    public ThenStep containsRecord(String user, String month, double average){
        resultAnalyser.containsRecord(
                user,
                month,
                average
        );
        return this;
    }

    public ThenStep containsError(String error){
        assertEquals(error, scriptRunner.getErrorMessage().replace("\n",""));
        return this;
    }

    public ThenStep exitCode(int code){
        assertEquals(code, scriptRunner.getExitCode());
        return this;
    }

    public ThenStep executionTimeLessThan(long seconds){
        assertTrue(executionTime <= seconds * 1000, "Время выполнения больше допустимого");
        return this;
    }

    public ThenStep executionTimeMoreThan(long seconds){
        assertTrue(executionTime >= seconds * 1000, "Время выполнения меньше ожидаемого");
        return this;
    }

    public ThenStep containsHeader() {
        resultAnalyser.containsHeader();
        return this;
    }

}
