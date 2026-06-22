import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalculateAveragePerfomanceTest {

    @Test
    @DisplayName("Обработка 10000 строк меньше 1 секунды")
    public void calculateAverageWith10kRecordsWorksFast() {
        new TestScenario()
                .given()
                .generateLargeFileOfRecords(10000)
                .when()
                .measureExecutionTime()
                .then()
                .exitCode(0)
                .executionTimeLessThan(1);
    }

    @Test
    @DisplayName("Обработка 100000 строк меньше 5 секунд")
    public void calculateAverageWith100kRecordsWorksFast() {
        new TestScenario()
                .given()
                .generateLargeFileOfRecords(100000)
                .when()
                .measureExecutionTime()
                .then()
                .exitCode(0)
                .executionTimeLessThan(5);

    }

    @Test
    @DisplayName("Обработка 1 миллиона строк меньше 20 секунд")
    public void calculateAverageWith1MRecordsWorksFast() {
        new TestScenario()
                .given()
                .generateLargeFileOfRecords(1000000)
                .when()
                .measureExecutionTime()
                .then()
                .exitCode(0)
                .executionTimeLessThan(20);

    }

    @Test
    @DisplayName("Большой файл вызывает задержку")
    public void largeFileCreatesExpectedDelay() {
        new TestScenario()
                .given()
                .generateLargeFileOfRecords(100001)
                .when()
                .measureExecutionTime()
                .then()
                .executionTimeMoreThan(6);
    }
}
