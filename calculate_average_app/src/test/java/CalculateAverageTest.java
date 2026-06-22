import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalculateAverageTest {

    @Test
    @DisplayName("2 разных юзера")
    public void calculatingAverageWithTwoUsersAndOneRecordWorksCorrectly() {
        new TestScenario()
                .given()
                .generateRecords(0)
                .withRecord("user1", "2025-01-01", "transport", 800.0)
                .withRecord("user2", "2025-01-02", "food", 100.0)
                .when().executeScript()
                .then()
                .containsRecord("user1", "ALL",  800.0)
                .containsRecord("user2", "ALL",  100.0);
    }

    @Test
    @DisplayName("Невалидная дата")
    public void calculatingAverageWithInvalidDataLeadsToInvalidDataError() {
        new TestScenario()
                .given()
                .generateRecords(0)
                .withRecord("user1", "invalid-date", "transport", 800.0)
                .when().executeScript()
                .then()
                .containsError("❌ Обнаружена неверная дата. Завершаем.");
    }

    @Test
    @DisplayName("Несуществующий файл")
    public void calculatingAverageWithNonExistingInputFileLeadsToFileNotFoundError() {
        String nonExistingFile = "file.txt";
        new TestScenario()
                .given()
                .generateRecords(1)
                .when().executeScript(nonExistingFile)
                .containsError("Файл не найден: " + nonExistingFile);
    }
}
