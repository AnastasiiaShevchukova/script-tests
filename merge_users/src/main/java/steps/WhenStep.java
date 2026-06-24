package steps;

import utils.DataGenerator;
import utils.ResultAnalyser;
import utils.ScriptRunner;

public class WhenStep {

    private final ScriptRunner scriptRunner;
    private final ResultAnalyser resultAnalyser;

    public WhenStep(DataGenerator dataGenerator, ScriptRunner scriptRunner, ResultAnalyser resultAnalyser){
        this.scriptRunner=scriptRunner;
        this.resultAnalyser=resultAnalyser;
    }

    public WhenStep executeScript(){
        scriptRunner.executeScript();
        return this;
    }

    public ThenStep then(){
        return new ThenStep(scriptRunner,resultAnalyser);
    }
}
