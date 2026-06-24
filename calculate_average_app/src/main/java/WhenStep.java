import helpers.DataGenerator;
import helpers.ResultAnalyser;
import helpers.ScriptRunner;

public class WhenStep {

    private final DataGenerator dataGenerator;
    private final ScriptRunner scriptRunner;
    private final ResultAnalyser resultAnalyser;

    private long executionTime;

    public WhenStep(DataGenerator dataGenerator, ScriptRunner scriptRunner, ResultAnalyser resultAnalyser){
        this.dataGenerator = dataGenerator;
        this.scriptRunner = scriptRunner;
        this.resultAnalyser = resultAnalyser;
    }

    public WhenStep executeScript(){
        scriptRunner.executeScript();
        return this;
    }

    public WhenStep executeScript(String filePath){
        scriptRunner.executeScript(filePath);
        return this;
    }

    public WhenStep measureExecutionTime(){
        long start = System.currentTimeMillis();
        scriptRunner.executeScript();
        executionTime = System.currentTimeMillis() - start;
        return this;
    }

    public ThenStep then(){
        return new ThenStep(scriptRunner, resultAnalyser, executionTime);
    }

}
