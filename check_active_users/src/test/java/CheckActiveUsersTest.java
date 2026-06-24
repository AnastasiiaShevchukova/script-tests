import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CheckActiveUsersTest {

    @Test
    @DisplayName("Один активный пользователь")
    public void activeUserShouldBeAddedToResult() {
        new TestScenario()
                .given()
                .withUsers("alice")
                .createLoginsFile()
                .withLogin("alice", "2026-06-20")
                .withBannedUsers()
                .when()
                .executeScript()
                .then()
                .exitCode(0)
                .containsUser("alice", "2026-06-20");
    }


    @Test
    @DisplayName("Несколько активных пользователей")
    public void multipleActiveUsersShouldBeAddedToResult() {
        new TestScenario()
                .given()
                .withUsers(
                        "alice",
                        "bob",
                        "carol"
                )
                .withLogin("alice", "2026-06-20")
                .withLogin("bob", "2026-06-10")
                .withLogin("carol", "2026-06-01")
                .withBannedUsers()
                .when()
                .executeScript()
                .then()
                .containsUser("alice", "2026-06-20")
                .containsUser("bob", "2026-06-10")
                .containsUser("carol", "2026-06-01");
    }


    @Test
    @DisplayName("Заблокированный пользователь исключается")
    public void bannedUserShouldNotBeIncluded() {
        new TestScenario()
                .given()
                .withUsers("alice", "bob")
                .withLogin("alice", "2026-06-20")
                .withLogin("bob", "2025-06-20")
                .withBannedUsers("bob")
                .when()
                .executeScript()
                .then()
                .containsUser("alice", "2026-06-20")
                .doesNotContainUser("bob");
    }


    @Test
    @DisplayName("Пользователь старше 30 дней исключается")
    public void oldLoginShouldNotBeIncluded() {
        new TestScenario()
                .given()
                .withUsers("alice")
                .withLogin("alice", "2026-01-01")
                .withBannedUsers()
                .when()
                .executeScript()
                .then()
                .doesNotContainUser("alice");
    }


    @Test
    @DisplayName("Пользователь с входом 30 дней назад считается активным")
    public void loginExactly30DaysAgoShouldBeActive() {
        new TestScenario()
                .given()
                .withUsers("alice")
                .withLogin("alice", "TODAY-30")
                .withBannedUsers()
                .when()
                .executeScript()
                .then()
                .containsUser("alice");
    }


    @Test
    @DisplayName("Пользователь отсутствует в logins.csv")
    public void userWithoutLoginShouldBeIgnored() {
        new TestScenario()
                .given()
                .withUsers("alice", "bob")
                .withLogin("alice", "2026-06-20")
                .withBannedUsers()
                .when()
                .executeScript()
                .then()
                .containsUser("alice")
                .doesNotContainUser("bob");
    }


    @Test
    @DisplayName("Пустой users.txt создает пустой результат")
    public void emptyUsersFileShouldCreateEmptyResult() {
        new TestScenario()
                .given()
                .withEmptyUsers()
                .withLogin("alice", "2025-06-20")
                .withBannedUsers()
                .when()
                .executeScript()
                .then()
                .containsHeader("login,last_login");
    }


    @Test
    @DisplayName("Пустой banned.json не исключает пользователей")
    public void emptyBanListShouldAllowUsers() {
        new TestScenario()
                .given()
                .withUsers("alice", "bob")
                .withLogin("alice", "2026-06-20")
                .withLogin("bob", "2026-06-20")
                .withBannedUsers()
                .when()
                .executeScript()
                .then()
                .containsUser("alice")
                .containsUser("bob");
    }


    @Test
    @DisplayName("Некорректная дата")
    public void invalidDateShouldIgnoreUser() {
        new TestScenario()
                .given()
                .withUsers("alice")
                .withLogin("alice", "invalid-date")
                .withBannedUsers()
                .when()
                .executeScript()
                .then()
                .doesNotContainUser("alice");
    }


    @Test
    @DisplayName("Пустая дата последнего входа")
    public void emptyLastLoginShouldIgnoreUser() {
        new TestScenario()
                .given()
                .withUsers("alice")
                .withLogin("alice", "")
                .withBannedUsers()
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
                .when()
                .executeScript()
                .then()
                .exitCode(1)
                .containsError("❌ Один из входных файлов не найден.");
    }


    @Test
    @DisplayName("Отсутствует logins.csv")
    public void missingLoginsFileShouldReturnError() {
        new TestScenario()
                .given()
                .withUsers("alice")
                .withoutLoginsFile()
                .when()
                .executeScript()
                .then()
                .exitCode(1)
                .containsError("❌ Один из входных файлов не найден.");
    }


    @Test
    @DisplayName("Отсутствует banned.json")
    public void missingBannedFileShouldReturnError() {
        new TestScenario()
                .given()
                .withUsers("alice")
                .withLogin("alice", "2026-06-20")
                .withoutBannedFile()
                .when()
                .executeScript()
                .then()
                .exitCode(1)
                .containsError("❌ Один из входных файлов не найден.");
    }


    @Test
    @DisplayName("Некорректный JSON banned.json")
    public void invalidJsonShouldReturnError() {
        new TestScenario()
                .given()
                .withUsers("alice")
                .withLogin(
                        "alice",
                        "2026-06-20"
                )
                .withInvalidBannedJson()
                .when()
                .executeScript()
                .then()
                .containsError("parse error");
    }


    @Test
    @DisplayName("Проверка формата active_users.csv")
    public void outputFileShouldHaveCorrectFormat() {
        new TestScenario()
                .given()
                .withUsers("alice")
                .withLogin("alice", "2026-06-20")
                .withBannedUsers()
                .when()
                .executeScript()
                .then()
                .containsHeader("login,last_login")
                .containsUser("alice", "2026-06-20");
    }
}

