package steps;

import utils.DataGenerator;
import utils.ResultAnalyser;
import utils.ScriptRunner;

public class GivenStep {

    private final DataGenerator dataGenerator;
    private final ScriptRunner scriptRunner;
    private final ResultAnalyser resultAnalyser;

    public GivenStep(DataGenerator dataGenerator, ScriptRunner scriptRunner, ResultAnalyser resultAnalyser){
        this.dataGenerator=dataGenerator;
        this.scriptRunner=scriptRunner;
        this.resultAnalyser=resultAnalyser;
    }

    public GivenStep withUsers(String... users){
        dataGenerator.withUsers(users);
        return this;
    }

    public GivenStep withJson(String json){
        dataGenerator.createJson(json);
        return this;
    }

    public GivenStep createCsv(){
        dataGenerator.createCsv();
        return this;
    }

    public GivenStep withEmail(String login,String email){
        dataGenerator.withEmail(login,email);
        return this;
    }

    public GivenStep withoutUsersFile(){
        dataGenerator.withoutUsersFile();
        return this;
    }

    public WhenStep when(){
        return new WhenStep(
                dataGenerator,
                scriptRunner,
                resultAnalyser
        );
    }
}
