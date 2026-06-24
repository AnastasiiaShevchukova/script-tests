import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
                .then()
                .containsError("Файл не найден: " + nonExistingFile);
    }

    @Test
    @DisplayName("Один пользователь с несколькими тратами")
    public void calculatingAverageWithMultipleRecordsWorksCorrectly() {
        new TestScenario()
                .given()
                .generateRecords(0)
                .withRecord("user1", "2025-01-01", "food", 1000)
                .withRecord("user1", "2025-01-02", "transport", 500)
                .when()
                .executeScript()
                .then()
                .containsRecord("user1", "ALL", 750);
    }

    @Test
    @DisplayName("Среднее значение с дробными расходами")
    public void calculatingAverageWithDecimalAmountsWorksCorrectly(){
        new TestScenario()
                .given()
                .generateRecords(0)
                .withRecord("user1", "2025-01-01", "food", 100.50)
                .withRecord("user1", "2025-01-02", "food", 200.50)
                .when()
                .executeScript()
                .then()
                .containsRecord("user1", "ALL", 150.5);

    }

    @Test
    @DisplayName("Один пользователь")
    public void calculatingAverageWithOneUserWorksCorrectly(){
        new TestScenario()
                .given()
                .generateRecords(0)
                .withRecord("user1", "2025-01-01", "food", 500)
                .when()
                .executeScript()
                .then()
                .containsRecord("user1", "ALL", 500);

    }

    // Тест фиксирует баг скрипта!
    @Test
    @DisplayName("Неверный разделитель приводит к некорректному результату")
    public void calculatingAverageWithInvalidSeparatorCreatesWrongResult(){
        new TestScenario()
                .given()
                .generateWithInvalidSeparator()
                .when()
                .executeScript()
                .then()
                .containsRecord("user1;2025-01-01;food;100", "ALL", 0);
    }

    @Test
    @DisplayName("Отрицательная сумма")
    public void calculatingAverageWithNegativeAmount(){
        new TestScenario()
                .given()
                .generateRecords(0)
                .withRecord("user1", "2025-01-01", "food", -100)
                .when()
                .executeScript()
                .then()
                .containsRecord("user1", "ALL", -100);
    }

    @Test
    @DisplayName("Проверка заголовка результата")
    public void resultFileContainsCorrectHeader() {
        new TestScenario()
                .given()
                .generateRecords(1)
                .when()
                .executeScript()
                .then()
                .containsHeader();
    }


}
