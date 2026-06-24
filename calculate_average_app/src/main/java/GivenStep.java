import helpers.DataGenerator;
import helpers.ResultAnalyser;
import helpers.ScriptRunner;

public class GivenStep {

    private final DataGenerator dataGenerator;
    private final ScriptRunner scriptRunner;
    private final ResultAnalyser resultAnalyser;

    public GivenStep(DataGenerator dataGenerator, ScriptRunner scriptRunner, ResultAnalyser resultAnalyser){
        this.dataGenerator = dataGenerator;
        this.scriptRunner = scriptRunner;
        this.resultAnalyser = resultAnalyser;
    }

    public GivenStep generateRecords(int num){
        dataGenerator.generateRecords(num);
        return this;
    }

    public GivenStep generateLargeFileOfRecords(int num){
        dataGenerator.generateLargeFile(num);
        return this;
    }

    public GivenStep withRecord(String user, String date, String category, double amount){
        dataGenerator.withRecord(user, date, category, amount);
        return this;
    }

    public WhenStep when(){
        return new WhenStep(dataGenerator, scriptRunner, resultAnalyser);
    }

    public GivenStep generateWithInvalidSeparator(){
        dataGenerator.withInvalidSeparator();
        return this;
    }
}
