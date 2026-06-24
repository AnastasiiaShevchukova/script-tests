package steps;

import utils.DataGenerator;
import utils.ResultAnalyser;
import utils.ScriptRunner;

public class GivenStep {

    private final DataGenerator dataGenerator;
    private final ScriptRunner scriptRunner;
    private final ResultAnalyser resultAnalyser;

    public GivenStep(DataGenerator dataGenerator, ScriptRunner scriptRunner, ResultAnalyser resultAnalyser){
        this.dataGenerator = dataGenerator;
        this.scriptRunner = scriptRunner;
        this.resultAnalyser = resultAnalyser;
    }


    public GivenStep withUsers(String... users){
        dataGenerator.createUsersFile(users);
        return this;
    }


    public GivenStep withEmptyUsers(){
        dataGenerator.createEmptyUsersFile();
        return this;
    }


    public GivenStep withLogin(String user, String date){
        dataGenerator.addLogin(user, date);
        return this;
    }


    public GivenStep createLoginsFile(){
        dataGenerator.createLoginsFile();
        return this;
    }


    public GivenStep withBannedUsers(String... users){
        dataGenerator.createBannedFile(users);
        return this;
    }


    public GivenStep withInvalidBannedJson(){
        dataGenerator.createInvalidJson();
        return this;
    }


    public GivenStep withoutUsersFile(){
        dataGenerator.deleteUsersFile();
        return this;
    }


    public GivenStep withoutLoginsFile(){
        dataGenerator.deleteLoginsFile();
        return this;
    }


    public GivenStep withoutBannedFile(){
        dataGenerator.deleteBannedFile();
        return this;
    }


    public WhenStep when(){
        return new WhenStep(dataGenerator, scriptRunner, resultAnalyser);
    }

    public GivenStep given() {
        dataGenerator.resetFiles();
        return new GivenStep(dataGenerator, scriptRunner, resultAnalyser);
    }

}
