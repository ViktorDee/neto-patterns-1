import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import entities.DeliveryData;
import utils.DataGenerator;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private static Faker faker;

    @BeforeAll
    static void setupAll() {
        faker = new Faker(new Locale("ru"));
    }

    private void printTestData(String name, String phone) {
        System.out.println(name + "\n" + phone + "\n");
    }

    private void printTestData(entities.DeliveryData deliveryData) {
        printTestData(deliveryData.getName(), deliveryData.getPhone());
    }

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldGenerateDeliveryDataUsingUtils() {
        DeliveryData data = DataGenerator.Delivery.generateData("ru");
        printTestData(data);
    }

    @Test
    void testValidDataWithReplanning() {
        String planningDate = generateDate(5);
        open("http://localhost:9999/");

        $("[data-test-id=city] input").setValue("Новосибирск");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate).pressEscape();
        $("[data-test-id=name] input").setValue(faker.name().fullName());
        $("[data-test-id=phone] input").setValue(faker.phoneNumber().phoneNumber());
        $x("//span[contains(text(),'условиями')]").click();
        $x("//span[contains(text(),'Запланировать')]").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + planningDate), Duration.ofSeconds(3))
                .shouldBe(Condition.visible);
        $x("//span[contains(text(),'Запланировать')]").click();
        $("[data-test-id=replan-notification]")
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldBe(Condition.visible);
        $x("//span[contains(text(),'Перепланировать')]").click();
    }
}
