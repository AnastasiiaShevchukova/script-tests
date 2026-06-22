import helpers.DataGenerator;
import helpers.ResultAnalyser;
import helpers.ScriptRunner;


public class TestScenario {

    private final DataGenerator dataGenerator;
    private final ScriptRunner scriptRunner;
    private final ResultAnalyser resultAnalyser;


    public TestScenario() {
        this.dataGenerator = new DataGenerator();
        this.scriptRunner = new ScriptRunner();
        this.resultAnalyser = new ResultAnalyser();
    }


    public GivenStep given() {
        return new GivenStep(dataGenerator, scriptRunner, resultAnalyser);
    }

}