import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MergeUsersTest {

    @Test
    @DisplayName("Все пользователи с полными данными объединяются")
    public void usersWithFullDataShouldBeMerged() {
        new TestScenario()
                .given()
                .withUsers("alice")
                .withJson("""
                    {
                      "alice":"Alice Smith"
                    }
                    """)
                .createCsv()
                .withEmail("alice","alice@example.com")
                .when()
                .executeScript()
                .then()
                .exitCode(0)
                .containsUser("alice","Alice Smith","alice@example.com");
    }

    @Test
    @DisplayName("Один пользователь")
    public void singleUserShouldBeMerged() {
        new TestScenario()
                .given()
                .withUsers("alice")
                .withJson("""
                    {
                      "alice":"Alice Smith"
                    }
                    """)
                .createCsv()
                .withEmail("alice","alice@example.com")
                .when()
                .executeScript()
                .then()
                .containsUser("alice","Alice Smith","alice@example.com");
    }

    @Test
    @DisplayName("Несколько пользователей")
    public void multipleUsersShouldBeMerged() {
        new TestScenario()
                .given()
                .withUsers(
                        "alice",
                        "bob",
                        "carol"
                )
                .withJson("""
                    {
                      "alice":"Alice Smith",
                      "bob":"Bob Johnson",
                      "carol":"Carol Lee"
                    }
                    """)
                .createCsv()
                .withEmail("alice","alice@example.com")
                .withEmail("bob","bob@example.com")
                .withEmail("carol","carol@example.com")
                .when()
                .executeScript()
                .then()
                .containsUser("alice","Alice Smith","alice@example.com")
                .containsUser("bob","Bob Johnson","bob@example.com")
                .containsUser("carol","Carol Lee","carol@example.com");
    }

    @Test
    @DisplayName("Пользователь есть в txt, но нет в json")
    public void userWithoutJsonShouldBeIgnored() {
        new TestScenario()
                .given()
                .withUsers("alice")
                .withJson("{}")
                .createCsv()
                .withEmail("alice","alice@example.com")
                .when()
                .executeScript()
                .then()
                .exitCode(0)
                .doesNotContainUser("alice");
    }

    @Test
    @DisplayName("Пользователь есть в txt, но нет в csv")
    public void userWithoutCsvShouldBeIgnored() {
        new TestScenario()
                .given()
                .withUsers("alice")
                .withJson("""
                    {
                      "alice":"Alice Smith"
                    }
                    """)
                .createCsv()
                .when()
                .executeScript()
                .then()
                .doesNotContainUser("alice");
    }

    @Test
    @DisplayName("Пустой users.txt")
    public void emptyUsersFileShouldCreateEmptyResult() {
        new TestScenario()
                .given()
                .withUsers()
                .withJson("""
                    {
                      "alice":"Alice Smith"
                    }
                    """)
                .createCsv()
                .withEmail("alice","alice@example.com")
                .when()
                .executeScript()
                .then()
                .containsHeader();
    }

    @Test
    @DisplayName("Пустой users.json")
    public void emptyJsonShouldCreateEmptyResult() {
        new TestScenario()
                .given()
                .withUsers("alice")
                .withJson("{}")
                .createCsv()
                .withEmail("alice","alice@example.com")
                .when()
                .executeScript()
                .then()
                .doesNotContainUser("alice");
    }


    @Test
    @DisplayName("Пустой users.csv")
    public void emptyCsvShouldCreateEmptyResult() {
        new TestScenario()
                .given()
                .withUsers("alice")
                .withJson("""
                    {
                      "alice":"Alice Smith"
                    }
                    """)
                .createCsv()
                .when()
                .executeScript()
                .then()
                .doesNotContainUser("alice");
    }


    @Test
    @DisplayName("Отсутствует users.txt")
    public void missingUsersFileShouldReturnError() {
        new TestScenario()
                .given()
                .withoutUsersFile()
                .withJson("""
                    {
                      "alice":"Alice Smith"
                    }
                    """)
                .createCsv()
                .withEmail("alice","alice@example.com")
                .when()
                .executeScript()
                .then()
                .exitCode(1);
    }

    @Test
    @DisplayName("Проверка формата full_users.csv")
    public void outputFileShouldHaveCorrectFormat() {
        new TestScenario()
                .given()
                .withUsers("alice")
                .withJson("""
                    {
                      "alice":"Alice Smith"
                    }
                    """)
                .createCsv()
                .withEmail("alice","alice@example.com")
                .when()
                .executeScript()
                .then()
                .containsHeader()
                .containsUser(
                        "alice",
                        "Alice Smith",
                        "alice@example.com"
                );
    }

    @Test
    @DisplayName("Дубликаты логинов в CSV")
    public void duplicateLoginsInCsvShouldUseLastEmail() {
        new TestScenario()
                .given()
                .withUsers("alice")
                .withJson("""
                {
                  "alice":"Alice Smith"
                }
                """)
                .createCsv()
                .withEmail("alice","old@example.com")
                .withEmail("alice","new@example.com")
                .when()
                .executeScript()
                .then()
                .containsUser(
                        "alice",
                        "Alice Smith",
                        "new@example.com"
                );
    }

    @Test
    @DisplayName("Проверка формата full_users.csv")
    public void fullUsersCsvShouldHaveCorrectHeader() {
        new TestScenario()
                .given()
                .withUsers("alice")
                .withJson("""
                {
                  "alice":"Alice Smith"
                }
                """)
                .createCsv()
                .withEmail("alice","alice@example.com")
                .when()
                .executeScript()
                .then()
                .containsHeader()
                .containsUser(
                        "alice",
                        "Alice Smith",
                        "alice@example.com"
                );
    }
}
