package steps;

import utils.ResultAnalyser;
import utils.ScriptRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThenStep {

    private final ScriptRunner scriptRunner;
    private final ResultAnalyser resultAnalyser;
    private final long executionTime;

    public ThenStep(ScriptRunner scriptRunner, ResultAnalyser resultAnalyser, long executionTime){
        this.scriptRunner = scriptRunner;
        this.resultAnalyser = resultAnalyser;
        this.executionTime = executionTime;
    }

    public ThenStep containsUser(String login, String lastLogin){
        resultAnalyser.containsUser(login, lastLogin);
        return this;
    }


    public ThenStep containsUser(String login){
        resultAnalyser.containsUser(login);
        return this;
    }


    public ThenStep doesNotContainUser(String login){
        resultAnalyser.doesNotContainUser(login);
        return this;
    }


    public ThenStep containsHeader(String header){
        resultAnalyser.containsHeader(header);
        return this;
    }


    public ThenStep containsError(String error){
        resultAnalyser.assertError(error, scriptRunner.getOutputMessage());
        return this;
    }


    public ThenStep exitCode(int code){
        assertEquals(code, scriptRunner.getExitCode());
        return this;
    }


}
