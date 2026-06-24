import steps.GivenStep;
import utils.DataGenerator;
import utils.ResultAnalyser;
import utils.ScriptRunner;


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
        dataGenerator.resetFiles();
        dataGenerator.initFiles();
        return new GivenStep(dataGenerator, scriptRunner, resultAnalyser);
    }

}
