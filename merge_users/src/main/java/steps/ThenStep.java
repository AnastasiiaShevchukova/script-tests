package steps;

import utils.ResultAnalyser;
import utils.ScriptRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThenStep {

    private final ScriptRunner scriptRunner;
    private final ResultAnalyser resultAnalyser;

    public ThenStep(ScriptRunner scriptRunner, ResultAnalyser resultAnalyser){
        this.scriptRunner=scriptRunner;
        this.resultAnalyser=resultAnalyser;
    }

    public ThenStep containsUser(String login,String name,String email){
        resultAnalyser.containsUser(login,name,email);
        return this;
    }


    public ThenStep doesNotContainUser(String login){
        resultAnalyser.doesNotContainUser(login);
        return this;
    }


    public ThenStep containsHeader(){
        resultAnalyser.containsHeader();
        return this;
    }


    public ThenStep exitCode(int code){
        assertEquals(code,scriptRunner.getExitCode());
        return this;
    }

    public ThenStep containsError(String error){
        assertTrue(scriptRunner.getErrorMessage().contains(error));
        return this;
    }

}
