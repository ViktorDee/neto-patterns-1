import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import utils.DataGenerator;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;
import static utils.DataGenerator.generateDate;

public class CardDeliveryTest {

    private static Faker faker;

    @BeforeAll
    static void setupAll() {
        faker = new Faker(new Locale("ru"));
    }

    @Test
    void testValidDataWithReplanning() {
        String firstMeetingDays = generateDate(5);
        String secondMeetingDays = generateDate(10);
        open("http://localhost:9999/");

        $("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A" + Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstMeetingDays);
        $("[data-test-id=name] input").setValue(faker.name().fullName());
        $("[data-test-id=phone] input").setValue(faker.phoneNumber().phoneNumber());
        $x("//span[contains(text(),'условиями')]").click();
        $x("//span[contains(text(),'Запланировать')]").click();
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDays), Duration.ofSeconds(5))
                .shouldBe(Condition.visible);
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A" + Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(secondMeetingDays);
        $x("//span[contains(text(),'Запланировать')]").click();
        $("[data-test-id=replan-notification]")
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldBe(Condition.visible);
        $x("//span[contains(text(),'Перепланировать')]").click();
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDays), Duration.ofSeconds(4))
                .shouldBe(Condition.visible);
    }
}
